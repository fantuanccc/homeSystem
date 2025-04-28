package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.AddressDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDAO {
    // 根据手机号查询地址是否存在
    boolean selectByPhone(String phoneNumber, Long addressId);

    // 获取地址信息
    AddressDO getAddressInfo(Long addressId);

    int isUsingStrategy(Long strategyId);
}
