package com.hua.furnitureManagement.pojo.vo;

import lombok.Data;

@Data
public class StrategyVO {
    private Long id;
    private String gmtCreate;
    private String gmtModified;
    private String name; // 全局策略名称
    private String message; // 全局策略内容
    private Integer type; // 策略种类 0非自定义 1自定义
    private Long addressId; // 地址id
}
