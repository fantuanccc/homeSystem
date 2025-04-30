package com.hua.furnitureManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;

import java.util.Date;

public interface DeviceEnergyService {
    // 查询该用户家中每天的设备总能耗量数据 (折线图)
    JSONArray energyLineChart(Long addressId);

    // 指定设备耗时数据 (柱状图)
    JSONArray queryDeviceEnergy(Long deviceId);

    // 指定设备开启次数 (柱状图)
    JSONArray queryDeviceOpenCount(Long deviceId);

    // 指定设备能耗值 () (柱状图)
    JSONArray queryDeviceEnergyValue(Long deviceId);

    // 根据设备id和日期查询对应设备能耗信息
    DeviceEnergyDO queryByDeviceIdAndDs(Long deviceId, String ds);

    // 计算设备能耗定时任务
    void calDeviceEnergyTask();

    // 添加设备能耗记录定时任务
    void addDeviceEnergyDayRecordTask();
}
