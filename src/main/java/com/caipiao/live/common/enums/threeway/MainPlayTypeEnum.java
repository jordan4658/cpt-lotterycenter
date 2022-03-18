package com.caipiao.live.common.enums.threeway;

/**
 * @ClassName: ThreeWayConversionUtils
 * @Description: 下三路玩法细分类型枚举
 * @author: HANS
 * @date: 2019年6月11日 下午4:11:13
 */
public enum MainPlayTypeEnum {
    SINGLE(1, "单"),
    DOUBLE(2, "双"),
    BIG(3, "大"),
    SMALL(4, "小"),
    POULTRY(5, "家禽"),
    BEAST(6, "野兽"),
    DRAGON(7, "龙"),
    TIGER(8, "虎"),
    ;

    private int value;
    private String desc;

    MainPlayTypeEnum(int value, String desc) {
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
