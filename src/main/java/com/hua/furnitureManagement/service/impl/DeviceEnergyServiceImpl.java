package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.dao.DeviceEnergyDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import lombok.extern.slf4j.Slf4j;
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
    private UserDAO userDAO;

    @Override
    public JSONArray energyLineChart(Long addressId) {
        JSONArray result = new JSONArray();
        // 获取设备总量
        List<Map<String, Object>> tempList = deviceEnergyDAO.lineChart(addressId);
        tempList.forEach(map -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", DateUtils.formatDateOnly((Date) map.get("ds")));
            temp.put("value", map.get("value"));
            result.add(temp);
        });
        log.info("energyLineChart result: {}", result);
        return result;
    }

    @Override
    public JSONArray queryDeviceEnergy(Long deviceId) {
        JSONArray result = new JSONArray();
        // 获取设备的耗时数据
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", DateUtils.formatDateOnly(deviceEnergyDO.getDs()));
            temp.put("value", deviceEnergyDO.getTime());
            result.add(temp);
        });
        log.info("queryDeviceEnergy result: {}", result);
        return result;
    }

    @Override
    public JSONArray queryDeviceOpenCount(Long deviceId) {
        JSONArray result = new JSONArray();
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", DateUtils.formatDateOnly(deviceEnergyDO.getDs()));
            temp.put("value", deviceEnergyDO.getCount());
            result.add(temp);
        });
        log.info("queryDeviceOpenCount result: {}", result);
        return result;
    }

    @Override
    public JSONArray queryDeviceEnergyValue(Long deviceId) {
        JSONArray result = new JSONArray();
        List<DeviceEnergyDO> tempList = deviceEnergyDAO.deviceEnergyData(deviceId);
        tempList.forEach(deviceEnergyDO -> {
            JSONObject temp = new JSONObject();
            temp.put("ds", DateUtils.formatDateOnly(deviceEnergyDO.getDs()));
            temp.put("value", deviceEnergyDO.getNum());
            result.add(temp);
        });
        log.info("queryDeviceEnergyValue result: {}", result);
        return result;
    }

    @Override
    public DeviceEnergyDO queryByDeviceIdAndDs(Long deviceId, Date ds) {
        DeviceEnergyDO deviceEnergyDO = deviceEnergyDAO.queryByDeviceIdAndDs(deviceId, ds);

        return deviceEnergyDO;
    }

}
