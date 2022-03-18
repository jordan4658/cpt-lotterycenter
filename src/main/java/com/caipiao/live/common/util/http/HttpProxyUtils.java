package com.caipiao.live.common.util.http;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpProxyUtils {


    private static final Logger logger = LoggerFactory.getLogger(HttpProxyUtils.class);


    private static final int CONNECT_TIMEOUT = 10000;// 设置连接超时时间,单位毫秒
    private static final int CONNECTION_REQUEST_TIMEOUT = 3000;// 从连接池获取到连接的超时,单位毫秒
    private static final int SOCKET_TIMEOUT = 7000;// 请求获取数据的超时时间,单位毫秒

    public static String doProxyPostJson(String proxyIp, int port, String tcp, String url,
                                         Map<String, String> paramsMap) {

        // 设置代理IP、端口、协议
        HttpHost proxy = new HttpHost(proxyIp, port, tcp);

        // 把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();

        // 实例化CloseableHttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();


        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求超时 20+10+25=55s 配合业务设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT) // 设置连接超时时间,单位毫秒。
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 从连接池获取到连接的超时,单位毫秒。
                    .setSocketTimeout(SOCKET_TIMEOUT)// 请求获取数据的超时时间,单位毫秒;
                    .setProxy(proxy)//设置代理
                    .build();
            httpPost.setConfig(requestConfig);


            List<BasicNameValuePair> list = new ArrayList<>();
            for (String key : paramsMap.keySet()) {
                list.add(new BasicNameValuePair(key, String.valueOf(paramsMap.get(key))));
            }

            // 创建请求内容
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);

            // 执行http请求
            response = httpClient.execute(httpPost);
            response = closeableHttpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("dfProxyPostJson occur error:{}, url:{}, proxyHost:{}, proxyPort:{}, params:{}",
                 e, url, proxyIp, port, JSONObject.toJSONString(paramsMap));
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                closeableHttpClient.close();
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("doProxyPostJson  error {} ", e);
            }
        }

        return resultString;
    }
}
