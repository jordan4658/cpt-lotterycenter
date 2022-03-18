package com.caipiao.live.common.util;


import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.model.common.RequestInfo;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.util.http.HttpRespons;
import com.caipiao.live.common.util.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author: admin
 * @Description:基础工具类
 * @Version: 1.0.0
 * @Date; 2017-12-20 10:53
 */
public class BaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(BaseUtil.class);
    // 验证码字符集
    private static final char[] chars = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * @param size 随机数长度
     * @Author: admin
     * @Description: 获取随机数
     * @Version: 1.0.0
     * @Date; 2018/4/25 16:12
     * @return: java.lang.String
     */
    public static String getRandomNumber(int size) {
        StringBuffer sb = new StringBuffer();
        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            // 记录字符
            sb.append(chars[n]);
        }
        return sb.toString();
    }

    /**
     * PARAM 检查参数是否有误
     *
     * @param object
     * @param resultInfo
     * @return
     */
    public static boolean checkRequestInfo(Object object, ResultInfo resultInfo) {
        RequestInfo<Object> requestInfo = (RequestInfo<Object>) object;

        if (requestInfo == null || requestInfo.getData() == null) {
            resultInfo.setCode(StatusCode.PARAM_ERROR.getCode());
            resultInfo.setMsg(StatusCode.PARAM_ERROR.getMsg());
            return true;
        }
        return false;
    }

    /**
     * @param request
     * @Author: admin
     * @Description: nginx代理后获取ip方法
     * @Version: 1.0.0
     * @Date; 2018/5/17 9:31
     * @return: java.lang.String
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.contains(",")) {
            // 多个路由时，取第一个非unknown的ip
            final String[] arr = ip.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ip = str;
                    break;
                }
            }
        }
        //logIp(ip, request);
        return ip;
    }

    public static String getServerIp() {
        // 获取操作系统类型
        try {
            HttpRespons hr = HttpUtils.sendGet("http://ifconfig.me");
            int code = hr.code;
            if (code == 200) {
                return hr.content.trim();
            } else {
                return "获取外网ip地址失败";
            }
        } catch (Exception e) {
            logger.error("获取外网ip地址失败", e);
            return e.getMessage();
        }
    }

    /**
     * 根据网络接口获取IP地址
     *
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    @SuppressWarnings({"unused", "rawtypes"})
    public static String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("getIpByEthNum occur error", e);
        }
        return "获取服务器IP错误";
    }

    public static String getUserIp(HttpServletRequest request) {
        String userIp = request.getHeader(Constants.HEADER_USER_IP);
        if (StringUtils.isNotEmpty(userIp)) {
            return userIp;
        }
        userIp = request.getParameter(Constants.HEADER_USER_IP);
        if (StringUtils.isNotEmpty(userIp)) {
            return userIp;
        }
        return getIpAddress(request);
    }

    private static void logIp(String ip, HttpServletRequest request) {
        StringBuilder ipLog = new StringBuilder();
        ipLog.append("X-forwarded-for:").append(request.getHeader("x-forwarded-for")).append("; ");
        ipLog.append("Proxy-Client-IP:").append(request.getHeader("Proxy-Client-IP")).append("; ");
        ipLog.append("WL-Proxy-Client-IP:").append(request.getHeader("WL-Proxy-Client-IP")).append("; ");
        ipLog.append("HTTP_CLIENT_IP:").append(request.getHeader("HTTP_CLIENT_IP")).append("; ");
        ipLog.append("HTTP_X_FORWARDED_FOR:").append(request.getHeader("HTTP_X_FORWARDED_FOR")).append("; ");
        ipLog.append("X-Real-IP:").append(request.getHeader("X-Real-IP")).append("; ");
        ipLog.append("Request.getRemoteAddr:").append(request.getRemoteAddr()).append("; ");
        ipLog.append("Ip:").append(ip).append("; ");
        logger.info(ipLog.toString());
    }


    public static String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        uri = request.getQueryString() != null ? uri + "?" + request.getQueryString() : uri;
        return uri;
    }


    public static void logAllParameters(HttpServletRequest request) {
        logAllParameters(request, null);
    }

    public static void logAllParameters(HttpServletRequest request, String prefix) {
        logParameters(request, prefix);
        //logRequestBody(request, prefix);
        logHeaders(request, prefix);
    }

    public static void logParameters(HttpServletRequest request) {
        logParameters(request, null);
    }

    public static void logParameters(HttpServletRequest request, String prefix) {
        String params = getRequestParameters(request);
        if (StringUtils.isEmpty(prefix)) {
            logger.info("{} parameters:{}", request.getRequestURI(), params);
        } else {
            logger.info("{} {} parameters:{}", prefix, request.getRequestURI(), params);
        }
    }

    public static void logHeaders(HttpServletRequest request) {
        logHeaders(request, null);
    }

    public static void logHeaders(HttpServletRequest request, String prefix) {
        String headers = getHeaders(request);
        if (StringUtils.isEmpty(prefix)) {
            logger.info("{} headers:{}", request.getRequestURI(), headers);
        } else {
            logger.info("{} {} headers:{}", prefix, request.getRequestURI(), headers);
        }
    }

    public static void logRequestBody(HttpServletRequest request) {
        logRequestBody(request, null);
    }

    public static void logRequestBody(HttpServletRequest request, String prefix) {
        String body = getRequestBody(request);
        if (StringUtils.isEmpty(prefix)) {
            logger.info("{} requestBody:{}", request.getRequestURI(), body);
        } else {
            logger.info("{} {} requestBody:{}", prefix, request.getRequestURI(), body);
        }
    }

    public static String getRequestParameters(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        StringBuilder parameterStr = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            StringBuilder value = new StringBuilder();
            for (String val : entry.getValue()) {
                value.append(val).append(",");
            }
            parameterStr.append(entry.getKey()).append("=").append(value.substring(0, value.length() - 1)).append("\n");
        }
        String log = null;
        if (parameterStr.length() > 0) {
            log = parameterStr.substring(0, parameterStr.length() - 1);
        }
        return log;
    }

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public static String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headerStr = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headerStr.append(name).append("=").append(value).append(";");
        }
        String log = null;
        if (headerStr.length() > 0) {
            log = headerStr.substring(0, headerStr.length() - 1);
        }
        return log;
    }

    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null && inputStream.available() > 0) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            logger.error("{} getRequestBody occur error.", request.getRequestURI(), ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    logger.error("{} getRequestBody close reader occur error.", request.getRequestURI(), ex);
                }
            }
        }
        return stringBuilder.toString();
    }


}
