package com.caipiao.live.common.util;


import java.net.URLDecoder;
import java.net.URLEncoder;


/***
 * URL 参数封装
 */
public class URLUtils {
    public static String encode(String str, String charset) {
        try {
            return URLEncoder.encode(str, charset);
        } catch (Exception e) {
            return str;
        }
    }

    public static String decode(String str, String charset) {
        try {
            return URLDecoder.decode(str, charset);
        } catch (Exception e) {
            return str;
        }
    }

    public static void appendParam(StringBuilder sb, String name, String val) {
        appendParam(sb, name, val, true);
    }

    public static void appendParam(StringBuilder sb, String name, String val, String charset) {
        appendParam(sb, name, val, true, charset);
    }

    public static void appendParam(StringBuilder sb, String name, String val, boolean and) {
        appendParam(sb, name, val, and, null);
    }

    public static void appendParam(StringBuilder sb, String name, String val, boolean and, String charset) {
        if (and) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append(name);
        sb.append("=");
        if (val == null) {
            val = "";
        }
        if (null != charset && !"null".equals(charset) && !"".equals(charset)) {
            sb.append(val);
        } else {
            sb.append(encode(val, charset));
        }
    }
}
