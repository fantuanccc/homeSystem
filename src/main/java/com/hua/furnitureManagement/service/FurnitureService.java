package com.hua.furnitureManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import com.hua.furnitureManagement.pojo.vo.DeviceVO;

/**
 * 家具管理服务接口
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
public interface FurnitureService {
    //列出家具列表
    Page<DeviceVO> list(Long userId, Integer pageNum, Integer pageSize);

    //添加或修改家具
    void addOrUpdate(DeviceDTO request);

    //删除家具
    void delete(Long deviceId, Long userId);

    //设备数据饼图
    JSONArray devicePieChart(Long userId);
}
