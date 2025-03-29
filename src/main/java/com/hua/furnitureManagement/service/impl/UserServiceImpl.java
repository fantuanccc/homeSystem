package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.domain.UserDO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
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

    @Override
    public UserVO login(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        UserDO userDO = new UserDO();
        userDO.setName(userDTO.getName());
        UserDO user = userDAO.login(userDO);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        BeanUtils.copyProperties(user, userVO);
        return userVO;
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
