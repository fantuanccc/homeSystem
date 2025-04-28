package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DevicesDAO {

    // 查询住址内的设备总数
    Integer countByAddressId(Long addressId);

    // 分页查询住址内的设备列表
    List<DeviceDO> listByAddressId(Long addressId, Integer pageNum, Integer pageSize);

    // 新增设备信息
    void insert(DeviceDO deviceDO);

    // 更新设备信息
    int update(DeviceDO deviceDO);

    // 删除设备信息
    int delete(Long deviceId);

    // 设备数据饼图
    List<Map<String, Object>> pieChart(Long addressId);

    // 根据设备ID查询设备信息
    DeviceDO selectById(Long id);

    // 获取设备策略是否被使用
    int isUsingDeviceStrategy(Long deviceStrategyId);

    // 获取开启状态的设备列表
    List<DeviceDO> deviceStartList();
}
