package com.hua.furnitureManagement.dao;


import com.hua.furnitureManagement.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    //登录
    UserDO login(UserDO userDO);

    //注册
    void register(UserDO userDO);

    //根据用户ID查询地址
    Long selectAddressByID(Long userId);
}
