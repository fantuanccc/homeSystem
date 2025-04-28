package com.hua.furnitureManagement.dao;


import com.hua.furnitureManagement.pojo.entry.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    //登录
    UserDO login(UserDO userDO);

    //注册
    void register(UserDO userDO);

    //根据用户ID查询地址
    Long selectAddressByID(Long userId);

    //编辑用户信息
    void edit(UserDO userDO);

    //获取用户信息
    UserDO getUserInfo(Long userId);
}
