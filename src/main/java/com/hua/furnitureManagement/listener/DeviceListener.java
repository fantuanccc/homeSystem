package com.hua.furnitureManagement.listener;

import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import com.hua.furnitureManagement.pojo.entry.DeviceEnergyDO;
import com.hua.furnitureManagement.service.DeviceEnergyService;
import com.hua.furnitureManagement.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 设备定时任务
 *
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
    private ThreadPoolExecutor commonThread = new ThreadPoolExecutor(
            2, 4,0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024));

    /**
     * 定时任务，计算设备耗电情况 每1分钟计算1次
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void calDeviceEnergyTask() {
        log.info("计算设备耗电定时任务开始，当前时间：{}", DateUtils.getCurrentDateFormatted());
        commonThread.execute(() -> deviceEnergyService.calDeviceEnergyTask());
    }

    /**
     * 添加设备每天的能耗日志记录 每天晚上12点进行添加
     */
//    @Scheduled(cron = "0 0 0 * * ? ")
    private void addDeviceEnergyDayRecordTask() {
        log.info("添加设备每天的能耗日志记录定时任务开始，当前时间：{}", DateUtils.getCurrentDateFormatted());
        commonThread.execute(() -> deviceEnergyService.addDeviceEnergyDayRecordTask());
    }
}
