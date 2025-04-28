package com.hua.furnitureManagement.common.enumeration;

public enum DeviceType {
    TEVELEVISION(0, "电视"),
    AIRCONDITIONER(1, "空调"),
    Frigde(2, "冰箱"),
    WASHINGMACHINE(3, "洗衣机"),
    Light(4, "灯");

    private Integer code;
    private String name;

    DeviceType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DeviceType getEnumByCode(Integer code) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.getCode().equals(code)) {
                return deviceType;
            }
        }
        return null;
    }
}
