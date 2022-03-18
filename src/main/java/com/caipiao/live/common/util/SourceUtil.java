package com.caipiao.live.common.util;

import com.caipiao.live.common.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 来源工具类（IOS | Android | WEB）
 */
public class SourceUtil {

    private static final Logger logger = LoggerFactory.getLogger(SourceUtil.class);

    /**
     * 获取客户端来源信息
     *
     * @param request HttpServletRequest
     * @return
     */
    public static String getClientSource(HttpServletRequest request) {
        // 获取请求头信息
        String requestHeader = request.getHeader("user-agent");
        logger.info("getClientSource.user-agent:{}", requestHeader);

        // 判断请求头是否位空
        if (StringUtils.isBlank(requestHeader)) {
            return "";
        }
        // 将请求头全部转小写
        requestHeader = requestHeader.toLowerCase();

        // 判断客户端类型
        if (requestHeader.contains("android") && !requestHeader.contains("applewebkit")) {
            return Constants.CLIENT_SOURCE_ANDROID;
        } else if (requestHeader.contains("ios") || requestHeader.contains("iphone") || requestHeader.contains("ipod") || requestHeader.contains("ipad")) {
            return Constants.CLIENT_SOURCE_IOS;
        } else if (requestHeader.contains("applewebkit") && requestHeader.contains("mobile")) {
            return Constants.CLIENT_SOURCE_H5;
        } else {
            return Constants.CLIENT_SOURCE_WEB;
        }
    }

}
