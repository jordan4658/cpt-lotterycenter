package com.caipiao.live.common.enums.threeway;

/**
 * @ClassName: ThreeWayConversionUtils
 * @Description: 下三路玩法算法枚举
 * @author: HANS
 * @date: 2019年6月11日 下午4:11:13
 */
public enum ThreeWayNameEnum {
    MAIN_WAY(1, "主路"),
    LARGE_SEED_WAY(2, "大眼仔"),
    SMALL_WAY(3, "小路"),
    STRONG_WAY(4, "小强路");

    private int value;
    private String desc;

    ThreeWayNameEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
