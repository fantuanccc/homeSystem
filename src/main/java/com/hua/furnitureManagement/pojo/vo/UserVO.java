package com.hua.furnitureManagement.pojo.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String role;
    private Integer isAdmin;
    private Long addressId;
    private String phoneNumber;
}
