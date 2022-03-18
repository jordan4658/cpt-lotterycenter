package com.caipiao.live.common.util.httpclient;

import com.caipiao.live.common.util.convert.MapToStringUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * httpclient工具
 *
 * @author 瑞夫
 * @version 1.0
 * @date 2020/6/19
 */
public class HttpSubmitUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSubmitUtil.class);

    static final int timeOut = 10 * 1000;

    private final static Object syncLock = new Object();

    private static CloseableHttpClient httpClient = null;
    private static final String DEFAULT_CHARSET = "UTF-8";


    private static void config(HttpRequestBase httpRequestBase) {
        // 设置Header等
        // httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        // httpRequestBase
        // .setHeader("Accept",
        // "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        // httpRequestBase.setHeader("Accept-Language",
        // "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
        // httpRequestBase.setHeader("Accept-Charset",
        // "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpRequestBase.setConfig(requestConfig);
    }


    /**
     * 建立请求，以表单HTML形式构造（默认）
     *
     * @param sParaTemp 请求参数数组
     * @param actionUrl 提交的地址
     * @param strMethod 提交方式。两个值可选：post、get
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, Object> sParaTemp, String actionUrl, String strMethod) {
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"frm1\" name=\"frm1\" action=\"" + actionUrl + "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sParaTemp.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"确认付款\" style=\"display:none;\"></form>");
        sbHtml.append("<script>setTimeout(\"document.getElementById('frm1').submit();\",100);</script>");

        return sbHtml.toString();
    }


    /**
     * post请求URL获取内容
     *
     * @param url
     * @return
     * @throws IOException
     * @author SHANHY
     * @create 2015年12月18日
     */
    public static String post(String url, Map<String, Object> params) {
        HttpPost httppost = new HttpPost(url);
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * GET请求URL获取内容
     *
     * @param url
     * @return
     * @author SHANHY
     * @create 2015年12月18日
     */
    public static String get(String url) {
        HttpGet httpget = new HttpGet(url);
        config(httpget);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httpget,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取HttpClient对象
     *
     * @return
     * @author SHANHY
     * @create 2015年12月18日
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(200, 40, 100, hostname, port);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建HttpClient对象
     *
     * @return
     * @author SHANHY
     * @create 2015年12月18日
     */
    public static CloseableHttpClient createHttpClient(int maxTotal,
                                                       int maxPerRoute, int maxRoute, String hostname, int port) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("http", plainsf)
                .register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();

        return httpClient;
    }

    /**
     * GET请求URL获取内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get4LocationAndBody(String url, Map<String, Object> params) {
        URI uri = generateURLParams(url, params);
        HttpGet httpGet = new HttpGet(uri);
        config(httpGet);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            response = getHttpClient(url).execute(httpGet,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = response.getFirstHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (!location.startsWith("http://") && !location.startsWith("https://")) {
                        String scheme = uri.getScheme();
                        String domain = uri.getHost();
                        location = scheme + "://" + domain + location;
                    }
                    LOGGER.info("Location 跳转地址：{}", location);
                } else {
                    LOGGER.error("Location field value is null");
                }
                return location;
            } else if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                EntityUtils.consume(entity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * post请求URL获取内容
     * form-data 提交
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String postForm4LocationAndBody(String url, Map<String, Object> params) {
        URI uri = generateURLParams(url, null);
        HttpPost httppost = new HttpPost(url);
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("postForm响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = response.getFirstHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (!location.startsWith("http://") && !location.startsWith("https://")) {
                        String scheme = uri.getScheme();
                        String domain = uri.getHost();
                        location = scheme + "://" + domain + location;
                    }
                    LOGGER.info("Location 跳转地址：{}", location);
                } else {
                    LOGGER.error("Location field value is null");
                }
                return location;
            } else if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                EntityUtils.consume(entity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * post请求URL获取内容
     * form-data 提交
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String postForm4Location(String url, Map<String, Object> params) {
        URI uri = generateURLParams(url, null);
        HttpPost httppost = new HttpPost(url);
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("postForm响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = response.getFirstHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (!location.startsWith("http://") && !location.startsWith("https://")) {
                        String scheme = uri.getScheme();
                        String domain = uri.getHost();
                        location = scheme + "://" + domain + location;
                    }
                    LOGGER.info("Location 跳转地址：{}", location);
                } else {
                    LOGGER.error("Location field value is null");
                }
                return location;
            } else if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                LOGGER.info("body str：{}", result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * x-www-form-urlencoded
     *
     * @param url
     * @param params
     * @return
     */
    public static String postXwwwform4Location(String url, Map<String, Object> params) {
        URI uri = generateURLParams(url, null);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("postForm响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = response.getFirstHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (!location.startsWith("http://") && !location.startsWith("https://")) {
                        String scheme = uri.getScheme();
                        String domain = uri.getHost();
                        location = scheme + "://" + domain + location;
                    }
                    LOGGER.info("Location 跳转地址：{}", location);
                } else {
                    LOGGER.error("Location field value is null");
                }
                return location;
            } else if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                LOGGER.info("body str：{}", result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * post请求URL获取内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String postXwwwform4Body(String url, Map<String, Object> params) {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            LOGGER.info("开始请求：{} body：{}", url, MapToStringUtil.mapToString(params, "&", "="));
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("postForm响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                EntityUtils.consume(entity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * post请求URL获取内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String postXwwwform4LocationAndBody(String url, Map<String, Object> params) {
        URI uri = generateURLParams(url, null);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("postForm响应状态：{}", response.getStatusLine());
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = response.getFirstHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (!location.startsWith("http://") && !location.startsWith("https://")) {
                        String scheme = uri.getScheme();
                        String domain = uri.getHost();
                        location = scheme + "://" + domain + location;
                    }
                    LOGGER.info("Location 跳转地址：{}", location);
                } else {
                    LOGGER.error("Location field value is null");
                }
                return location;
            } else if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
                EntityUtils.consume(entity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 构建URL参数
     *
     * @param url
     * @param params
     * @return
     */
    private static URI generateURLParams(String url, Map<String, Object> params) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
                }
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            return uri;
        }
        return uri;
    }

    /**
     * 构建提交参数name value值
     *
     * @param httpost
     * @param params
     */
    private static void setPostParams(HttpPost httpost,
                                      Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            try {
                httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        // URL列表数组
        /*String[] urisToGet = {
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497"};

        long start = System.currentTimeMillis();
        try {
            int pagecount = urisToGet.length;
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
            for (int i = 0; i < pagecount; i++) {
                HttpPost httpget = new HttpPost(urisToGet[i]);
                config(httpget);
                // 启动线程抓取
                executors
                        .execute(new GetRunnable(urisToGet[i], countDownLatch));
            }
            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + ","
                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }

        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));*/
    }

    static class GetRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        private String url;

        public GetRunnable(String url, CountDownLatch countDownLatch) {
            this.url = url;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(get(url));
            } finally {
                countDownLatch.countDown();
            }
        }
    }

}
