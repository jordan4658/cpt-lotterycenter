package com.caipiao.live.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2018/5/25 025 17:51
 */
public class ExceptionAdviceTool {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdviceTool.class);

    public static String getServerStartupException(Exception e, String defaultMsg) {
        String errorMessage = e.getCause().getMessage();
        errorMessage = null == errorMessage ? "" : errorMessage;
        if (errorMessage.contains("Load balancer does not have available server for client")) {
            return "服务启动中，请稍候再试！";
        }
        return defaultMsg;
    }

}
