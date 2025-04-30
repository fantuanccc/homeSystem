package com.hua.furnitureManagement.service;

import com.hua.furnitureManagement.pojo.dto.AddressDTO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
import com.hua.furnitureManagement.pojo.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Author hua
 * @Date 2025/3/22
 */
public interface UserService {
    /**
     * 登录
     */
    UserVO login(UserDTO user);

    /**
     * 注册
     */
    void register(UserDTO user);

    /**
     * 编辑用户信息
     * @param user
     */
    void edit(UserDTO user);

    /**
     * 根据id和住址获取用户所有信息
     * @param userId
     * @param addressId
     * @return
     */
    UserDetailVO getUserInfo(Long userId, Long addressId);

    /**
     * 申请住址密钥 （添加/更新）
     */
    void applyKey();

    /**
     * 启用禁用密钥
     * @param status
     */
    void updateKeyStatus(Integer status);

    /**
     * 添加用户住址信息
     * @param request
     * @return 地址id
     */
    Long addAddress(AddressDTO request);

    /**
     * 获取所有小区名
     * @return
     */
     List<String> allAddressName();

    /**
     * 获取对应小区下面的单元号
     * @param addressName
     * @return
     */
    List<String> unitNumber(String addressName);

    /**
     * 获取用户所有住址信息
     * @param userId
     * @return
     */
    List<Map<String, Object>> userAllAddress(Long userId);

    /**
     * 获取用户角色
     * @param userId
     * @param addressId
     * @return
     */
    Integer getRole(Long userId, Long addressId);

    /**
     * 查询手机号是否存在用户表
     * @param phoneNumber
     * @return
     */
    boolean isExistPhoneNumber(String phoneNumber);

    /**
     * 根据手机号获取用户id
     * @param phoneNumber
     * @return
     */
    Long getUserId(String phoneNumber);
}
