package com.caipiao.core.library.tool;

public class OrderSnUtil {

    /**
     * 生成订单号
     * @return
     */
    public static String createOrderSn() {
        // 获取随机数
        Integer randomOne = RandomUtil.getRandomOne(5);

        // 获取当前时间戳
        long timeMillis = System.currentTimeMillis();

        // 时间戳 + 随机数
        long randomNum = timeMillis + randomOne;

        return Long.toString(randomNum) + randomOne;
    }

}
