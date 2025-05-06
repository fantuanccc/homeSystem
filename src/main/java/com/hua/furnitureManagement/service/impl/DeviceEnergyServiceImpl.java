package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.dao.DeviceEnergyDAO;
import com.hua.furnitureManagement.dao.DeviceStrategyDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;
import com.hua.furnitureManagement.pojo.entry.DeviceStrategyDO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import com.hua.furnitureManagement.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("deviceEnergyService")
@Slf4j
public class DeviceEnergyServiceImpl implements DeviceEnergyService {
    @Autowired
    private DeviceEnergyDAO deviceEnergyDAO;
    @Autowired
    private DeviceService deviceService;

    @Override
    public JSONArray energyLineChart(Long addressId) {
        JSONArray result = new JSONArray();
        // 获取设备总量
        List<Map<String, Object>> tempList = deviceEnergyDAO.lineChart(addressId);
        tempList.forEach(map -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", map.get("ds"));
            temp.put("value", map.get("value"));
            result.add(temp);
        });
        return result;
    }

    @Override
    public JSONArray queryDeviceEnergy(Long deviceId) {
        JSONArray result = new JSONArray();
        // 获取设备的耗时数据
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", deviceEnergyDO.getDs());
            temp.put("value", deviceEnergyDO.getTime());
            result.add(temp);
        });
        return result;
    }

    @Override
    public JSONArray queryDeviceOpenCount(Long deviceId) {
        JSONArray result = new JSONArray();
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", deviceEnergyDO.getDs());
            temp.put("value", deviceEnergyDO.getCount());
            result.add(temp);
        });
        return result;
    }

    @Override
    public JSONArray queryDeviceEnergyValue(Long deviceId) {
        JSONArray result = new JSONArray();
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", deviceEnergyDO.getDs());
            temp.put("value", deviceEnergyDO.getNum());
            result.add(temp);
        });
        return result;
    }

    @Override
    public DeviceEnergyDO queryByDeviceIdAndDs(Long deviceId, String ds) {
        DeviceEnergyDO deviceEnergyDO = deviceEnergyDAO.queryByDeviceIdAndDs(deviceId, ds);
        return deviceEnergyDO;
    }

    @Override
    public void calDeviceEnergyTask() {
        // 获取状态为开启的设备
        List<DeviceDO> deviceList = deviceService.deviceStartList();
        String ds = DateUtils.getCurrentDateOnly();

        // 计算设备能耗值
        for (DeviceDO deviceDO : deviceList) {
            try {
                // 计算每分钟的能耗值
                double num = deviceDO.getPower() / 60;
                // 查询设备能耗表是否创建当天能耗记录
                DeviceEnergyDO deviceEnergyDO = queryByDeviceIdAndDs(deviceDO.getId(), ds);
                if (deviceEnergyDO == null) {
                    deviceEnergyDO = new DeviceEnergyDO();
                    // 创建能耗记录
                    deviceEnergyDO.DeviceDOTransformEnergyDOInit(deviceDO);
                    addDeviceEnergyRecord(deviceEnergyDO);
                }
                //更新数据
                deviceEnergyDO.setNum(deviceEnergyDO.getNum() + num);
                int n = deviceEnergyDAO.updateDeviceEnergyNum(deviceEnergyDO);
                if (n == 0) {
                    throw new RuntimeException("更新失败");
                }
                log.info("设备 {} 能耗数值更新成功", deviceDO.getId());
            } catch (Exception e) {
                log.error("计算设备 {} 能耗失败，原因：{}", deviceDO.getId(), e.getMessage(), e);
            }
        }
    }

    @Override
    public void addDeviceEnergyDayRecordTask() {
        // 获取所有设备信息
        List<DeviceDO> deviceList = deviceService.allDeviceList();
        for (DeviceDO deviceDO : deviceList) {
            try {
                DeviceEnergyDO deviceEnergyDO = new DeviceEnergyDO();
                // 初始化设备能耗信息
                deviceEnergyDO.DeviceDOTransformEnergyDOInit(deviceDO);
                addDeviceEnergyRecord(deviceEnergyDO);
                log.info("添加设备 {} 当天能耗记录成功", deviceDO.getId());
            } catch (Exception e) {
                log.error("添加设备 {} 当天能耗记录失败，原因：{}", deviceDO.getId(), e.getMessage(), e);
            }
        }
    }

    // 添加设备能耗记录
    private synchronized void addDeviceEnergyRecord(DeviceEnergyDO deviceEnergyDO) {
        DeviceEnergyDO deviceEnergy = deviceEnergyDAO.queryByDeviceIdAndDs(deviceEnergyDO.getDevicesId(), deviceEnergyDO.getDs());
        if (deviceEnergy == null) {
            deviceEnergyDAO.addDeviceEnergyRecord(deviceEnergyDO);
            log.info("添加设备能耗记录成功，设备能耗信息{}", deviceEnergyDO);
        } else {
            log.info("设备 {} 能耗记录已存在", deviceEnergyDO.getDevicesId());
        }
    }
}
