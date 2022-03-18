package com.caipiao.core.library.tool;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 来源工具类（IOS | Android | WEB）
 */
public class SourceUtil {

    /**
     * 获取客户端来源信息
     * @param request HttpServletRequest
     * @return
     */
    public static String getClientSource(HttpServletRequest request) {
        // 获取请求头信息
        String requestHeader = request.getHeader("user-agent");
        System.out.println("======== 请求头信息："+requestHeader);

        // 判断请求头是否位空
        if (StringUtils.isBlank(requestHeader)) {
            return "";
        }
        // 将请求头全部转小写
        requestHeader = requestHeader.toLowerCase();

        // 判断客户端类型
        if (requestHeader.contains("android")) {
            return "Android";
        } else if (requestHeader.contains("ios")) {
            return "IOS";
        } else {
            return "WEB";
        }
    }


}
