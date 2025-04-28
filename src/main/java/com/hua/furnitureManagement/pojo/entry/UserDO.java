package com.hua.furnitureManagement.pojo.entry;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String username;
    private String password;
    private Integer isOwner;
    private Integer isDeleted;
    private Long addressId;
    private String phoneNumber;
}
