package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.DeviceStrategyDO;
import com.hua.furnitureManagement.pojo.entry.StrategyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StrategyDAO {
    // 根据地址id获取全局策略信息
    List<StrategyDO> getStrategyInfoByAddressId(Long addressId, Integer pageNum, Integer pageSize);

    // 获取全局策略总数
    int getStrategyCount(Long addressId);

    // 根据设备id获取全局策略信息
    StrategyDO getStrategyInfoById(Long id);

    // 添加全局策略信息
    long addStrategy(StrategyDO strategyDO);

    // 更新全局策略信息
    void updateStrategy(StrategyDO strategyDO);

    // 删除全局策略信息
    void deleteStrategy(Long id);

    // 根据设备类型获取设备策略信息
    List<DeviceStrategyDO> getDeviceStrategyListByDeviceType(Integer deviceType, Long addressId);
}
