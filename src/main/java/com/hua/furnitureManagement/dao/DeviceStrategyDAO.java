package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceStrategyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceStrategyDAO {
    // 根据设备id获取设备策略信息
    DeviceStrategyDO selectById(Long deviceId);

    // 根据地址id获取设备策略信息
    List<DeviceStrategyDO> getDeviceStrategyInfoByAddressId(Long addressId, Integer pageNum, Integer pageSize);

    // 获取设备策略总数
    int getDeviceStrategyCount(Long addressId);

    // 新增用户自定义单设备策略
    long addDeviceStrategy(DeviceStrategyDO deviceStrategyDO);

    // 更新用户自定义单设备策略
    void updateDeviceStrategy(DeviceStrategyDO deviceStrategyDO);

    // 删除用户自定义单设备策略
    void deleteDeviceStrategy(Long id);

    // 根据id获取设备策略信息
    DeviceStrategyDO getDeviceStrategyInfoById(Long id);
}
