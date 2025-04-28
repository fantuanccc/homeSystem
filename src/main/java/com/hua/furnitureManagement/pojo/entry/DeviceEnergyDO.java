package com.hua.furnitureManagement.pojo.entry;


import lombok.Data;

import java.util.Date;

@Data
public class DeviceEnergyDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private Long devicesId;  //设备ID
    private Date ds; //设备开启日期
    private Long time; //当天设备耗时
    private Long count; //当天设备使用次数
    private Long num; //当天设备使用次数
    private Long addressId; //住址id
}
