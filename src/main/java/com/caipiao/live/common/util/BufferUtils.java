package com.caipiao.live.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Buffer工具类
 */
public class BufferUtils {

    private static final Logger logger = LoggerFactory.getLogger(BufferUtils.class);

    /**
     * 缓存读全信息
     */
    public static StringBuffer readBufferAll(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = request.getReader();
            String rl;
            while ((rl = br.readLine()) != null) {
                sb.append(rl);
            }
            br.close();
        } catch (IOException e) {
            logger.error("paylog Buffer工具类 IOException BufferUtils ggCallback", e);
        }
        return sb;
    }
}
