package com.caipiao.live.common.enums.threeway;

/**
 * @ClassName: ThreeWayConversionUtils
 * @Description: 下三路彩票类型枚举
 * @author: HANS
 * @date: 2020年01月06日 下午4:11:13
 */
public enum ThreeWayCaipiaoTypeEnum {
    SPECIAL_CODE(1101, "特码"),
    POSITIVE_CODE(2202, "正码"),
    POSITIVE_ONE(3303, "正码一"),
    POSITIVE_TWO(4404, "正码二"),
    POSITIVE_THREE(5505, "正码三"),
    POSITIVE_FOUR(6606, "正码四"),
    POSITIVE_FIVE(7707, "正码五"),
    POSITIVE_SIX(8808, "正码六"),
    ;

    private int value;
    private String desc;

    ThreeWayCaipiaoTypeEnum(int value, String desc) {
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
