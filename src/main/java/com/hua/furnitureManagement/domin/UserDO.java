package com.hua.furnitureManagement.domin;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;
    private String password;
    private Integer isAdmin;
    private Integer isDeleted;
    private Long addressId;
    private String phoneNumber;
}
