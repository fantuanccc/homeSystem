package com.hua.furnitureManagement.common.enumeration;


public enum DeviceTypeEnum {
    TEVELEVISION(0, "电视"),
    AIRCONDITIONER(1, "空调"),
    Frigde(2, "冰箱"),
    WASHINGMACHINE(3, "洗衣机"),
    Light(4, "灯");

    private Integer code;
    private String name;

    DeviceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DeviceTypeEnum getEnumByCode(Integer code) {
        for (DeviceTypeEnum deviceType : DeviceTypeEnum.values()) {
            if (deviceType.getCode().equals(code)) {
                return deviceType;
            }
        }
        return null;
        }
}
