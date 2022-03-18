package com.caipiao.live.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    private static final Logger logger = LoggerFactory.getLogger(PatternUtil.class);

    /**
     * 昵称只能汉字 数字 中文 下划线
     *
     * @param matcherStr
     * @return
     */
    public static boolean checkUserNickName(String matcherStr) {
        String regex = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
        Matcher matcher = Pattern.compile(regex).matcher(matcherStr);
        return !matcher.matches();
    }

    /**
     * 校验账号合法性
     *
     * @param matcherStr
     * @return
     */
    public static boolean checkAccount(String matcherStr) {
        if (Pattern.compile("[a-zA-Z]{1,12}").matcher(matcherStr).matches()) {
            logger.info("checkAccount is whole characters. matcherStr:{}", matcherStr);
            return false;
        }
        if (Pattern.compile("[0-9]{1,12}").matcher(matcherStr).matches()) {
            logger.info("checkAccount is whole numbers. matcherStr:{}", matcherStr);
            return false;
        }
        String regex = "^[a-zA-Z0-9]{6,12}$";
        Matcher matcher = Pattern.compile(regex).matcher(matcherStr);
        boolean result = matcher.matches();
        logger.info("checkAccount matcherStr:{}, result:{}", matcherStr, result);
        return result;
    }

    /**
     * 检查中文字符长度
     */
    public static boolean checkByteLength(String param, int validLength) {
        int byteLength = 0;
        try {
            byteLength = param.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            logger.error("checkByteLength occur error.", e);
        }
        return byteLength <= validLength;
    }

    /**
     * 判断是否是数字（小数）
     *
     * @param matcherStr
     * @return
     */
    public static boolean isNumber(String matcherStr) {
        String regex = "^[0-9]+(.[0-9]+)?$";
        Matcher matcher = Pattern.compile(regex).matcher(matcherStr);
        return matcher.matches();
    }

    /**
     * 是否是中文
     *
     * @param matcherStr
     * @return
     */
    public static boolean isChinese(String matcherStr) {
        String regex = "^[\\u4e00-\\u9fa5]*$";
        Matcher matcher = Pattern.compile(regex).matcher(matcherStr);
        return matcher.matches();
    }

    public static boolean isMobile(String mobile) {
        return isSpecialMobile(mobile, "^1[3456789]\\d{9}$");
    }

    /**
     * 判定是否为虚拟号段，这里特指170/171号段
     *
     * @param mobile
     * @return
     */
    public static boolean is170171Mobile(String mobile) {
        return isSpecialMobile(mobile, "^17[01]\\d{8}$");
    }

    /**
     * 判定是否为虚拟号段
     *
     * @param mobile
     * @return
     */
    public static boolean isVirtualMobile(String mobile) {
        return isSpecialMobile(mobile, "^(165|167|170|171|172|174)\\d{8}$");
    }
    
    /**
     * 判断是否为指定格式的手机号
     *
     * @param mobile 手机号
     * @param regex  指定正则
     * @return
     */
    public static boolean isSpecialMobile(String mobile, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(mobile);
        return matcher.matches();
    }
    
    public static void main(String[] args){
        System.out.println(is170171Mobile("17100500200"));
    }

}
