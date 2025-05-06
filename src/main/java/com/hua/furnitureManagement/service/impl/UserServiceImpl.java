package com.hua.furnitureManagement.service.impl;

import cn.hutool.core.util.StrUtil;
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

import java.util.*;

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
        UserDO user = userDAO.login(userDTO.getPhoneNumber());
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
        // 判断手机号是否存在
        if(!userDAO.isExistPhoneNumber(userDTO.getPhoneNumber())){
            throw new GlobalException("该手机号已存在");
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDAO.register(userDO);

        // 判断该手机号是否有房子,完善信息
        List<Long> addressList = addressDAO.getAddressByPhoneNumber(userDTO.getPhoneNumber());
        log.info("新用户 {} 房子信息：{}", userDO.getId(), addressList);
        // 添加新用户与其地址关系
        if(addressList.size() > 0){
            addressList.forEach(addressId -> {
                UserAddressDO userAddressDO = new UserAddressDO();
                userAddressDO.setUserId(userDO.getId());
                userAddressDO.setAddressId(addressId);
                // 用户手机号与地址表手机号一致，则为户主
                userAddressDO.setIsOwner(1);
                userAddressDAO.add(userAddressDO);
            });
        }
    }

    @Override
    public void edit(UserDTO user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        userDO.setId(BaseContext.getCurrentId());
        // 判断手机号是否存在
        if(StrUtil.isNotBlank(user.getPhoneNumber())) {
            if(userDAO.isExistPhoneNumber(user.getPhoneNumber())){
                log.error("用户 {} 编辑信息失败，原因：手机号已存在", BaseContext.getCurrentId());
                throw new GlobalException("手机号已存在");
            }
        }
        // 更新用户信息
        int n = userDAO.edit(userDO);
        if (n == 0) {
            log.error("更新用户信息失败，请重试");
            throw new GlobalException("更新用户信息失败，请重试");
        }
    }

    @Override
    public UserDetailVO getUserInfo(Long userId) {
        // 获取对应用户的个人信息
        UserDO userDO = userDAO.getUserInfo(userId);
        UserDetailVO userInfo = new UserDetailVO();
        userInfo.setUsername(userDO.getUsername());
        userInfo.setPhoneNumber(userDO.getPhoneNumber());

        // 获取对应用户的地址信息
        List<Long> addressList = userAddressDAO.selectAddressIdByUserId(userId);
        List<Map<String, Object>> list = new ArrayList<>();
        addressList.forEach(addressId -> {
            AddressDO addressInfo = addressDAO.getAddressInfo(addressId);
            Map<String, Object> map = new HashMap<>();
            map.put("addressId", addressInfo.getId());
            map.put("addressName", addressInfo.getName());
            map.put("unitNumber", addressInfo.getUnitNumber());
            map.put("role", userAddressDAO.getRole(userId, addressId));
            list.add(map);
        });
        userInfo.setAddressList(list);
        log.info("用户个人信息：{}", userInfo);
        return userInfo;
    }

    @Override
    public String applyKey(Long addressId) {
        // 生成一个随机数做为密钥（采用雪花算法） 数数据中心ID默认为1L, 机器ID由本机IP地址生成
        SnowflakeKeyGenerator generator = new SnowflakeKeyGenerator(SnowflakeKeyGenerator.getMachineId(), 1L);
        //获取当前用户的住址id
        AddressDO addressDO = new AddressDO();
        addressDO.setKey(generator.generateKey());
        addressDO.setId(addressId);
        int n = addressDAO.applyKey(addressDO);
        if (n == 0) {
            log.error("申请密钥失败，请重试");
            throw new GlobalException("申请密钥失败，请重试");
        }
        log.info("申请密钥：{}", addressDO.getKey());
        return addressDO.getKey();
    }

    @Override
    public void updateKeyStatus(Integer status, Long addressId) {
        int n = addressDAO.updateKeyStatus(status, addressId);
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
        // 判断当前用户已添加该地址信息
        if(userAddressDAO.isExistLink(BaseContext.getCurrentId(), addressDO.getId())){
            log.error("用户 {} 已添加该地址信息", BaseContext.getCurrentId());
            throw new GlobalException("用户已添加该地址信息, 请勿重复添加");
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

        // 获取所有地址信息jwt校验失败
        addressIds.forEach(addressId -> {
            AddressDO addressDO = addressDAO.getAddressInfo(addressId);
            Map<String, Object> map = new HashMap<>();
            map.put("addressId", addressId);
            map.put("addressName", addressDO.getName());
            map.put("unitNumber", addressDO.getUnitNumber());
            if(Objects.equals(addressDO.getId(), BaseContext.getCurrentAddressId())){
                map.put("currentAddress", true);
            }
            result.add(map);
        });
        log.info("查询当前用户的所有地址信息：{}", result);
        return result;
    }

    @Override
    public Integer getRole(Long userId, Long addressId) {
        Integer role = userAddressDAO.getRole(userId, addressId);
        log.info("查询用户 {} 地址 {} 的角色：{}", userId, addressId, role == 1 ? "户主" : "家庭成员");
        return role;
    }

    @Override
    public boolean isExistPhoneNumber(String phoneNumber) {
        return userDAO.isExistPhoneNumber(phoneNumber);
    }

    @Override
    public UserVO codeLogin(String phoneNumber) {
        UserVO userVO = new UserVO();
        UserDO user = userDAO.login(phoneNumber);
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public Map<String, Object> selectKey(Long addressId) {
        Map<String, Object> map = addressDAO.getKeyById(addressId);
        log.info("查询地址 {} 的密钥：{} , 状态：{}", addressId, map.get("key"), map.get("status"));
        return map;
    }
}
