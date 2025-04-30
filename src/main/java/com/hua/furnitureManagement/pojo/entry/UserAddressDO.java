package com.hua.furnitureManagement.pojo.entry;

import lombok.Data;

@Data
public class UserAddressDO {
    private Long id;
    private Long userId;
    private Long addressId;
    private Integer isOwner;

    @Override
    public String toString() {
        return "UserAddressDO{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                ", isOwner=" + isOwner +
                '}';
    }
}
