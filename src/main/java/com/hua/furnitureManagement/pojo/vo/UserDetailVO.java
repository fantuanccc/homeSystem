package com.hua.furnitureManagement.pojo.vo;

import lombok.Data;

@Data
public class UserDetailVO {
    private String username;
    private String isOwner; // 0: 普通用户, 1: 户主
    private String phoneNumber;
    private String addressName; // 小区名
    private String unitNumber; // 单元号
}
