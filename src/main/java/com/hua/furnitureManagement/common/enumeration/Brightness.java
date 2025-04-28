package com.hua.furnitureManagement.common.enumeration;

public enum Brightness {
    LOW(0, "低亮度"),
    MEDIUM(1,"正常亮度"),
    HIGH(2,"高亮度");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private Brightness(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}

