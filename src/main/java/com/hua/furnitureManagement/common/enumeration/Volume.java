package com.hua.furnitureManagement.common.enumeration;

public enum Volume {
    LOW(0, "低音量"),
    MEDIUM(1,"正常音量"),
    HIGH(2,"高音量");

    private Integer code;
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private Volume(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
