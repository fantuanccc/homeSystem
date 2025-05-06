package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.enumeration.DeviceType;
import com.hua.furnitureManagement.common.exception.GlobalException;
import com.hua.furnitureManagement.dao.DeviceStrategyDAO;
import com.hua.furnitureManagement.dao.DevicesDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceStrategyDO;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import com.hua.furnitureManagement.pojo.vo.DeviceDetailVO;
import com.hua.furnitureManagement.service.DeviceService;
import com.hua.furnitureManagement.pojo.vo.DeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 家具管理服务实现类
 *
 * @Author hua
 * @Date 2025/2/17
 */
@Service("furnitureService")
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DevicesDAO devicesDAO;
    @Autowired
    private DeviceStrategyDAO deviceStrategyDAO;

    @Override
    public Page<DeviceVO> list(Long addressId, Integer pageNum, Integer pageSize) {
        Page<DeviceVO> result = new Page<>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        if (addressId == null) {
            // 用户地址不存在
            throw new GlobalException("用户暂未添加地址信息，请先完善地址信息！");
        }
        //获取设备总量
        Integer count = devicesDAO.countByAddressId(addressId);
        result.setTotalCount(count);
        result.setTotalPage(count / pageSize + 1);
        //获取当前页数的设备列表
        List<DeviceDO> deviceDOList = devicesDAO.listByAddressId(addressId, (pageNum - 1) * pageSize, pageSize);
        List<DeviceVO> deviceVOList = new ArrayList<>();
        deviceDOList.forEach(deviceDO -> {
            DeviceVO deviceVO = new DeviceVO();
            deviceVO.transformVO(deviceDO);
            deviceVOList.add(deviceVO);
        });
        result.setData(deviceVOList);
        return result;
    }

    @Override
    public void addOrUpdate(DeviceDTO request) {
        DeviceDO deviceDO = new DeviceDO();
        BeanUtils.copyProperties(request, deviceDO);
        //根据传参判断是新增还是更新
        if (request.getId() == null) {
            //新增
            //获取住址id
            Long addressId = BaseContext.getCurrentAddressId();
            deviceDO.setAddressId(addressId);
            devicesDAO.insert(deviceDO);
            log.info("新增设备成功，设备信息：{}", deviceDO);
        } else {
            //更新
            int n = devicesDAO.update(deviceDO);
            if (n == 0) {
                throw new RuntimeException("更新失败");
            }
            log.info("更新设备成功，设备信息：{}", deviceDO);
        }
    }

    @Override
    public void delete(Long deviceId) {
        //判断设备是否处于关闭状态
        if(devicesDAO.selectById(deviceId).getStatus() == 1){
            throw new GlobalException("请先关闭设备后，再进行删除。");
        }
        // 删除设备信息
        int n = devicesDAO.delete(deviceId);
        if (n == 0) {
            log.error("删除设备失败，设备id：{}", deviceId);
            throw new RuntimeException("删除失败");
        }
        log.info("删除设备成功，设备id：{}", deviceId);
        // TODO 删除其他关于该设备的记录信息
    }

    @Override
    public JSONArray devicePieChart(Long addressId) {
        JSONArray result = new JSONArray();
        // 获取设备总量
        List<Map<String, Object>> tempList = devicesDAO.pieChart(addressId);
        for (Map<String, Object> temp : tempList) {
            temp.put("name", DeviceType.getEnumByCode((Integer) temp.get("type")).getName());
            temp.remove("type");
            result.add(temp);
        }
        return result;
    }

    @Override
    public DeviceDetailVO deviceDetail(Long deviceId) {
        DeviceDetailVO deviceDetailVO = new DeviceDetailVO();
        // 获取设备的基本属性
        DeviceDO deviceDO = devicesDAO.selectById(deviceId);
        deviceDetailVO.deviceTransformVO(deviceDO);
        // 获取设备的详细属性
        if(deviceDO.getDevicesStrategyId() != null){
            DeviceStrategyDO deviceStrategyDO = deviceStrategyDAO.selectById(deviceDO.getDevicesStrategyId());
            deviceDetailVO.deviceStrategyTransformVO(deviceStrategyDO);
        }
        log.info("获取设备详情信息：{}", deviceDetailVO);
        return deviceDetailVO;
    }

    @Override
    public List<DeviceDO> deviceStartList() {
        List<DeviceDO> result = devicesDAO.deviceStartList();
        log.info("开启状态设备列表：{}", result);
        return result;
    }

    @Override
    public List<DeviceDO> allDeviceList() {
        List<DeviceDO> result = devicesDAO.allDeviceList();
        log.info("所有设备列表：{}", result);
        return result;
    }

    @Override
    public void setDeviceMode(Long deviceId, Long strategyId) {
        // 根据策略id获取策略信息
        DeviceStrategyDO deviceStrategyDO = deviceStrategyDAO.getDeviceStrategyInfoById(strategyId);

        // 更新设备的详细信息
        DeviceDO deviceDO = new DeviceDO();
        BeanUtils.copyProperties(deviceStrategyDO, deviceDO);
        deviceDO.setId(deviceId);
        deviceDO.setDevicesStrategyId(strategyId);
        int n = devicesDAO.updateMode(deviceDO);
        if (n == 0) {
            log.error("设定模式失败，设备id：{}", deviceId);
            throw new RuntimeException("设定模式失败");
        }
        log.info("设定模式成功，设备id：{}", deviceId);
    }

    @Override
    public void updateDeviceParam(DeviceDTO request) {
        DeviceDO deviceDO = new DeviceDO();
        BeanUtils.copyProperties(request, deviceDO);
        deviceDO.setDevicesStrategyId(-1L);
        int n = devicesDAO.updateMode(deviceDO);
        if (n == 0) {
            log.error("设备参数更新失败，设备id：{}", request.getId());
            throw new RuntimeException("设备参数更新失败");
        }
        log.info("设备参数更新失败，设备id：{}", request.getId());
    }
}
