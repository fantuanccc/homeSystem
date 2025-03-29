package com.hua.furnitureManagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.hua.furnitureManagement.common.result.Response;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import com.hua.furnitureManagement.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/furniture/home")
public class HomeViewController {
    @Autowired
    private FurnitureService furnitureService;
    @Autowired
    private DeviceEnergyService deviceEnergyService;

    @GetMapping("/pieChart")
    public Result<JSONArray> devicePieChart(@RequestParam Long userId) {
        try {
            return Response.success(furnitureService.devicePieChart(userId));
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/lineChart")
    public Result<JSONArray> energyLineChart(@RequestParam Long userId) {
        try {
            return Response.success(deviceEnergyService.energyLineChart(userId));
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}
