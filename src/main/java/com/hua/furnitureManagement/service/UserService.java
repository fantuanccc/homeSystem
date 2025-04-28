package com.hua.furnitureManagement.service;

import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
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

    /**
     * 编辑用户信息
     * @param user
     */
    void edit(UserDTO user);

    /**
     * 获取用户信息
     * @param userId
     * @param addressId
     * @return
     */
    UserDetailVO getUserInfo(Long userId, Long addressId);
}
