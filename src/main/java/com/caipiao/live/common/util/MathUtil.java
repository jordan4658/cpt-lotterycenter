package com.caipiao.live.common.util;

import com.caipiao.live.common.constant.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;

public class MathUtil {

    /** 默认的除法精确度 */
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 精确加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和(BigDecimal)
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    //   转为 BigDecimal 截取两位
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 精确减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差(BigDecimal)
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }

    /**
     * 精确乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积(BigDecimal)
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ONE;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        return v1.multiply(v2);
    }

    public static BigDecimal divide(Integer v1, Integer v2) {
        return divide(BigDecimal.valueOf(v1), BigDecimal.valueOf(v2), DEF_DIV_SCALE);
    }

    public static BigDecimal divide(Integer v1, Integer v2, int scale) {
        return divide(BigDecimal.valueOf(v1), BigDecimal.valueOf(v2), scale);
    }

    /**
     * ( 相对 )精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }

        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }

        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 精确加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和(String)
     */
    public static String add(String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (isBlank(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(add(b1, b2));
    }


    /**
     * 精确减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差(String)
     */
    public static String subtract(String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (isBlank(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(subtract(b1, b2));
    }

    /**
     * 精确乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积(String)
     */
    public static String multiply(String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "1";
        }
        if (isBlank(v2)) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(multiply(b1, b2));
    }

    /**
     * ( 相对 )精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(String)
     */
    public static String divide(String v1, String v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(String)
     */
    public static String divide(String v1, String v2, Integer scale) {
        if (null == v1) {
            return "0";
        }
        if (null == v2) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(divide(b1, b2, scale));
    }

    /**
     * 精确加法运算 , 计算多个数值总和 , 若其中有null值则忽略
     *
     * @param valList 被加数集合
     * @return 两个参数的和(BigDecimal)
     */
    public static BigDecimal sum(BigDecimal v1, BigDecimal... valList) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        for (BigDecimal val : valList) {
            if (null != val) {
                v1 = v1.add(val);
            }
        }
        return v1;
    }

    /**
     * 精确加法运算 , 计算多个数值总和 , 若其中有null值则忽略
     *
     * @param valList 被加数集合
     * @return 两个参数的和(String)
     */
    public static String sum(String v1, String... valList) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        for (String val : valList) {
            if (!isBlank(val)) {
                b1 = add(b1, new BigDecimal(val.trim())).setScale(2,BigDecimal.ROUND_DOWN);
            }
        }
        return String.valueOf(b1);
    }

    /**
     * 平均数
     *
     * @param valList
     * @return
     */
    public static BigDecimal avg(BigDecimal... valList) {
        if (null != valList && valList.length != 0) {
            return divide(sum(BigDecimal.ZERO, valList), new BigDecimal(valList.length));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 平均数
     *
     * @param valList
     * @return
     */
    public static String avg(String... valList) {
        if (null != valList && valList.length != 0) {
            return divide(sum("0", valList), String.valueOf(valList.length));
        }
        return "0";
    }

    /**
     * 最大值
     *
     * @param v1
     * @param valList
     * @return
     */
    public static BigDecimal max(BigDecimal v1, BigDecimal... valList) {
        BigDecimal max = v1;
        if (null == valList || valList.length == 0) {
            return max;
        }
        for (BigDecimal val : valList) {
            if (null != val && val.compareTo(max) > 0) {
                max = val;
            }
        }
        return max;
    }

    /**
     * 最大值
     *
     * @param valList
     * @return
     */
    public static BigDecimal maxArr(BigDecimal... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }

        return max(valList[0], valList);
    }

    /**
     * 最小值
     *
     * @param v1
     * @param valList
     * @return
     */
    public static BigDecimal min(BigDecimal v1, BigDecimal... valList) {
        BigDecimal min = v1;
        if (null == valList || valList.length == 0) {
            return min;
        }
        for (BigDecimal val : valList) {
            if (null != val && val.compareTo(min) < 0) {
                min = val;
            }
        }
        return min;
    }

    /**
     * 最小值
     *
     * @param valList
     * @return
     */
    public static BigDecimal minArr(BigDecimal... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return min(valList[0], valList);
    }

    /**
     * 最大值
     *
     * @param v1
     * @param valList
     * @return
     */
    public static String max(String v1, String... valList) {
        if (isBlank(v1)) {
            return null;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal maxBd = new BigDecimal(v1.trim());

        for (String val : valList) {
            if (!isBlank(val) && new BigDecimal(val).compareTo(maxBd) > 0) {
                maxBd = new BigDecimal(val);
            }
        }
        return String.valueOf(maxBd);
    }

    /**
     * 最大值
     *
     * @param valList
     * @return
     */
    public static String maxArr(String... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return max(valList[0], valList);
    }

    /**
     * 最小值
     *
     * @param v1
     * @param valList
     * @return
     */
    public static String min(String v1, String... valList) {
        if (isBlank(v1)) {
            return null;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal minBd = new BigDecimal(v1.trim());

        for (String val : valList) {
            if (!isBlank(val) && new BigDecimal(val).compareTo(minBd) < 0) {
                minBd = new BigDecimal(val);
            }
        }
        return String.valueOf(minBd);
    }

    /**
     * 最小值
     *
     * @param valList
     * @return
     */
    public static String minArr(String... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return min(valList[0], valList);
    }

    /**
     * 排列组合C(n,m)
     *
     * @param
     * @return
     */
    public static int getCnm(int n, int m) {
        int total = 1;
        int chushu = 1;
        for (int i = 0; i < m; i++) {
            total = total * (n - i);
        }
        for (int i = 0; i < m; i++) {
            chushu = chushu * (m - i);
        }
        return total / chushu;
    }

    /**
     * 排列组合C(n,m)
     *
     * @param
     * @return
     */
    public static int getAnm(int n, int m) {
        int total = 1;
        for (int i = 0; i < m; i++) {
            total = total * (n - i);
        }
        return total;
    }


    /**
     * 判断字符串是否为空(不依赖第三方)
     *
     * @param str
     * @return
     */
    private static boolean isBlank(String str) {
        return null == str || str.trim().length() == 0;
    }

    /**
     * 判断是否是由同一字符构成的，true代表由同一字符构成的 false反之
     */
    public static boolean isSameSymbol(String s) {
        boolean flag = false;
        //当s为空字符串或者null,认为不是由同一字符构成的
        if (null == s || s.trim().length() <= 1) {
            return flag;
        }
        char chacter = s.charAt(0);
        for (int i = 1; i <= s.length() - 1; i++) {
            if (chacter != s.charAt(i)) {
                flag = false;
                return flag;
            }
        }
        flag = true;
        return flag;
    }

    /**
     * 判断数字是否是顺子
     *
     * @param s
     * @return
     */
    public static boolean isShunZi(String s) {
        boolean flag = false;
        if (null == s || s.trim().length() <= 1) {
            return flag;
        }
        char[] chars1 = s.toCharArray();
        char[] chars2 = s.toCharArray();
        Arrays.sort(chars1);
        for (int i = 0; i < s.length(); i++) {
            if (chars1[i] != chars2[i]) {
                return false;
            }
        }

        int num1 = Integer.valueOf(chars1[0]);
        int num2 = Integer.valueOf(chars1[s.length() - 1]);
        if ((num2 - num1) / (s.length() - 1) == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * Cnm排序组合
     *
     * @param n 下标
     * @param m 上标
     * @return
     */
    public static int countCnm(int n, int m) {
        if (n < m) {
            return 0;
        } else if (n == m) {
            return 1;
        }
        int fenzi = n;
        for (int i = n - 1, count = n - m; i > count; i--) {
            fenzi *= i;
        }
        int fenmu = m;
        for (int k = m - 1; k > 1; k--) {
            fenmu *= k;
        }
        return fenzi / fenmu;
    }

    public static int countCnmIsLong(long n, long m) {
        if (n < m) {
            return 0;
        } else if (n == m) {
            return 1;
        }
        long fenzi = n;
        for (long i = n - 1, count = n - m; i > count; i--) {
            fenzi *= i;
        }
        long fenmu = m;
        for (long k = m - 1; k > 1; k--) {
            fenmu *= k;
        }
        return (int) (fenzi / fenmu);
    }


    //字符串 百分比
    public static String StringtoBaiFen(String a, String b) {
        if(Constants.STR_ZERO.equals(b)){
            return "0.00%";
        }
        String baifen = "";
        Double y = Double.valueOf(a);
        Double z = Double.valueOf(b);
        Double fen = y / z;
        DecimalFormat df1 = new DecimalFormat("0.00%"); // ##.00%
        baifen = df1.format(fen);
        return baifen;
    }

}
