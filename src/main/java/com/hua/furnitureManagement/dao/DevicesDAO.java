package com.hua.furnitureManagement.dao;

import com.hua.furnitureManagement.domain.DeviceDO;
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
    int delete(Long deviceId, Long addressId);

    // 设备数据饼图
    List<Map<String, Object>> pieChart(Long addressId);
}
