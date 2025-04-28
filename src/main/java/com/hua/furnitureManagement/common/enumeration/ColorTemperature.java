package com.hua.furnitureManagement.common.enumeration;

public enum ColorTemperature {
    COLD(0, "冷光"),
    NORMAL(1,"正常光"),
    WARM(2,"暖光");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private ColorTemperature(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
