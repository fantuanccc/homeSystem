package com.hua.furnitureManagement.pojo.vo;

import com.hua.furnitureManagement.common.util.DateUtils;
import com.hua.furnitureManagement.pojo.entry.DeviceDO;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceVO {
    private Long id;
    private String gmtCreate;
    private String gmtModified;
    private String name; //设备名称
    private Integer type; //（0为电视、1为空调、2为冰箱、3洗衣机、4为灯）
    private Integer status; //状态（0关闭，1开启）
    private String location; //设备位置
    private Long addressId; //设备所属地址id
    private Double power; //设备功率（单位：千瓦时）
    private Long devicesStrategyId; //设备策略id
    private String mode; //设备模式
    private Integer brightness; //亮度（灯,电视机,0低，1正常，2高）
    private Long temperature; //温度（空调、洗衣机、冰箱）
    private Integer fanSpeed; //风速（空调，0低速、1正常、2高速）
    private Integer volume; //音量（电视机，0低，1正常，2高）
    private Integer waterLevel; //水位（洗衣机,0低，1中，2高）
    private Integer colorTemperature; //色温（灯,0冷光，1正常，2暖光）
    private Long remainingTime; //洗涤剩余时间（洗衣机）
    private Integer color; //颜色（灯，0白光，1黄光，2白蓝光

    //DO转VO
    public void transformVO(DeviceDO deviceDO) {
        this.id = deviceDO.getId();
        this.gmtCreate = DateUtils.formatDateTime(deviceDO.getGmtCreate());
        this.gmtModified = DateUtils.formatDateTime(deviceDO.getGmtModified());
        this.name = deviceDO.getName();
        this.type = deviceDO.getType();
        this.status = deviceDO.getStatus();
        this.location = deviceDO.getLocation();
        this.addressId = deviceDO.getAddressId();
        this.power = deviceDO.getPower();
        this.devicesStrategyId = deviceDO.getDevicesStrategyId();
        this.mode = deviceDO.getMode();
        this.brightness = deviceDO.getBrightness();
        this.fanSpeed = deviceDO.getFanSpeed();
        this.volume = deviceDO.getVolume();
        this.waterLevel = deviceDO.getWaterLevel();
        this.remainingTime = deviceDO.getRemainingTime();
        this.color = deviceDO.getColor();
        this.colorTemperature = deviceDO.getColorTemperature();
    }
}
