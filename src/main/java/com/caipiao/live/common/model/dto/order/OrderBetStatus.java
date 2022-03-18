package com.caipiao.live.common.model.dto.order;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBetStatus {

    /** 中奖 */
    public static final String WIN = "WIN";
    /** 未中奖 */
    public static final String NO_WIN = "NO_WIN";
    /** 打和 */
    public static final String HE = "HE";
    /** 等待开奖 */
    public static final String WAIT = "WAIT";
    /** 撤单 */
    public static final String BACK = "BACK";

    private static final Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put(WIN, "中奖");
        map.put(NO_WIN, "未中奖");
        map.put(HE, "打和");
        map.put(WAIT, "等待开奖");
        map.put(BACK, "撤单");
    }

    /**
     * 获取未下注状态
     *
     * @return
     */
    public static List<String> getNoOrderStatus() {
        return Arrays.asList(WAIT, BACK);
    }

    /**
     * 获取相应内容
     *
     * @param status 状态
     * @return
     */
    public static String getState(String status) {
        return map.containsKey(status) ? map.get(status) : "";
    }

}
