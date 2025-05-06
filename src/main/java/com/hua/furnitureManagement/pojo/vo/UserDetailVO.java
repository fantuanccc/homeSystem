package com.hua.furnitureManagement.pojo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDetailVO {
    private String username; // 用户名
    private String phoneNumber; // 手机号
    List<Map<String, Object>> addressList; // 地址信息

    @Override
    public String toString() {
        return "UserDetailVO{" +
                "username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
