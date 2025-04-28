package com.hua.furnitureManagement.pojo.entry;

import lombok.Data;

import java.util.Date;

@Data
public class StrategyDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name; // 全局策略名称
    private String message; // 全局策略内容
    private Integer type; // 策略种类 0非自定义 1自定义
    private Long addressId; // 地址id

    @Override
    public String toString() {
        return "StrategyDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", addressId=" + addressId +
                '}';
    }
}
