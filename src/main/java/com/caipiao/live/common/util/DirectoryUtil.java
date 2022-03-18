package com.caipiao.live.common.util;

import java.util.concurrent.ConcurrentHashMap;

public class DirectoryUtil {

    //存储用户访问的ip数据  key:ip, value:时间String.valueOf(TimeHelper.time())  ，3分钟统计一次，10分钟算ip离线
    public static ConcurrentHashMap<String,String> ipMap = new ConcurrentHashMap<>();

}
