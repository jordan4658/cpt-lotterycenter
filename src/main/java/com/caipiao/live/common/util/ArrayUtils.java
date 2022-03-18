package com.caipiao.live.common.util;

import java.util.ArrayList;

/**
 * 数组工具类
 *
 * @author lzy
 * @create 2018-07-26 23:35
 **/
public class ArrayUtils {

    /**
     * 排序一个数组（数组元素有重复的）,并且记住新数组的元素在原数组中的位置
     *
     * @param ary
     * @return
     */
    public static int[] getSortIndex(int[] ary) {
        int[] index = new int[ary.length];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
        for (int i = 0; i < ary.length - 1; i++) {
            for (int j = i + 1; j < ary.length; j++) {
                if (ary[i] < ary[j]) {
                    int temp = ary[i];
                    int p = index[i];
                    ary[i] = ary[j];
                    index[i] = index[j];
                    ary[j] = temp;
                    index[j] = p;
                }
            }
        }
        return index;

    }

    /**
     * 排序一个数组（数组元素有重复的）,并且记住新数组的元素在原数组中的位置
     *
     * @param ary
     * @param nul 如果ary[i]为空的替代值
     * @return
     */
    public static int[] getSortIndex(Integer[] ary, int nul) {
        int[] index = new int[ary.length];
        for (int i = 0; i < index.length; i++) {
            if (ary[i] == null) {
                index[i] = nul;
                continue;
            }
            index[i] = ary[i];
        }
        return getSortIndex(index);

    }

    public static ArrayList<Integer> toList(int[] arr) {
        if (arr != null && arr.length > 0) {
            int len = arr.length;
            ArrayList<Integer> arrayList = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                arrayList.add(arr[i]);
            }
            return arrayList;
        }
        return null;
    }

    public static String toString(Object[] arr) {
        return toString(arr, ",");
    }

    public static String toString(Object[] arr, String separator) {
        if (null == arr || arr.length == 0) {
            return "";
        }
        separator = null == separator || "".equals(separator.trim()) ? "," : separator.trim();
        StringBuffer result = new StringBuffer();
        for (Object obj : arr) {
            result.append(obj).append(separator);
        }
        return result.substring(0, result.length() - 1);
    }

}
