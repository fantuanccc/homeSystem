package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.dao.AddressDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.pojo.entry.AddressDO;
import com.hua.furnitureManagement.pojo.entry.UserDO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
import com.hua.furnitureManagement.service.UserService;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用戶服务实现类
 *
 * @Author hua
 * @Date 2025/3/22
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AddressDAO addressDAO;

    @Override
    public UserVO login(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        UserDO userDO = new UserDO();
        userDO.setUsername(userDTO.getUsername());
        UserDO user = userDAO.login(userDO);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public void register(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setUsername(userDTO.getUsername());
        UserDO user = userDAO.login(userDO);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        userDO.setPassword(userDTO.getPassword());
        // 刚注册的用户均为普通用户，信息完善后判定是否为户主
        userDO.setIsOwner(0);
        userDAO.register(userDO);
    }

    @Override
    public void edit(UserDTO user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);

        //判断是否为户主
        if(addressDAO.selectByPhone(userDO.getPhoneNumber(), userDO.getAddressId())){
            userDO.setIsOwner(1);
        } else {
            userDO.setIsOwner(0);
        }
        // 更新用户信息
        userDAO.edit(userDO);
    }

    @Override
    public UserDetailVO getUserInfo(Long userId, Long addressId) {
        // 获取对应用户的个人信息和地址信息
        UserDO userDO = userDAO.getUserInfo(userId);
        AddressDO addressDO = addressDAO.getAddressInfo(addressId);

        UserDetailVO userInfo = new UserDetailVO();
        userInfo.setUsername(userDO.getUsername());
        userInfo.setAddressName(addressDO.getName());
        userInfo.setIsOwner(userDO.getIsOwner() == 1 ? "户主" : "家庭成员");
        userInfo.setPhoneNumber(userDO.getPhoneNumber());
        userInfo.setUnitNumber(addressDO.getUnitNumber());
        return userInfo;
    }
}
