package com.hua.furnitureManagement.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import com.hua.furnitureManagement.pojo.vo.DeviceDetailVO;
import com.hua.furnitureManagement.pojo.vo.DeviceVO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hua.furnitureManagement.service.DeviceService;


/**
 * 家具管理模块
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/management")
public class FurnitureManagementController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEnergyService deviceEnergyService;

    //分页查询
    @GetMapping("/list")
    public Result<Page<DeviceVO>> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            Long addressId = BaseContext.getCurrentAddressId();
            return Result.success(deviceService.list(addressId, pageNum, pageSize));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //新增/更新
    @PostMapping("/addOrUpdate")
    public Result<String> addOrUpdate(@RequestBody DeviceDTO request) {
        try {
            deviceService.addOrUpdate(request);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //删除
    @GetMapping("/delete")
    public Result<String> delete(@RequestParam Long deviceId) {
        try {
            deviceService.delete(deviceId);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //查询设备详情
    @GetMapping("/detail")
    public Result<DeviceDetailVO> deviceDetail(@RequestParam Long deviceId) {
        try {
            return Result.success(deviceService.deviceDetail(deviceId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //查询设备耗时
    @GetMapping("/detail/consume/time")
    public Result<JSONArray> queryDeviceEnergy(@RequestParam Long deviceId) {
        try {
            return Result.success(deviceEnergyService.queryDeviceEnergy(deviceId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //查询设备开启次数
    @GetMapping("/detail/count")
    public Result<JSONArray> queryDeviceOpenCount(@RequestParam Long deviceId) {
        try {
            return Result.success(deviceEnergyService.queryDeviceOpenCount(deviceId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    //查询设备耗电量
    @GetMapping("/detail/consume/value")
    public Result<JSONArray> queryDeviceEnergyValue(@RequestParam Long deviceId) {
        try {
            return Result.success(deviceEnergyService.queryDeviceEnergyValue(deviceId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}