package com.caipiao.core.library.tool;

/**
 * 数学统计的一些方法
 *
 * @author lzy
 * @create 2018-09-18 16:36
 **/
public class CountUtils {

    /**
     * Cnm排序组合
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
        return fenzi/fenmu;
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
        return (int) (fenzi/fenmu);
    }

    public static void main(String[] args) {
//        System.out.println(countCnm(3, 5));

        String aa = "1-3球";
        System.out.println(Integer.valueOf(aa.charAt(2) + ""));
    }
}
