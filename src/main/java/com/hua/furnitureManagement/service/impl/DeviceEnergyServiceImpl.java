package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.enumeration.DeviceTypeEnum;
import com.hua.furnitureManagement.dao.DeviceEnergyDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("deviceEnergyService")
public class DeviceEnergyServiceImpl implements DeviceEnergyService {
    @Autowired
    private DeviceEnergyDAO deviceEnergyDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public JSONArray energyLineChart(Long userId) {
        JSONArray result = new JSONArray();
        // 获取用户的地址
        Long addressId = userDAO.selectAddressByID(userId);
        // 获取设备总量
        List<Map<String, Object>> tempList = deviceEnergyDAO.lineChart(addressId);
        result.add(tempList);
        return result;
    }
}
