package com.hua.furnitureManagement.pojo.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String username;
    private Integer isOwner; // 0: 普通用户, 1: 户主
    private Long addressId;
    private String addressName;
    private String unitNumber;
    private String phoneNumber;
    private String token;
}
