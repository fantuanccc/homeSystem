package com.hua.furnitureManagement.common.enumeration;

public enum WaterLevel {
    LOW(0, "低水位"),
    MEDIUM(1,"正常水位"),
    HIGH(2,"高水位");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private WaterLevel(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
