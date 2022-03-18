package com.caipiao.live.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:    IdListUtils
 * Package:    com.caipiao.live.common.util
 * Description:
 * Datetime:    2020/5/25   10:06
 * Author:   木鱼
 */
public class IdListUtils {

    public static String idList2String(List<Integer> idList) {
        return StringUtils.join(idList.toArray(), ",");
    }

    public static List<Integer> string2IdList(String ids) {
        return Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

}
