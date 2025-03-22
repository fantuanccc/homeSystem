package com.hua.furnitureManagement.dao;


import com.hua.furnitureManagement.domin.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    UserDO login(UserDO userDO);

    void register(UserDO userDO);
}
