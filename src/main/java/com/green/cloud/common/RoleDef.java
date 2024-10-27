package com.green.cloud.common;

public enum RoleDef {

    SUPER_ADMI(1, "超级管理员"),
    ADMI(2, "管理员"),
    USER(3, "普通用户");

    private final Integer value;
    private final String name;


    RoleDef(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getName(){return this.name;}
}
