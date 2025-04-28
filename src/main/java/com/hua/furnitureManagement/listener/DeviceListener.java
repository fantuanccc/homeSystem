package com.hua.furnitureManagement.listener;

import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import com.hua.furnitureManagement.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 设备定时任务
 * @Author hua
 * @Date 2025/4/27
 */
@Component
@Slf4j
public class DeviceListener {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEnergyService deviceEnergyService;

    /**
     * 定时任务，计算设备耗电情况 每1分钟计算1次
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void calDeviceEnergyTask() throws ParseException {
        // 获取状态为开启的设备
        List<DeviceDO> deviceList = deviceService.deviceStartList();
        String currentDateOnly = DateUtils.getCurrentDateOnly();
        Date ds = DateUtils.parseDate(currentDateOnly, "yyyy-MM-dd");

        // 计算设备能耗值
        for (DeviceDO deviceDO : deviceList) {

            DeviceEnergyDO deviceEnergyDO = deviceEnergyService.queryByDeviceIdAndDs(deviceDO.getId(), ds);
        }
    }
}
