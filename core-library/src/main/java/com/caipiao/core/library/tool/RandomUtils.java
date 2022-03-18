package com.caipiao.core.library.tool;

import java.util.Random;

/**
 * 获取随机数
 *
 * @author lzy
 * @create 2018-08-06 10:23
 **/
public class RandomUtils {

    private static Random random = new Random();

    /**
     * 获取北京PK10的公式杀号
     * @return
     */
    public static String getBjpksKillNumber() {
        return getKillNumber(10);
    }
    
    /**
     * 获取澳洲PK10的公式杀号
     * @return
     */
    public static String getAuspksKillNumber() {
    	return getKillNumber(10);
    }
    
    /**
     * 获取极速PK10的公式杀号
     * @return
     */
    public static String getSpdpksKillNumber() {
    	return getKillNumber(10);
    }
    /**
     * 获取极速PK10的公式杀号
     * @return
     */
    public static String getLuckpksKillNumber() {
    	return getKillNumber(10);
    }
    /**
     * 获取10分PK10的公式杀号
     * @return
     */
    public static String getTenpksKillNumber() {
    	return getKillNumber(10);
    }
    /**
     * 获取5分PK10的公式杀号
     * @return
     */
    public static String getFivepksKillNumber() {
    	return getKillNumber(10);
    }

    /**
     * 获取幸运飞艇的公式杀号
     * @return
     */
    public static String getXyftKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取六合彩的公式杀号
     * @return
     */
    public static String getLhcKillNumber() {
        return getKillNumber(49);
    }

    /**
     * 获取公式杀号
     * @return
     */
    public static String getKillNumber(int max) {
        StringBuilder killNumber = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String numStr;
            do {
                int num = random.nextInt(max) + 1;
                numStr = (num < 10 ? "0" : "") + num;
            } while (killNumber.toString().contains(numStr));

            if (i == 0) {
                killNumber.append(numStr);
            } else {
                killNumber.append(",").append(numStr);
            }
        }
        return killNumber.toString();
    }
}
