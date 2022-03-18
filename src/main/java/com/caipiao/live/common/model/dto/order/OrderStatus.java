package com.caipiao.live.common.model.dto.order;

public class OrderStatus {

    /**
     * 正常
     */
    public static final String NORMAL = "NORMAL";

    /**
     * 撤单
     */
    public static final String BACK = "BACK";

    /**
     * 获取相应内容
     *
     * @param status 状态
     * @return
     */
    public static String getStatus(String status) {
        switch (status) {
            case NORMAL:
                return "正常";
            case BACK:
                return "撤单";
            default:
                return "";
        }
    }

}
