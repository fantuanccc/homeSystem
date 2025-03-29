package com.hua.furnitureManagement.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceDTO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name; //设备名称
    private Integer type; //（0为电视、1为空调、2为冰箱、3洗衣机、4为灯）
    private Integer status; //状态（0关闭，1开启）
    private String location; //设备位置
    private Integer addressId; //设备所属地址id
    private Double energyConsumption; //设备耗电量
    private Long devicesStrategyId; //设备策略id
    private String mode; //设备模式
    private Integer brightness; //亮度（灯,电视机,0低，1正常，2高）
    private Long temperature; //温度（空调、洗衣机、冰箱）
    private Integer fanSpeed; //风速（空调，0低速、1正常、2高速）
    private Integer volume; //音量（电视机，0低，1正常，2高）
    private Integer waterLevel; //水位（洗衣机,0低，1中，2高）
    private Integer colorTemperatuer; //色温（灯,0冷光，1正常，2暖光）
    private Long remainingTime; //洗涤剩余时间（洗衣机）
    private Integer color; //颜色（灯，0白光，1黄光，2白蓝光
}
