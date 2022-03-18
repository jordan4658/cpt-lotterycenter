package com.caipiao.core.library.tool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 生成号码工具类
 *
 * @author lzy
 * @create 2018-07-06 15:46
 **/
public class GenerateCodeUtils {


    /**
     * 生成卡号
     * @param count 生成数量
     * @param codeLen 卡号长度,必须大于15位小于26
     * @return
     */
    public static Set<String> generateCardNumber(int count, int codeLen) {
        Set<String> codes = new HashSet<>(count);
        if (count > 0 && codeLen > 15 && codeLen < 26) {
            for (int i = 0; i < count; i++) {
                codes.add(generateNumber(codeLen));
            }
            if (codes.size() < count) {
                while (true) {
                    codes.add(generateNumber(codeLen));
                    if (codes.size() == count) {
                        break;
                    }
                }
            }
        }
        return codes;
    }


    /**
     * 生成卡号
     * @param codeLen 卡号长度,必须大于15位小于26
     * @return
     */
    public static String generateNumber(int codeLen) {
        String time = System.currentTimeMillis() + "";
        String nano = System.nanoTime() + "";
        int len = codeLen - time.length() + 1;
        if (len < nano.length() - 7) {
            return time.substring(1) + nano.substring(7, len + 7);
        } else {
            return time.substring(1) + nano.substring(nano.length() - len);
        }
    }

    /**
     * 随机生成字符串
     * @param length 产生的字符串长度
     * @return
     */
    public static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即59位；去掉数字0大写和小写字母oO
        String str="QWERTYUPASDFGHJKLZXCVBNM23456789";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for(int i = 0; i < length; ++i){
            //产生0-61的数字
            int number=random.nextInt(str.length()-1);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

}
