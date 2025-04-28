package com.hua.furnitureManagement.common.enumeration;

public enum EnergyConsumptionLevel {
    LOW(0, "低能耗"),
    MEDIUM(1,"正常能耗"),
    HIGH(2,"高能耗");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private EnergyConsumptionLevel(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
