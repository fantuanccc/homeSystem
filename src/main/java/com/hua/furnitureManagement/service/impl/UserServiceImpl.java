package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.domin.UserDO;
import com.hua.furnitureManagement.dto.UserDTO;
import com.hua.furnitureManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.assertj.core.util.DateUtil.now;

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

    @Override
    public void login(UserDTO userDTO) {
       UserDO userDO = new UserDO();
       userDO.setName(userDTO.getName());
       UserDO user = userDAO.login(userDO);
       if (user == null) {
           throw new RuntimeException("用户名不存在");
       }
       if (!user.getPassword().equals(userDTO.getPassword())) {
           throw new RuntimeException("密码错误");
       }
    }

    @Override
    public void register(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setName(userDTO.getName());
        UserDO user = userDAO.login(userDO);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        userDO.setPassword(userDTO.getPassword());
        userDO.setIsAdmin(0);
        userDAO.register(userDO);
    }
}
