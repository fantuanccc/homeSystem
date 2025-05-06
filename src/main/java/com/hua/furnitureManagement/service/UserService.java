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
     * 获取用户所有信息
     * @param userId
     * @return
     */
    UserDetailVO getUserInfo(Long userId);

    /**
     * 申请住址密钥 （添加/更新）
     * @param addressId
     */
    String applyKey(Long addressId);

    /**
     * 启用禁用密钥
     * @param status
     * @param addressId
     */
    void updateKeyStatus(Integer status, Long addressId);

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
     * 手机号验证码登录
     * @param phoneNumber
     * @return
     */
    UserVO codeLogin(String phoneNumber);

    /**
     * 获取密钥
     * @param addressId
     * @return
     */
    Map<String, Object> selectKey(Long addressId);
}
