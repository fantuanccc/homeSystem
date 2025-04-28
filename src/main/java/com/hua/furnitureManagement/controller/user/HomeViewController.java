package com.hua.furnitureManagement.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import com.hua.furnitureManagement.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 主页模块
 * @Author hua
 * @Date 2025/3/30
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/home")
public class HomeViewController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEnergyService deviceEnergyService;

    @GetMapping("/pieChart")
    public Result<JSONArray> devicePieChart() {
        try {
            Long AddressId = BaseContext.getCurrentAddressId();
            return Result.success(deviceService.devicePieChart(AddressId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/lineChart")
    public Result<JSONArray> energyLineChart() {
        try {
            Long AddressId = BaseContext.getCurrentAddressId();
            return Result.success(deviceEnergyService.energyLineChart(AddressId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
