package com.caipiao.live.common.enums;

/**
 * 推单结束状态
 */
public enum PushOrderFinishStatusEnum {

    NOT_DRAW(1, "推单未开奖"),
    ABORT_DRAW(2, "已被中止"),
    BALANCE_DRAW_FINISHED(3, "已开奖结算");

    PushOrderFinishStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
