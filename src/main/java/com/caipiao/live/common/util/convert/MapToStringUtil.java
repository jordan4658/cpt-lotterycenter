package com.caipiao.live.common.util.convert;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * map转字符串
 * @author 瑞夫
 * @version 1.0
 * @date 2020/6/2
 */
public class MapToStringUtil {
    /**
     * Map转String工具
     * @param map
     * @param separator 分隔符
     * @param kvSplice  键值拼接符
     * @return
     */
    public static String mapToString(Map<?, ?> map, String separator, String kvSplice) {
        List<String> result = new ArrayList<>();
        map.entrySet().parallelStream().reduce(result, (first, second)->{
            first.add(second.getKey() + kvSplice + second.getValue());
            return first;
        }, (first, second)->{
            if (first == second) {
                return first;
            }
            first.addAll(second);
            return first;
        });

        return StringUtils.join(result, separator);
    }
}
