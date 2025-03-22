package com.hua.furnitureManagement.controller;

import com.hua.furnitureManagement.common.result.Response;
import com.hua.furnitureManagement.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hua.furnitureManagement.service.FurnitureManagementService;

/**
 * 家具管理Controller层
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/furniture/homeView")
public class FurnitureManagementController {

    @Autowired
    private FurnitureManagementService furnitureManagementService;

    @GetMapping("/test")
    public Result<String> test() {
        furnitureManagementService.test();
        return Response.success("测试成功");
    }

}
