package com.hua.furnitureManagement.controller;

import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.result.Response;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.pojo.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hua.furnitureManagement.service.FurnitureService;

/**
 * 家具管理Controller层
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/furniture/management")
public class FurnitureManagementController {

    @Autowired
    private FurnitureService furnitureService;

    //分页查询
    @GetMapping("/list")
    public Result<Page<?>> list(@RequestParam Long userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            return Response.success(furnitureService.list(userId, pageNum, pageSize));
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    //新增/更新
    @PostMapping("/addOrUpdate")
    public Result<Void> addOrUpdate(@RequestBody DeviceDTO request) {
        try {
            furnitureService.addOrUpdate(request);
            return Response.success();
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    //删除
    @GetMapping("/delete")
    public Result<Void> delete(@RequestParam Long deviceId, @RequestParam Long userId) {
        try {
            furnitureService.delete(deviceId, userId);
            return Response.success();
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}