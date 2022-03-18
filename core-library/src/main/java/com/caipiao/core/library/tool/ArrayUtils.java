package com.caipiao.core.library.tool;

import java.util.*;

/**
 * 数组工具类
 *
 * @author lzy
 * @create 2018-07-26 23:35
 **/
public class ArrayUtils {

    /**
     * 排序一个数组（数组元素有重复的）,并且记住新数组的元素在原数组中的位置
     * @param ary
     * @return
     */
    public static int[] getSortIndex(int[] ary) {
        int[] index = new int[ary.length];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
        for (int i = 0; i < ary.length-1; i++) {
            for (int j = i+1; j < ary.length; j++) {
                if(ary[i] < ary[j]){
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

    public static void main(String[] args) {
        //java排序一个数组（数组元素有重复的）,并且记住新数组的元素在原数组中的位置
        /*int[] ary = {1,2,3,4,1,2};
        int[] index = getSortIndex(ary);
        System.out.println(Arrays.toString(ary));
        System.out.println(Arrays.toString(index));*/


        String aa = ",,03,,05,";
        String[] split = aa.split(",");
        int length = split.length;
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            System.out.println(i + " " + split[i]);
        }
       /* StringBuffer winNum = new StringBuffer();
        System.out.println(winNum.length());*/
//        winNum.append("01").append(",");
//        System.out.println(winNum.substring(0, winNum.length() - 1));
    }

}
