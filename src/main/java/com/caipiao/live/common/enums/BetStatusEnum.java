package com.caipiao.live.common.enums;

/**
 * 投注状态
 */
public enum BetStatusEnum {
    DISABLE(0, "不允许投注"),
    ENABLE(1, "允许投注");
    private int code;
    private String desc;

    BetStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean isEnableStatus(int status) {
        return BetStatusEnum.ENABLE.code == status;
    }

    public static boolean isDisableStatus(int status) {
        return BetStatusEnum.DISABLE.code == status;
    }

    public boolean isEnableStatus() {
        return BetStatusEnum.isEnableStatus(this.code);
    }

    public boolean isDisableStatus() {
        return BetStatusEnum.isDisableStatus(this.code);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
