package com.hua.furnitureManagement.service;

import com.hua.furnitureManagement.dto.UserDTO;

/**
 * @ClassName UserService
 * @Author hua
 * @Date 2025/3/22
 */
public interface UserService {
    /**
     * 登录
     */
    public void login(UserDTO user);

    /**
     * 注册
     */
    public void register(UserDTO user);
}
