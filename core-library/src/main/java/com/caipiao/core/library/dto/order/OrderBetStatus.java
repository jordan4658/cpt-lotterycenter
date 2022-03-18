package com.caipiao.core.library.dto.order;


public class OrderBetStatus {

    /**
     * 中奖
     */
    public static final String WIN = "WIN";

    /**
     * 未中奖
     */
    public static final String NO_WIN = "NO_WIN";

    /**
     * 打和
     */
    public static final String HE = "HE";

    /**
     * 等待开奖
     */
    public static final String WAIT = "WAIT";

    /**
     * 撤单
     */
    public static final String BACK = "BACK";

    /**
     * 获取相应内容
     * @param status 状态
     * @return
     */
    public static String getState(String status) {
        switch (status) {
            case WIN:
                return "中奖";
            case NO_WIN:
                return "未中奖";
            case HE:
                return "打和";
            case WAIT:
                return "等待开奖";
            case BACK:
                return "撤单";
            default:
                return "";
        }
    }

}
