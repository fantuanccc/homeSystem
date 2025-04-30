package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.AddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDAO {
    // 获取地址信息
    AddressDO getAddressInfo(Long addressId);

    // 获取正在使用的策略
    int isUsingStrategy(Long strategyId);

    // 申请密钥
    int applyKey(AddressDO addressDO);

    // 启用禁用密钥
    int updateKeyStatus(Integer status, Long id);

    // 获取密钥
    AddressDO getKey(String name, String unitNumber);

    // 获取所有小区名
    List<String> allAddressName();

    // 获取对应小区下面的单元号
    List<String> unitNumber(String name);
}
