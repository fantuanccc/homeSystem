package com.hua.furnitureManagement.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceStrategyDTO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name; //策略名称
    private Integer energyConsumptionLevel; //能耗等级（0低，1正常，2高）
    private Integer devicesType; //设备类型（0为电视、1为空调、2为冰箱、3洗衣机、4为灯)
    private Integer brightness; //亮度（0低，1正常，2高）
    private Long temperature; //温度
    private String mode; //模式
    private Integer fanSpeed; //风速（空调，0低速、1正常、2高速）
    private Integer volume; //音量（电视机，0低，1正常，2高）
    private Integer waterLevel; //水位（洗衣机,0低，1中，2高）
    private Integer colorTemperature; //色温（灯,0冷光，1正常，2暖光）
    private Long remainingTime; //洗涤剩余时间（洗衣机）
    private Integer color; //颜色（灯，0白光，1黄光，2白蓝光
    private Long addressId; //地址ID
}
