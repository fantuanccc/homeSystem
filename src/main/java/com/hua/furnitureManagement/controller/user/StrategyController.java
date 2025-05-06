package com.hua.furnitureManagement.controller.user;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hua.furnitureManagement.common.Page;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.pojo.dto.DeviceStrategyDTO;
import com.hua.furnitureManagement.pojo.dto.StrategyDTO;
import com.hua.furnitureManagement.pojo.vo.DeviceStrategyVO;
import com.hua.furnitureManagement.pojo.vo.StrategyVO;
import com.hua.furnitureManagement.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略模块
 *
 * @Author hua
 * @Date 2025/4/1
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/strategy")
public class StrategyController {
    @Autowired
    private StrategyService strategyService;

    /**
     * 说明：系统即为系统自带的策略列表。由系统管理员管理。
     * 非系统的策略均为用户管理
     * 策略分为全局策略和单设备策略
    */

     /**
     * 获取自定义单设备策略列表(分页)
     */
    @GetMapping("/deviceStrategyPage")
    public Result<Page<JSONArray>> deviceStrategyPage(@RequestParam Integer pageNum,
                                                         @RequestParam Integer pageSize) {
        try {
            return Result.success(strategyService.deviceStrategyPage(pageNum, pageSize));
        } catch (Exception e) {
            log.error("deviceStrategyPage报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取系统默认单设备策略列表(分页)
     */
    @GetMapping("/systemDeviceStrategyPage")
    public Result<Page<JSONArray>> systemDeviceStrategyPage(@RequestParam Integer pageNum,
                                                               @RequestParam Integer pageSize) {
        try {
            return Result.success(strategyService.systemDeviceStrategyPage(pageNum, pageSize));
        } catch (Exception e) {
            log.error("systemDeviceStrategyPage报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除自定义设备策略
     */
    @GetMapping("/deleteDeviceStrategy")
    public Result<String> deleteDeviceStrategy(@RequestParam Long id) {
        try {
            strategyService.deleteDeviceStrategy(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("deleteDeviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除系统单设备策略
     */
    @GetMapping("/deleteSystemDeviceStrategy")
    public Result<String> deleteSystemDeviceStrategy(@RequestParam Long id) {
        try {
            strategyService.deleteSystemDeviceStrategy(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("deleteSystemDeviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增或者更新自定义单设备策略
     */
    @PostMapping("/addOrUpdateDeviceStrategy")
    public Result<String> addOrUpdateDeviceStrategy(@RequestBody DeviceStrategyDTO request) {
        try {
            strategyService.addOrUpdateDeviceStrategy(request);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("addOrUpdateDeviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增或者更新系统单设备策略
     */
    @PostMapping("/addOrUpdateSystemDeviceStrategy")
    public Result<String> addOrUpdateSystemDeviceStrategy(@RequestBody DeviceStrategyDTO request) {
        try {
            strategyService.addOrUpdateSystemDeviceStrategy(request);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("addOrUpdateSystemDeviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取自定义全局策略列表(分页)
     */
    @GetMapping("/strategyPage")
    public Result<Page<JSONArray>> strategyPage(@RequestParam Integer pageNum,
                                                   @RequestParam Integer pageSize) {
        try {
            return Result.success(strategyService.strategyPage(pageNum, pageSize));
        } catch (Exception e) {
            log.error("strategyPage报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取系统默认全局策略列表(分页)
     */
    @GetMapping("/systemStrategyPage")
    public Result<Page<JSONArray>> systemStrategyPage(@RequestParam Integer pageNum,
                                                         @RequestParam Integer pageSize) {
        try {
            return Result.success(strategyService.systemStrategyPage(pageNum, pageSize));
        } catch (Exception e) {
            log.error("systemStrategyPage报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除自定义全局策略
     */
    @GetMapping("/deleteStrategy")
    public Result<String> deleteStrategy(@RequestParam Long id) {
        try {
            strategyService.deleteStrategy(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("deleteStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除系统默认全局策略
     */
    @GetMapping("/deleteSystemStrategy")
    public Result<String> deleteSystemStrategy(@RequestParam Long id) {
        try {
            strategyService.deleteSystemStrategy(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("deleteSystemDeviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增或者更新自定义全局策略
     */
    @PostMapping("/addOrUpdateStrategy")
    public Result<String> addOrUpdateStrategy(@RequestBody StrategyDTO request) {
        try {
            strategyService.addOrUpdateStrategy(request);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("addOrUpdateStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增或者更新系统默认全局策略
     */
    @PostMapping("/addOrUpdateSystemStrategy")
    public Result<String> addOrUpdateSystemStrategy(@RequestBody StrategyDTO request) {
        try {
            strategyService.addOrUpdateSystemStrategy(request);
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("addOrUpdateSystemStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 单设备策略列表，供用户选择
     */
    @GetMapping("/deviceStrategyList")
    public Result<List<JSONObject>> deviceStrategyList(@RequestParam Integer deviceType){
        try {
            return Result.success(strategyService.deviceStrategyList(deviceType));
        } catch (Exception e) {
            log.error("deviceStrategyList报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 全局策略列表，供用户选择
     */
    @GetMapping("/strategyList")
    public Result<List<JSONObject>> strategyList(){
        try {
            return Result.success(strategyService.strategyList());
        } catch (Exception e) {
            log.error("strategyList报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 设备策略详细
     */
    @GetMapping("/deviceStrategy")
    public Result<DeviceStrategyVO> deviceStrategy(@RequestParam Long id){
        try {
            return Result.success(strategyService.deviceStrategy(id));
        } catch (Exception e) {
            log.error("deviceStrategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 全局策略详细
     */
    @GetMapping("/strategy")
    public Result<StrategyVO> strategy(@RequestParam Long id){
        try {
            return Result.success(strategyService.strategy(id));
        } catch (Exception e) {
            log.error("strategy报错，原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
