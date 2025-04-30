package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.exception.GlobalException;
import com.hua.furnitureManagement.common.key.SnowflakeKeyGenerator;
import com.hua.furnitureManagement.dao.AddressDAO;
import com.hua.furnitureManagement.dao.UserAddressDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.pojo.dto.AddressDTO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.entry.AddressDO;
import com.hua.furnitureManagement.pojo.entry.UserAddressDO;
import com.hua.furnitureManagement.pojo.entry.UserDO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import com.hua.furnitureManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用戶服务实现类
 *
 * @Author hua
 * @Date 2025/3/22
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AddressDAO addressDAO;
    @Autowired
    private UserAddressDAO userAddressDAO;

    @Override
    public UserVO login(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        UserDO userDO = new UserDO();
        userDO.setPhoneNumber(userDTO.getPhoneNumber());
        UserDO user = userDAO.login(userDO);
        if (user == null) {
            throw new GlobalException("手机号或密码错误");
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new GlobalException("手机号或密码错误");
        }
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public void register(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setPhoneNumber(userDTO.getPhoneNumber());
        UserDO user = userDAO.login(userDO);
        // 判断手机号是否存在
        if(user != null){
            throw new GlobalException("该手机号已存在");
        }
        userDO.setPassword(userDTO.getPassword());
        userDAO.register(userDO);
    }

    @Override
    public void edit(UserDTO user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        // 判断手机号是否存在

        // 更新用户信息
        int n = userDAO.edit(userDO);
        if (n == 0) {
            log.error("更新用户信息失败，请重试");
            throw new GlobalException("更新用户信息失败，请重试");
        }
    }

    @Override
    public UserDetailVO getUserInfo(Long userId, Long addressId) {
        // 获取对应用户的个人信息和地址信息
        UserDO userDO = userDAO.getUserInfo(userId);
        AddressDO addressDO = addressDAO.getAddressInfo(addressId);

        UserDetailVO userInfo = new UserDetailVO();
        userInfo.setUsername(userDO.getUsername());
        userInfo.setAddressName(addressDO.getName());
//        userInfo.setIsOwner(userDO.getIsOwner() == 1 ? "户主" : "家庭成员");
        userInfo.setPhoneNumber(userDO.getPhoneNumber());
        userInfo.setUnitNumber(addressDO.getUnitNumber());
        return userInfo;
    }

    @Override
    public void applyKey() {
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())) {
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        // 生成一个随机数做为密钥（采用雪花算法） 数数据中心ID默认为1L, 机器ID由本机IP地址生成
        SnowflakeKeyGenerator generator = new SnowflakeKeyGenerator(SnowflakeKeyGenerator.getMachineId(), 1L);
        //获取当前用户的住址id
        AddressDO addressDO = new AddressDO();
        addressDO.setKey(generator.generateKey());
        addressDO.setId(BaseContext.getCurrentAddressId());
        int n = addressDAO.applyKey(addressDO);
        if (n == 0) {
            log.error("申请密钥失败，请重试");
            throw new GlobalException("申请密钥失败，请重试");
        }
    }

    @Override
    public void updateKeyStatus(Integer status) {
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())) {
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        int n = addressDAO.updateKeyStatus(status, BaseContext.getCurrentAddressId());
        if (n == 0) {
            if (status == 0) {
                log.error("禁用密钥失败，请重试");
                throw new GlobalException("禁用密钥失败，请重试");
            } else {
                log.error("启用密钥失败，请重试");
                throw new GlobalException("启用密钥失败，请重试");
            }
        }
    }

    @Override
    public Long addAddress(AddressDTO request) {
        // 判断密钥是否匹配
        AddressDO addressDO = addressDAO.getKey(request.getAddressName(), request.getUnitNumber());
        if(!request.getKey().equals(addressDO.getKey())){
            log.error("密钥不匹配");
            throw new GlobalException("添加地址失败，密钥不匹配");
        }
        UserAddressDO userAddressDO = new UserAddressDO();
        userAddressDO.setUserId(BaseContext.getCurrentId());
        userAddressDO.setAddressId(addressDO.getId());
        // 非户主
        userAddressDO.setIsOwner(0);
        return userAddressDAO.add(userAddressDO);
    }

    @Override
    public List<String> allAddressName() {
        List<String> result = addressDAO.allAddressName();
        log.info("查询所有地址名称：{}", result);
        return result;
    }

    @Override
    public List<String> unitNumber(String addressName) {
        List<String> result = addressDAO.unitNumber(addressName);
        log.info("查询地址 {} 的所有单元号：{}", addressName, result);
        return result;
    }

    @Override
    public List<Map<String, Object>> userAllAddress(Long userId) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 获取当前用户的地址信息
        List<Long> addressIds = userAddressDAO.selectAddressIdByUserId(userId);

        // 获取所有地址信息
        addressIds.forEach(addressId -> {
            AddressDO addressDO = addressDAO.getAddressInfo(addressId);
            Map<String, Object> map = new HashMap<>();
            map.put("addressId", addressId);
            map.put("addressName", addressDO.getName());
            map.put("unitNumber", addressDO.getUnitNumber());
            result.add(map);
        });
        log.info("查询当前用户的所有地址信息：{}", result);
        return result;
    }

    @Override
    public Integer getRole(Long userId, Long addressId) {
        return userAddressDAO.getRole(userId, addressId);
    }

    @Override
    public boolean isExistPhoneNumber(String phoneNumber) {
        return userDAO.isExistPhoneNumber(phoneNumber);
    }

    @Override
    public Long getUserId(String phoneNumber) {
        return userDAO.getUserId(phoneNumber);
    }
}
