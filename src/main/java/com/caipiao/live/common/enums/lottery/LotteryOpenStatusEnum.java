package com.caipiao.live.common.enums.lottery;

import java.util.ArrayList;
import java.util.List;

/**
 * 开奖状态枚举
 */
public enum LotteryOpenStatusEnum {
    WAIT("WAIT", "等待开奖"),
    AUTO("AUTO", "自动开奖"),
    HANDLE("HANDLE", "手动开奖");

    private String key;
    private String value;

    LotteryOpenStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 已开奖状态
     *
     * @return
     */
    public static List<String> getOpenStatus() {
        List<String> list = new ArrayList<>();
        list.add(AUTO.name());
        list.add(HANDLE.name());
        return list;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
