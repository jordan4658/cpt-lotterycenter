package com.caipiao.core.library.tool;


import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.model.RequestInfo;
import com.caipiao.core.library.model.base.BaseInfo;
import com.caipiao.core.library.security.AESUtil;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * @Author: admin
 * @Description:基础工具类
 * @Version: 1.0.0
 * @Date; 2017-12-20 10:53
 */
public class BaseUtil {

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
     * 检查参数是否有误
     *
     * @param object
     * @param resultInfo
     * @return
     */
    public static boolean checkRequestInfo(Object object, BaseInfo resultInfo) {
        RequestInfo<Object> requestInfo = (RequestInfo<Object>) object;
        if (requestInfo == null || requestInfo.getData() == null) {
            resultInfo.setStatus(StatusCode.PARAM_ERROR.getCode());
            resultInfo.setInfo(StatusCode.PARAM_ERROR.getValue());
            return true;
        }
        return false;
    }

    /**
     * 检查参数是否有误 token 是否为16位，code是否为9私彩编号
     *
     * @param token
     * @param code
     * @return
     */
    public static boolean checkToken(String token, String code) {
        List<String> codeList = Lists.newArrayList("btbffc", "jsssc", "tenssc", "fivessc", "onelhc", "fivelhc", "amlhc", "jspks", "fivepks", "tenpks");
        if (token == null || code == null) {
            return true;
        } else {
            if (token.length() == 16 && codeList.contains(code)) {
                return false;
            }
        }
        return true;
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
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
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
        return ip;
    }

    /**
     * @param data 待加密数据
     * @Author: admin
     * @Description: AES 加密
     * @Version: 1.0.0
     * @Date; 2018/4/23 17:19
     * @return: java.lang.String
     */
    public static String AesEncrypt(String data) {
        return AESUtil.encrypt(data, Constants.AES_KEY);
    }

    /**
     * @param data 待解密数据
     * @Author: admin
     * @Description: AES 解密
     * @Version: 1.0.0
     * @Date; 2018/4/23 17:19
     * @return: java.lang.String
     */
    public static String AesDecrypt(String data) {
        return AESUtil.decrypt(data, Constants.AES_KEY);
    }

}
