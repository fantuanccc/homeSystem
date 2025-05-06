package com.hua.furnitureManagement.dao;


import com.hua.furnitureManagement.pojo.entry.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    //登录
    UserDO login(String phoneNumber);

    //注册
    void register(UserDO userDO);

    //编辑用户信息
    int edit(UserDO userDO);

    //获取用户信息
    UserDO getUserInfo(Long userId);

    //是否存在手机号
    boolean isExistPhoneNumber(String phoneNumber);
}
