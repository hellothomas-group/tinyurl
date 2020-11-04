package com.hellothomas.assignment.constants.enums;

public enum UrlTypeEnum {
    // 系统
    SYSTEM(0),

    // 自定义
    CUSTOM(1);

    private int value;

    UrlTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
