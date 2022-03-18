package com.caipiao.live.common.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil extends HttpCommonUtils {

    //	public static boolean isTest=Boolean.parseBoolean(SysConfig.getInstance().getProperty("is_test"));
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // 连接主机超时（30s）
    public static final int HTTP_CONNECT_TIMEOUT_30S = 30 * 1000;
    // 从主机读取数据超时（3min）
    public static final int HTTP_READ_TIMEOUT_3MIN = 180 * 1000;
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

    public static String postJson(String url, String body, String charset) {
        int timeout = 10000;
        return postJson(url, body, charset, timeout, timeout, null);
    }

    public static String postJson(String url, String body, String charset, int connTimeout, int socketTimeout, HttpHost proxy) {

        String result = null;
        if (true) {
            if (null == charset) {
                charset = "UTF-8";
            }
            CloseableHttpClient httpClient = null;
            HttpPost httpPost = null;
            CloseableHttpResponse response = null;
            try {
                httpClient = HttpConnectionManager.getInstance().getHttpClient();
                httpPost = new HttpPost(url);

                // 设置连接超时,设置读取超时
                RequestConfig requestConfig = RequestConfig.custom()
                        .setProxy(proxy)
                        .setConnectTimeout(connTimeout)
                        .setSocketTimeout(socketTimeout)
                        .build();
                httpPost.setConfig(requestConfig);

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-Type", "application/json;charset=utf-8");

                if (null != body && !"".equals(body)) {
                    // 设置参数
                    StringEntity se = new StringEntity(body, "UTF-8");
                    httpPost.setEntity(se);
                }
                response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    result = EntityUtils.toString(resEntity, charset);
                    EntityUtils.consume(resEntity); // 此句关闭了流
                }
            } catch (Exception e) {
                logger.error("postJson occur error:{}", e.getMessage(), e);
            } finally {
                closeResponse(response, url, body);
            }

        } else {
            result = "config.properties中 is_test 属性值为false, 若已正确设置请求值，请设置为true后再次测试";
        }

        return result;
    }

    /**
     * httpPost
     */
    public static String httpPost(String url, String jsonParam) {
        return (String) httpPostGetCookies(url, jsonParam).get("result");
    }

    /**
     * httpPost get Cookies
     */
    public static Map<String, Object> httpPostGetCookies(String url, String jsonParam) {
        CloseableHttpClient httpclient = HttpConnectionManager.getInstance().getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头和请求参数
        if (null != jsonParam && !jsonParam.isEmpty()) {
            StringEntity entity = new StringEntity(jsonParam, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }


        // 超时时间设置
        RequestConfig requestConfig = buildDefaultRequestConfigBuilder().build();
        httpPost.setConfig(requestConfig);

        // 发送请求
        CloseableHttpResponse response = null;
        Map<String, Object> map = new HashMap<>();

        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity);
            logger.info("httpPostGetCookies for url:{}; params:{}; result:{}.", url, jsonParam, str);
            EntityUtils.consume(entity); // 此句关闭了流

            // 获取数据内容
            map.put("result", str);
            // 获取返回到额Cookies
            Header[] headers = response.getHeaders("Set-Cookie");
            map.put("cookies", headers);
        } catch (Exception e) {
            logger.error("httpPostGetCookies for url:{} occur error:{}", url, e.getMessage(), e);
        } finally {
            closeResponse(response, url, jsonParam);
        }
        return map;
    }

    public static String formPost(String url, Map<String, String> params) {
        return formPost(url, params, null);
    }

    public static String formPost(String url, Map<String, String> params, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (params != null && params.size() > 0) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Charset.forName("UTF-8"));
                httpPost.setEntity(entity);
            }

            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultString = EntityUtils.toString(entity, "utf-8");
//            logger.info("{} http formPost response:{}, params:{}, headers:{}", url, resultString, JSONObject.toJSONString(params), JSONObject.toJSONString(headers));
            EntityUtils.consume(entity); // 此句关闭了流
        } catch (Exception e) {
            logger.error("http formPost occur error. e {} url:{}, params:{} ", e, url, JSONObject.toJSONString(params));
        } finally {
            closeResponse(response, url, JSONObject.toJSONString(params));
        }
        return resultString;
    }

//    public static void main(String[] args) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", "163");
//            jsonObject.put("nickname", "测试aaa");
//            jsonObject.put("head", "http");
//            jsonObject.put("sendNumber", 1);
//            jsonObject.put("holdNumber", 0);
//            jsonObject.put("money", 30);
//            jsonObject.put("gid", "163");
//
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("gid", "163");
//            map.put("data", jsonObject.toJSONString());
//            map.put("single", "2dac84db308f851b810e106d9b678ab3");
//            Map<String, String> headMap = new HashMap<String, String>();
//            headMap.put("AuthGC", "cpt");
//
//            String result = HttpClientUtil.formPost("http://cptadminapi.wtalking.chat/interactive/chat/redpackSendout", map, headMap);
////            String result = HttpUtils.doPost("http://cptadminapi.wtalking.chat/interactive/chat/redpackSendout", map,"utf-8", headMap);
//            System.out.println(result);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public static String formPost(String url) {
        return formPost(url, null);
    }


    /**
     * httpGet
     */
    public static String httpGet(String url) {
        return httpGet(url, null);
    }

    public static String httpGet(String url, HttpHost proxy) {
        return httpGetWithCookies(url, null, proxy);
    }

    public static byte[] httpGetWithCookiesResponseByte(String url, HttpHost proxy) {
        return httpGetWithCookiesResponseByte(url, null, proxy);
    }

    public static byte[] httpGetWithCookiesResponseByte(String url, Header[] headers, HttpHost proxy) {
        CloseableHttpClient httpclient = HttpConnectionManager.getInstance().getHttpClient();
        HttpGet httpGet = new HttpGet(url);

        // 超时时间设置
        RequestConfig.Builder requestConfigBuilder = buildDefaultRequestConfigBuilder();
        if (null != proxy) {
            requestConfigBuilder.setProxy(proxy);
        }
        httpGet.setConfig(requestConfigBuilder.build());
        httpGet.setHeader("User-agent", DEFAULT_USER_AGENT);
        if (headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        byte[] bytes = null;
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            bytes = EntityUtils.toByteArray(entity);
        } catch (Exception e) {
            logger.error("httpGetWithCookiesResponseByte for url:{} occur error:{}", url, e.getMessage(), e);
        } finally {
            closeResponse(response, url, null);
            logger.info("httpGetWithCookiesResponseByte for url:{} used times:{} ms", url, (System.currentTimeMillis() - start));
        }
        return bytes;
    }

    public static String httpGetWithCookies(String url, Header[] headers, HttpHost proxy) {
        CloseableHttpClient httpclient = HttpConnectionManager.getInstance().getHttpClient();
        HttpGet httpGet = new HttpGet(url);

        // 超时时间设置
        RequestConfig.Builder requestConfigBuilder = buildDefaultRequestConfigBuilder();
        if (null != proxy) {
            requestConfigBuilder.setProxy(proxy);
        }
        httpGet.setConfig(requestConfigBuilder.build());
        httpGet.setHeader("User-agent", DEFAULT_USER_AGENT);
        if (headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("httpGetWithCookies for url:{} occur error:{}", url, e.getMessage(), e);
            return "";
        } finally {
            closeResponse(response, url, null);
            logger.info("httpGetWithCookies for url:{} used times:{} ms", url, (System.currentTimeMillis() - start));
        }
    }

    private static RequestConfig.Builder buildDefaultRequestConfigBuilder() {
        return RequestConfig.custom()
                .setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S);
    }

}
