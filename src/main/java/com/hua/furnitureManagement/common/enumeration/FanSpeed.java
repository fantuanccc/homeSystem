package com.hua.furnitureManagement.common.enumeration;

public enum FanSpeed {
    LOW(0, "低速"),
    MEDIUM(1,"正常速"),
    HIGH(2,"高速");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private FanSpeed(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
