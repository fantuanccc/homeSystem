package com.hua.furnitureManagement.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.exception.GlobalException;
import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.dao.AddressDAO;
import com.hua.furnitureManagement.dao.DeviceStrategyDAO;
import com.hua.furnitureManagement.dao.DevicesDAO;
import com.hua.furnitureManagement.dao.StrategyDAO;
import com.hua.furnitureManagement.pojo.dto.DeviceStrategyDTO;
import com.hua.furnitureManagement.pojo.dto.StrategyDTO;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceStrategyDO;
import com.hua.furnitureManagement.pojo.entry.StrategyDO;
import com.hua.furnitureManagement.pojo.vo.DeviceStrategyVO;
import com.hua.furnitureManagement.pojo.vo.StrategyVO;
import com.hua.furnitureManagement.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("strategyService")
@Slf4j
public class StrategyServiceImpl implements StrategyService {
    @Autowired
    private StrategyDAO strategyDAO;
    @Autowired
    private DeviceStrategyDAO deviceStrategyDAO;
    @Autowired
    private DevicesDAO devicesDAO;
    @Autowired
    private AddressDAO addressDAO;

    @Override
    public Page<JSONArray> deviceStrategyPage(Integer pageNum, Integer pageSize) {
        if (BaseContext.getCurrentAddressId() == null) {
            // 用户地址不存在
            throw new GlobalException("用户暂未添加地址信息，请先完善地址信息！");
        }
        Page<JSONArray> page = new Page<>();
        JSONArray result = new JSONArray();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        // 获取住址id，根据住址id获取该住址内的所有设备策略
        Long addressId = BaseContext.getCurrentAddressId();
        Integer count = deviceStrategyDAO.getDeviceStrategyCount(addressId);
        List<DeviceStrategyDO> list = deviceStrategyDAO.getDeviceStrategyInfoByAddressId(addressId, (pageNum - 1) * pageSize, pageSize);

        page.setTotalCount(count);
        page.setTotalPage(count / pageSize + 1);
        // 封装数据
        packageDeviceStrategyDO(list, result);
        page.setData(result);
        return page;
    }

    @Override
    public Page<JSONArray> systemDeviceStrategyPage(Integer pageNum, Integer pageSize) {
        Page<JSONArray> page = new Page<>();
        JSONArray result = new JSONArray();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        // 住址id为-1的属于系统设备策略，根据住址id获取该住址内的所有设备策略
        Integer count = deviceStrategyDAO.getDeviceStrategyCount(-1L);
        List<DeviceStrategyDO> list = deviceStrategyDAO.getDeviceStrategyInfoByAddressId(-1L, (pageNum - 1) * pageSize, pageSize);

        page.setTotalCount(count);
        page.setTotalPage(count / pageSize + 1);
        // 封装数据
        packageDeviceStrategyDO(list, result);
        page.setData(result);
        return page;
    }

    @Override
    public void deleteDeviceStrategy(Long id) {
        // TODO 删除前考虑是否条件支持删除
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())){
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        // 判断该策略是否正在使用
        if(devicesDAO.isUsingDeviceStrategy(id) != 0){
            //策略正在被使用，不能删除
            throw new GlobalException("该策略正在被使用，不能删除");
        }
        // 删除用户自定义单设备策略
        deviceStrategyDAO.deleteDeviceStrategy(id);

        // TODO 删除设备策略相关的设备信息
    }

    @Override
    public void deleteSystemDeviceStrategy(Long id) {
        //TODO 写在管理员模块下
    }

    @Override
    public void addOrUpdateDeviceStrategy(DeviceStrategyDTO request) {
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())){
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        DeviceStrategyDO deviceStrategyDO = new DeviceStrategyDO();
        //根据是否存在设备id判断是新增还是更新操作
        if(request.getId() == null){
            //新增操作
            //获取该用户地址id
            Long addressId = BaseContext.getCurrentAddressId();
            BeanUtils.copyProperties(request, deviceStrategyDO);
            deviceStrategyDO.setAddressId(addressId);
            log.info("新增设备策略：{}", deviceStrategyDO);
            deviceStrategyDAO.addDeviceStrategy(deviceStrategyDO);
        } else {
            //更新操作
            BeanUtils.copyProperties(request, deviceStrategyDO);
            log.info("更新设备策略：{}", deviceStrategyDO);
            deviceStrategyDAO.updateDeviceStrategy(deviceStrategyDO);
        }
    }

    @Override
    public void addOrUpdateSystemDeviceStrategy(DeviceStrategyDTO request) {
        //TODO 写在管理员模块下
    }

    @Override
    public Page<JSONArray> strategyPage(Integer pageNum, Integer pageSize) {
        if (BaseContext.getCurrentAddressId() == null) {
            // 用户地址不存在
            throw new GlobalException("用户暂未添加地址信息，请先完善地址信息！");
        }
        Page<JSONArray> page = new Page<>();
        JSONArray result = new JSONArray();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        // 获取住址id，根据住址id获取该住址内的所有设备策略
        Long addressId = BaseContext.getCurrentAddressId();
        Integer count = strategyDAO.getStrategyCount(addressId);
        List<StrategyDO> list = strategyDAO.getStrategyInfoByAddressId(addressId, (pageNum - 1) * pageSize, pageSize);

        page.setTotalCount(count);
        page.setTotalPage(count / pageSize + 1);
        // 封装数据
        packageStrategyDO(list, result);
        page.setData(result);
        return page;
    }

    @Override
    public Page<JSONArray> systemStrategyPage(Integer pageNum, Integer pageSize) {
        Page<JSONArray> page = new Page<>();
        JSONArray result = new JSONArray();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        // 住址id为-1的属于系统策略，根据住址id获取该住址内的所有设备策略
        Integer count = strategyDAO.getStrategyCount(-1L);
        List<StrategyDO> list = strategyDAO.getStrategyInfoByAddressId(-1L, (pageNum - 1) * pageSize, pageSize);

        page.setTotalCount(count);
        page.setTotalPage(count / pageSize + 1);
        // 封装数据
        packageStrategyDO(list, result);
        page.setData(result);
        return page;
    }

    @Override
    public void deleteStrategy(Long id) {
        // TODO 删除前考虑是否条件支持删除
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())){
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        // 判断该策略是否正在使用
        if(addressDAO.isUsingStrategy(id) != 0){
            //策略正在被使用，不能删除
            throw new GlobalException("该策略正在被使用，不能删除");
        }
        // 删除用户自定义全局策略
        strategyDAO.deleteStrategy(id);
    }

    @Override
    public void deleteSystemStrategy(Long id) {
        //TODO 写在管理员模块下
    }

    @Override
    public void addOrUpdateStrategy(StrategyDTO request) {
        // 获取用户角色，判断是否可以进行管理操作
        if ("家庭成员".equals(BaseContext.getCurrentRole())){
            //仅有户主有管理功能
            throw new GlobalException("用户暂无该权限");
        }

        StrategyDO strategyDO = new StrategyDO();
        //根据是否存在设备id判断是新增还是更新操作
        if(request.getId() == null){
            //新增操作
            //获取该用户地址id
            Long addressId = BaseContext.getCurrentAddressId();
            BeanUtils.copyProperties(request, strategyDO);
            strategyDO.setAddressId(addressId);
            log.info("新增全局策略：{}", strategyDO);
            strategyDAO.addStrategy(strategyDO);
        } else {
            //更新操作
            BeanUtils.copyProperties(request, strategyDO);
            log.info("更新全局策略：{}", strategyDO);
            strategyDAO.updateStrategy(strategyDO);
        }
    }

    @Override
    public void addOrUpdateSystemStrategy(StrategyDTO request) {
        //TODO 写在管理员模块下
    }

    @Override
    public List<JSONObject> deviceStrategyList(Integer deviceType) {
        List<JSONObject> result = new ArrayList<>();
        // 获取对应设备类型的设备策略 （自定义）
        List<DeviceStrategyDO> list = strategyDAO.getDeviceStrategyListByDeviceType(deviceType, BaseContext.getCurrentAddressId());
        list.forEach(deviceStrategyDO -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", deviceStrategyDO.getId());
            jsonObject.put("name", deviceStrategyDO.getName());
            jsonObject.put("strategyType", "用户自定义方案");
            result.add(jsonObject);
        });
        // 获取对应设备类型的设备策略 （非定义）
        List<DeviceStrategyDO> systemList = strategyDAO.getDeviceStrategyListByDeviceType(deviceType, -1L);
        systemList.forEach(deviceStrategyDO -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", deviceStrategyDO.getId());
            jsonObject.put("name", deviceStrategyDO.getName());
            jsonObject.put("strategyType", "系统方案");
            result.add(jsonObject);
        });
        log.info("设备策略列表：{}", result);
        return result;
    }

    @Override
    public List<JSONObject> strategyList() {
        return null;
    }

    @Override
    public DeviceStrategyVO deviceStrategy(Long id) {
        DeviceStrategyVO result = new DeviceStrategyVO();
        DeviceStrategyDO deviceStrategyDO = deviceStrategyDAO.getDeviceStrategyInfoById(id);
        BeanUtils.copyProperties(deviceStrategyDO, result);
        result.setGmtCreate(DateUtils.formatDateTime(deviceStrategyDO.getGmtCreate()));
        result.setGmtModified(DateUtils.formatDateTime(deviceStrategyDO.getGmtModified()));
        return result;
    }

    @Override
    public StrategyVO strategy(Long id) {
        StrategyVO result = new StrategyVO();
        StrategyDO strategyDO = strategyDAO.getStrategyInfoById(id);
        BeanUtils.copyProperties(strategyDO, result);
        result.setGmtCreate(DateUtils.formatDateTime(strategyDO.getGmtCreate()));
        result.setGmtModified(DateUtils.formatDateTime(strategyDO.getGmtModified()));
        return result;
    }

    // 封装 DeviceStrategyDO 数据
    private void packageDeviceStrategyDO(List<DeviceStrategyDO> list, JSONArray result){
        for (DeviceStrategyDO entry : list) {
            try {
                JSONObject temp = new JSONObject();
                temp.put("id", entry.getId());
                temp.put("name", entry.getName());
                temp.put("createTime", DateUtils.formatDateTime(entry.getGmtCreate()));
                temp.put("modifyTime", DateUtils.formatDateTime(entry.getGmtModified()));
                temp.put("deviceType", entry.getDevicesType());
                result.add(temp);
            } catch (Exception e) {
                log.error("封装数据出错,原因：{}", e.getMessage());
                throw new RuntimeException("设备策略封装数据出错");
            }
        }
    }

    // 封装 StrategyDO 数据
    private void packageStrategyDO(List<StrategyDO> list, JSONArray result){
        for (StrategyDO entry : list) {
            try {
                JSONObject temp = new JSONObject();
                temp.put("id", entry.getId());
                temp.put("name", entry.getName());
                temp.put("createTime", DateUtils.formatDateTime(entry.getGmtCreate()));
                temp.put("modifyTime", DateUtils.formatDateTime(entry.getGmtModified()));
                result.add(temp);
            } catch (Exception e) {
                log.error("封装数据出错,原因：{}", e.getMessage());
                throw new RuntimeException("设备策略封装数据出错");
            }
        }
    }
}
