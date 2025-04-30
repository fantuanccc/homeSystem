package com.hua.furnitureManagement.pojo.entry;

import lombok.Data;

import java.util.Date;

@Data
public class AdminDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String username;
    private String password;
    private String phoneNumber;

    @Override
    public String toString() {
        return "AdminDO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
