package com.hua.furnitureManagement.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceEnergyDAO {

    List<Map<String, Object>> lineChart(Long addressId);
}
