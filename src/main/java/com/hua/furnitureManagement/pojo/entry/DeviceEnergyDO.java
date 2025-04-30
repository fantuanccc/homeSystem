package com.hua.furnitureManagement.pojo.entry;


import com.hua.furnitureManagement.common.util.DateUtils;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

@Data
public class DeviceEnergyDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private Long devicesId;  //设备ID
    private String ds; //设备开启日期
    private Long time; //当天设备耗时
    private Long count; //当天设备使用次数
    private Double num; //当天设备使用次数
    private Long addressId; //住址id

    @Override
    public String toString() {
        return "DeviceEnergyDO{" +
                "devicesId=" + devicesId +
                ", ds=" + ds +
                ", time=" + time +
                ", count=" + count +
                ", num=" + num +
                ", addressId=" + addressId +
                '}';
    }

    // 完善能耗表的设备信息，并初始化其他信息
    public void DeviceDOTransformEnergyDOInit(DeviceDO deviceDO) {
        this.devicesId = deviceDO.getId();
        this.addressId = deviceDO.getAddressId();
        this.time = 0L;
        this.count = 0L;
        this.num = 0.0;
        this.ds = DateUtils.getCurrentDateOnly();
    }
}
