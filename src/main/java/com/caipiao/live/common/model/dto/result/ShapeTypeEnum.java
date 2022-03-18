package com.caipiao.live.common.model.dto.result;

public enum ShapeTypeEnum {
    BIG("大"),
    SMALL("小"),
    SINGULAR("单"),
    QUANTITY("双"),
    PRIME("质"),
    COMPOSITE("合");

    private String value;

    ShapeTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
