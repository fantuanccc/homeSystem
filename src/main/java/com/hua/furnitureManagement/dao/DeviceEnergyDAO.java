package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceEnergyDAO {
    // 查询该用户家中每天的能耗数据(折线图)
    List<Map<String, Object>> lineChart(Long addressId);

    // 查询指定设备能耗数据
    List<DeviceEnergyDO> deviceEnergyData(Long deviceId);

    // 根据设备id和日期查询对应设备能耗信息
    DeviceEnergyDO queryByDeviceIdAndDs(Long deviceId, String ds);

    // 添加设备能耗记录
    Long addDeviceEnergyRecord(DeviceEnergyDO deviceEnergyDO);

    // 更新
    int updateDeviceEnergyNum(DeviceEnergyDO deviceEnergyDO);
}
