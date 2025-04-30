package com.hua.furnitureManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.vo.DeviceDetailVO;
import com.hua.furnitureManagement.pojo.vo.DeviceVO;

import java.util.List;

/**
 * 家具管理服务接口
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
public interface DeviceService {
    // 列出家具列表
    Page<DeviceVO> list(Long addressId, Integer pageNum, Integer pageSize);

    // 添加或修改家具
    void addOrUpdate(DeviceDTO request);

    // 删除家具
    void delete(Long deviceId);

    // 设备数据饼图
    JSONArray devicePieChart(Long addressId);

    // 设备详情
    DeviceDetailVO deviceDetail(Long id);

    // 获取状态为开启的设备
    List<DeviceDO> deviceStartList();

    // 获取所有设备
    List<DeviceDO> allDeviceList();
}
