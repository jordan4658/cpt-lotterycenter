package com.caipiao.live.common.util.lottery;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ListSortUtils
 * @Description: 集合工具类
 * @author: HANS
 * @date: 2019年5月13日 下午8:43:16
 */
public class ListSortUtils {

    /**
     * @Title: sort
     * @Description: 降序排序Map类型的
     * @author HANS
     * @date 2019年5月13日下午8:43:44
     */
    public static void sort(List<Map<String, Object>> data) {

        Collections.sort(data, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer a = (Integer) o1.get("dragonSum");
                Integer b = (Integer) o2.get("dragonSum");

                return b - a;
            }
        });
    }

}
