package com.caipiao.live.common.util.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpCommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpCommonUtils.class);

    public static void closeResponse(CloseableHttpResponse response, String url, Object params) {
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                logger.error("closeResponse occur error, url:{}, params:{}", url, params, e);
            }
        }
    }

    public static void closeHttpClient(CloseableHttpClient httpClient, String url, Object params) {
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("closeHttpClient occur error, url:{}, params:{}", url, params, e);
            }
        }
    }

    public static void closeHttpClientAndResponse(CloseableHttpResponse response, CloseableHttpClient httpClient, String url, Object params) {
        closeResponse(response, url, params);
        closeHttpClient(httpClient, url, params);
    }
}
