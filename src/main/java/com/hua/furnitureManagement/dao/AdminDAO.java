package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.AdminDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDAO {
    // 管理员登录
    AdminDO login(AdminDO adminDO);
}
