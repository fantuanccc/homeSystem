package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.UserAddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressDAO {

    // 添加用户地址
    Long add(UserAddressDO userAddressDO);

    // 根据用户id查询地址id列表
    List<Long> selectAddressIdByUserId(Long userId);

    // 获取用户角色
    Integer getRole(Long userId, Long addressId);

    // 根据用户id和地址id查询地址信息
    boolean isExistLink(Long userId, Long addressId);
}
