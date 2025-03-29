package com.hua.furnitureManagement.service;

import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserVO;

/**
 * @ClassName UserService
 * @Author hua
 * @Date 2025/3/22
 */
public interface UserService {
    /**
     * 登录
     */
    public UserVO login(UserDTO user);

    /**
     * 注册
     */
    public void register(UserDTO user);
}
