package com.hua.furnitureManagement.pojo.entry;

import lombok.Data;

import java.util.Date;

@Data
public class AddressDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name; // 地址名称
    private String unitNumber; // 单元号
    private String phoneNumber; // 户主手机号
    private Long strategyId; // 策略ID
    private String key; // 密钥
    private Integer keyStatus; // 密钥状态
}
