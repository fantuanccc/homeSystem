package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.enumeration.DeviceTypeEnum;
import com.hua.furnitureManagement.dao.DevicesDAO;
import com.hua.furnitureManagement.dao.UserDAO;
import com.hua.furnitureManagement.domain.DeviceDO;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import com.hua.furnitureManagement.service.FurnitureService;
import com.hua.furnitureManagement.pojo.vo.DeviceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 家具管理服务实现类
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
@Service("furnitureManagementService")
public class FurnitureServiceImpl implements FurnitureService {

    @Autowired
    private DevicesDAO devicesDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public Page<DeviceVO> list(Long userId, Integer pageNum, Integer pageSize) {
        Page<DeviceVO> result = new Page<>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        // 获取用户的地址
        Long addressId = userDAO.selectAddressByID(userId);
        if (addressId == null) {
            // 用户地址不存在
            throw new RuntimeException("用户暂未添加地址信息，请先完善地址信息！");
        }
        //获取设备总量
        Integer count = devicesDAO.countByAddressId(addressId);
        result.setTotalCount(count);
        result.setTotalPage(count / pageSize);
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
        //根据传参判断是新增还是更新
        if (request.getId() == null) {
            //新增
            BeanUtils.copyProperties(request, deviceDO);
            devicesDAO.insert(deviceDO);
        } else {
            BeanUtils.copyProperties(request, deviceDO);
            int n = devicesDAO.update(deviceDO);
            if (n == 0) {
                throw new RuntimeException("更新失败");
            }
        }
    }

    @Override
    public void delete(Long deviceId, Long userId) {
        // 获取用户的地址
        Long addressId = userDAO.selectAddressByID(userId);

        int n = devicesDAO.delete(deviceId, addressId);
        if (n == 0) {
            throw new RuntimeException("删除失败");
        }
        // TODO 删除其他关于该设备的记录信息
    }
    @Override
    public JSONArray devicePieChart(Long userId) {
        JSONArray result = new JSONArray();
        // 获取用户的地址
        Long addressId = userDAO.selectAddressByID(userId);
        // 获取设备总量
        List<Map<String, Object>> tempList = devicesDAO.pieChart(addressId);
        for (Map<String, Object> temp : tempList) {
            temp.put("name", DeviceTypeEnum.getEnumByCode((Integer) temp.get("type")).getName());
            temp.remove("type");
            result.add(temp);
        }
        return result;
    }
}
