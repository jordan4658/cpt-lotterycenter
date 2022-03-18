package com.caipiao.live.common.enums.lottery;

/**
 * 彩票活动时间类型
 */
public enum ActivityTimeTypeEnum {
    DURATIVE(0, "持续类型"),
    FIXED(1, "定时启停类型");

    private int value;
    private String desc;

    ActivityTimeTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 是否是持续时间类型
     *
     * @param value
     * @return
     */
    public static boolean isDurativeTimeType(Integer value) {
        return null == value || DURATIVE.getValue() == value;
    }

    /**
     * 是否是定时启停时间类型
     *
     * @param value
     * @return
     */
    public static boolean isFixedTimeType(Integer value) {
        return null != value && FIXED.getValue() == value;
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
