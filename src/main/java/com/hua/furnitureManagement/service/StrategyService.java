package com.hua.furnitureManagement.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.pojo.dto.DeviceStrategyDTO;
import com.hua.furnitureManagement.pojo.dto.StrategyDTO;
import com.hua.furnitureManagement.pojo.vo.DeviceStrategyVO;
import com.hua.furnitureManagement.pojo.vo.StrategyVO;

import java.util.List;

public interface StrategyService {
    // 获取设备策略列表(分页)
    Page<JSONArray> deviceStrategyPage(Integer pageNum, Integer pageSize);

    // 获取系统设备策略列表(分页)
    Page<JSONArray> systemDeviceStrategyPage(Integer pageNum, Integer pageSize);

    // 删除设备策略
    void deleteDeviceStrategy(Long id);

    // 删除系统设备策略
    void deleteSystemDeviceStrategy(Long id);

    // 添加或更新设备策略
    void addOrUpdateDeviceStrategy(DeviceStrategyDTO request);

    // 添加或更新系统设备策略
    void addOrUpdateSystemDeviceStrategy(DeviceStrategyDTO request);

    // 获取全局策略列表(分页)
    Page<JSONArray> strategyPage(Integer pageNum, Integer pageSize);

    // 获取全局系统设备策略列表(分页)
    Page<JSONArray> systemStrategyPage(Integer pageNum, Integer pageSize);

    // 删除全局策略
    void deleteStrategy(Long id);

    // 删除全局系统策略
    void deleteSystemStrategy(Long id);

    // 添加或更新全局策略
    void addOrUpdateStrategy(StrategyDTO request);

    // 添加或更新全局系统策略
    void addOrUpdateSystemStrategy(StrategyDTO request);

    // 单设备策略列表，供用户选择
    List<JSONObject> deviceStrategyList(Long strategyId);

    // 全局策略列表，供用户选择
    List<JSONObject> strategyList();

    // 单设备策略详情
    DeviceStrategyVO deviceStrategy(Long id);

    // 全局策略详情
    StrategyVO strategy(Long id);
}
