package com.caipiao.live.common.util;

import java.math.BigDecimal;

/**
 * 用于页面展示相关的工具类
 */
public class ViewUtil {

    /**
     * 根据金额区间显示页面字体颜色
     *
     * @param money
     * @return
     */
    public static String getAmountColor(BigDecimal money) {
        if (null == money) {
            return "";
        }
        if (money.compareTo(BigDecimal.valueOf(1)) >= 0 && money.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            return "c-orange";
        }
        if (money.compareTo(BigDecimal.valueOf(1000)) > 0 && money.compareTo(BigDecimal.valueOf(10000)) <= 0) {
            return "c-green";
        }
        if (money.compareTo(BigDecimal.valueOf(10000)) > 0) {
            return "c-red";
        }
        return "";
    }

    /**
     * 保留三位小数，3位之后的全舍
     * @param money 金额
     * @return
     */
    public static BigDecimal getTradeOffAmount(BigDecimal money) {
        if (null == money || money.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("0.000");
        }
        return money.setScale(3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 保留三位小数，2位之后的全舍
     * @param money 金额
     * @return
     */
    public static BigDecimal getTwoAmount(BigDecimal money) {
        if (null == money || money.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("0.000");
        }
        return money.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
