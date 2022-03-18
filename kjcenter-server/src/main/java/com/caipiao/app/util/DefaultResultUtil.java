package com.caipiao.app.util;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.tool.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认空结果返回工具类
 *
 * @author Hans
 * @create 2019-03-27 20:07
 */
public class DefaultResultUtil {

    /**
     * 定义空结果
     */
    public static Map<String, Object> getNullResult() {
        // 定义返回结果
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), Constants.DEFAULT_NULL);
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), Constants.DEFAULT_NULL);
        result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), Constants.DEFAULT_NULL);
        result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), Constants.DEFAULT_NULL);
        return result;
    }


}
