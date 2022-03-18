package com.caipiao.live.common.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.live.common.util.JsonUtil;
import com.caipiao.live.common.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 * HTTP 请求工具类
 *
 * @SuppressWarnings("此类后续禁用，http工具类统一使用 HttpClientUtil 工具类")
 */
//@Deprecated
public abstract class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final int MAX_TIMEOUT = 27000;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=";
    public static final String CONTENT_TYPE_HTML = "text/html;charset=";
    public static final String TAOBAO_IPADDRESS_QUERY_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    // 连接超时时间2s
    private static int timeout = 20000;
    private static String defaultContentEncoding = "utf-8";

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private HttpUtils() {
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, new HashMap<>());
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, String params) throws IOException {
        String ctype = "";
        byte[] content = params.getBytes(DEFAULT_CHARSET);
        return _doPost(url, ctype, content, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset) throws IOException {
        return doPost(url, params, charset, null);
    }

    public static String doPost(String url, Map<String, String> params, String charset, Map<String, String> headerMap)
            throws IOException {
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, CONTENT_TYPE_FORM + charset, content, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param ctype   请求类型
     * @param content 请求字节数组
     * @return 响应字符串
     * @throws IOException
     */
    @Deprecated
    private static String doPost(String url, String ctype, byte[] content) throws IOException {
        return _doPost(url, ctype, content, null);
    }

    private static String _doPost(String url, String ctype, byte[] content, Map<String, String> headerMap)
            throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(MAX_TIMEOUT);
            conn.setReadTimeout(MAX_TIMEOUT);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    public static String doGetUseTimeOut(String url, Integer timeOut) throws IOException {
        return doGet(url, null, DEFAULT_CHARSET, timeOut);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET, null);
    }

    private static String doGet(String url, Map<String, String> params, String charset, Integer timeOut)
            throws IOException {
        return doGet(url, params, CONTENT_TYPE_FORM, charset, timeOut);
    }

    public static String doGet(String url, Map<String, String> params, Map<String, String> header)
            throws IOException {
        return doGet(url, params, CONTENT_TYPE_FORM, DEFAULT_CHARSET, DEFAULT_TIMEOUT, header);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @param timeOut
     * @return 响应字符串
     */

    private static String doGet(String url, Map<String, String> params, String contentType, String charset,
                                Integer timeOut, Map<String, String> header) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, contentType + charset, header);
            if (timeOut != null && timeOut > 0) {
                conn.setConnectTimeout(timeOut);
                conn.setReadTimeout(timeOut);
            }
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static String doGet(String url, Map<String, String> params, String contentType, String charset,
                                Integer timeOut) throws IOException {
        return doGet(url, params, contentType, charset, timeOut, null);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap)
            throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "ASIT");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (null == query || query.trim().length() == 0) {
            return url;
        }

        if (null == url.getQuery() || url.getQuery().trim().length() == 0) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (name == null || value == null || name.trim().length() == 0 || value.trim().length() == 0) {
                continue;
            }
            if (hasParam) {
                query.append("&");
            } else {
                hasParam = true;
            }

            query.append(name).append("=").append(URLEncoder.encode(value, charset));
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (null == msg || msg.trim().length() == 0) {
                logger.info("getResponseAsString is empty. responseCode:{}, responseMessage:{}", conn.getResponseCode(), conn.getResponseMessage());
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                return msg;
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }
            logger.info("getStreamAsString response:{}", response);
            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (ctype != null && ctype.trim().length() > 0) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (null != pair[1] && pair[1].trim().length() > 0) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (null != value && value.trim().length() > 0) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (null != value && value.trim().length() > 0) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<>();

        String[] pairs = query.split("[?]")[1].split("[&]");
        if (pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    // 获取HttpServletRequest对象
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 使用代理
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    private static String doGetProxy(String url, Map<String, String> params, String charset, Integer timeOut,
                                     String userAgent, String ip, int port) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String query = buildQuery(params, charset);
            conn = getProxyConnection(buildGetUrl(url, query), METHOD_GET, CONTENT_TYPE_FORM + charset, null, ip, port);
            if (timeOut != null && timeOut > 0) {
                conn.setConnectTimeout(timeOut);
                conn.setReadTimeout(timeOut);
            }
            conn.setRequestProperty("User-Agent", userAgent);
            rsp = getResponseAsString(conn);
            logger.info(rsp + "==================================");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * @param url
     * @param timeOut   请求超时时间
     * @param userAgent 代理标识
     * @return
     * @throws IOException
     */
    public static String doGetUseTimeOutAndAgent(String url, Integer timeOut, String userAgent, String ip, int port)
            throws IOException {
        return doGetProxy(url, null, DEFAULT_CHARSET, timeOut, userAgent, ip, port);
    }

    /**
     * 使用代理获取HttpURLConnection
     */
    private static HttpURLConnection getProxyConnection(URL url, String method, String ctype,
                                                        Map<String, String> headerMap, String ip, int port) throws IOException {
        HttpURLConnection conn = null;
        // 代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection(proxy);
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection(proxy);
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "ASIT");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }


    /**
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送GET请求
     */
    public static HttpRespons sendGet(String urlString) throws IOException {
        return send(urlString, "GET", null, null);
    }

    /**
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送GET请求
     */
    public static HttpRespons sendGet(String urlString, Map<String, Object> params) throws IOException {
        return send(urlString, "GET", params, null);
    }

    /**
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送GET请求
     */
    public static HttpRespons sendGet(String urlString, Map<String, Object> params, Map<String, Object> propertys) throws IOException {
        return send(urlString, "GET", params, propertys);
    }

    /**
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送POST请求
     */
    public static HttpRespons sendPost(String urlString) throws IOException {
        return send(urlString, "POST", null, null);
    }

    /**
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送POST请求
     */
    public static HttpRespons sendPost(String urlString, Map<String, Object> params) throws IOException {
        return send(urlString, "POST", params, null);
    }

    /**
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送POST请求
     */
    public static HttpRespons sendPost(String urlString, Map<String, Object> params, Map<String, Object> propertys) throws IOException {
        return send(urlString, "POST", params, propertys);
    }

    /**
     * @param urlString  地址
     * @param method     get/post
     * @param parameters 添加由键值对指定的请求参数
     * @param propertys  添加由键值对指定的一般请求属性
     * @return 响映对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送HTTP请求
     */
    private static HttpRespons send(String urlString, String method, Map<String, Object> parameters, Map<String, Object> propertys) throws IOException {
        HttpURLConnection urlConnection = null;

        if ("GET".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }

        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(timeout);

        if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, (String) propertys.get(key));
            }
        }

        if ("POST".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }
        return makeContent(urlString, urlConnection);
    }

    /**
     * @param urlString
     * @param urlConnection
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 得到响应对象
     */
    private static HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = defaultContentEncoding;
            }
            httpResponser.urlString = urlString;
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();
            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 发送GET请求
     */
    public static URLConnection sendGetRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        StringBuilder buf = new StringBuilder(url);
        Set<Entry<String, Object>> entrys = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            buf.append("?");
            entrys = params.entrySet();
            for (Map.Entry<String, Object> entry : entrys) {
                buf.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(buf.toString());
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeout);
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Map.Entry<String, Object> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        conn.getResponseCode();
        return conn;
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 发送POST请求
     */
    public static URLConnection sendPostRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        StringBuilder buf = new StringBuilder();
        Set<Entry<String, Object>> entrys = null;
        // 如果存在参数，则放在HTTP请求体，形如name=aaa&age=10
        if (params != null && !params.isEmpty()) {
            entrys = params.entrySet();
            for (Map.Entry<String, Object> entry : entrys) {
                buf.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(timeout);
        OutputStream out = conn.getOutputStream();
        out.write(buf.toString().getBytes("UTF-8"));
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Map.Entry<String, Object> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        conn.getResponseCode(); // 为了发送成功
        return conn;
    }

    /**
     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能: <FORM METHOD=POST ACTION="http://192.168.0.200:8080/ssi/fileload/test.do" enctype="multipart/form-data"> <INPUT TYPE="text" NAME="name"> <INPUT TYPE="text" NAME="id">
     * <input type="file" name="imagefile"/> <input type= "file" name="zip"/> </FORM>
     *
     * @param path   上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http:// www.itcast.cn或http://192.168.1.10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param files  上传文件
     */
    @SuppressWarnings("resource")
    public static boolean uploadFiles(String path, Map<String, Object> params, FormFile[] files) throws Exception {
        final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
        final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

        int fileDataLength = 0;
        if (files != null && files.length != 0) {
            for (FormFile uploadFile : files) {// 得到文件类型数据的总长度
                StringBuilder fileExplain = new StringBuilder();
                fileExplain.append("--");
                fileExplain.append(BOUNDARY);
                fileExplain.append("\r\n");
                fileExplain.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
                fileExplain.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                fileExplain.append("\r\n");
                fileDataLength += fileExplain.length();
                if (uploadFile.getInStream() != null) {
                    fileDataLength += uploadFile.getFile().length();
                } else {
                    fileDataLength += uploadFile.getData().length;
                }
            }
        }
        StringBuilder textEntity = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {// 构造文本类型参数的实体数据
                textEntity.append("--");
                textEntity.append(BOUNDARY);
                textEntity.append("\r\n");
                textEntity.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                textEntity.append(entry.getValue());
                textEntity.append("\r\n");
            }
        }
        // 计算传输给服务器的实体数据总长度
        int dataLength = textEntity.toString().getBytes().length + fileDataLength + endline.getBytes().length;

        URL url = new URL(path);
        int port = url.getPort() == -1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        // 下面完成HTTP请求头的发送
        String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: " + dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: " + url.getHost() + ":" + port + "\r\n";
        outStream.write(host.getBytes());
        // 写完HTTP请求头后根据HTTP协议再写一个回车换行
        outStream.write("\r\n".getBytes());
        // 把所有文本类型的实体数据发送出来
        outStream.write(textEntity.toString().getBytes());
        // 把所有文件类型的实体数据发送出来
        if (files != null && files.length != 0) {
            for (FormFile uploadFile : files) {
                StringBuilder fileEntity = new StringBuilder();
                fileEntity.append("--");
                fileEntity.append(BOUNDARY);
                fileEntity.append("\r\n");
                fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
                fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                outStream.write(fileEntity.toString().getBytes());
                if (uploadFile.getInStream() != null) {
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    uploadFile.getInStream().close();
                } else {
                    outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
                }
                outStream.write("\r\n".getBytes());
            }
        }
        // 下面发送数据结束标志，表示数据已经结束
        outStream.write(endline.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (reader.readLine().indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
            return false;
        }
        outStream.flush();
        outStream.close();
        reader.close();
        socket.close();
        return true;
    }

    /**
     * @param path   上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http:// www.itcast.cn或http://192.168.1.10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file   上传文件
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 提交数据到服务器
     */
    public static boolean uploadFile(String path, Map<String, Object> params, FormFile file) throws Exception {
        return uploadFiles(path, params, new FormFile[]{file});
    }

    /**
     * @param inStream
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 将输入流转为字节数组
     */
    public static byte[] read2Byte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * @param inStream
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 将输入流转为字符串
     */
    public static String read2String(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return new String(outSteam.toByteArray(), "UTF-8");
    }

    /**
     * @param path     请求地址
     * @param xml      xml数据
     * @param encoding 编码
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     */
    public static byte[] postXml(String path, String xml, String encoding) throws Exception {
        byte[] data = xml.getBytes(encoding);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml; charset=" + encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            return read2Byte(conn.getInputStream());
        }
        return null;
    }

    /**
     * 原生字符串发送put请求
     *
     * @param url
     * @param token
     * @param jsonStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPut(String url, String token, String jsonStr) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setHeader("DataEncoding", "UTF-8");
        httpPut.setHeader("token", token);

        CloseableHttpResponse httpResponse = null;
        try {
            httpPut.setEntity(new StringEntity(jsonStr));
            httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @param token
     * @param jsonStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doDelete(String url, String token, String jsonStr) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpDelete.setConfig(requestConfig);
        httpDelete.setHeader("Content-type", "application/json");
        httpDelete.setHeader("DataEncoding", "UTF-8");
        httpDelete.setHeader("token", token);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * AG棋牌平台用
     *
     * @param url
     * @param cagent
     * @return
     */
    public static String sendPost(String url, String cagent) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        /* httpPut.setHeader("Content-type", "application/json"); */
        httpPost.setHeader("DataEncoding", "UTF-8");
        httpPost.setHeader("User-Agent", "WEB_LIB_GI_" + cagent);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

//	public static void main(String[] agrs)
//	{
//		
//		try
//		{
//			URL url= new URL("http://api.kuaidi100.com/api?id=b4319cbb03756a04&com=shunfeng&nu=612434557760&valicode=&show=2&muti=1&order=asc");
//			URLConnection con=url.openConnection();
//			 con.setAllowUserInteraction(false);
//			   InputStream urlStream = url.openStream();
//			   String type = con.guessContentTypeFromStream(urlStream);
//			   String charSet=null;
//			   if (type == null)
//			    type = con.getContentType();
//
//			   if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
//			    return ;
//
//			   if(type.indexOf("charset=") > 0)
//			    charSet = type.substring(type.indexOf("charset=") + 8);
//
//			   byte b[] = new byte[10000];
//			   int numRead = urlStream.read(b);
//			  String content = new String(b, 0, numRead);
//			   while (numRead != -1) {
//			    numRead = urlStream.read(b);
//			    if (numRead != -1) {
//			     //String newContent = new String(b, 0, numRead);
//			     String newContent = new String(b, 0, numRead, charSet);
//			     content += newContent;
//			    }
//			   }
//			   System.out.println("content:" + content);
//			   urlStream.close();
//		} catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}

    /**
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 快递100测试
     */
    public static void testPost() throws Exception {
        String url = "https://m.kuaidi100.com/query";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "yunda");
        params.put("postid", "3966750242380");
        params.put("id", "1");
        params.put("valicode", "");
        HttpURLConnection conn = (HttpURLConnection) sendPostRequest(url, params, null);
        int code = conn.getResponseCode();
        if (code == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = read2Byte(in);
            String json = new String(data);
            System.out.print(json);
        } else {
            throw new Exception();
        }
    }

    /**
     * @param defaultContentEncoding
     * @author abu
     * <p>
     * Description:<br>
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }

    /**
     * 中国南北方省市区分
     */
    public final static String southhpro = "江苏,江苏省,安徽,安徽省,湖北,湖北省,重庆,重庆市,四川,四川省,西藏,西藏自治区,云南,云南省,贵州,贵州省,湖南,湖南省,江西,江西省,广西,广西壮族自治区,广东,广东省,福建,福建省,浙江,浙江省,上海,上海市,海南,海南省,台湾,台湾省,香港,香港特别行政区,澳门,澳门特别行政区";
    public final static String northpro = "山东,山东省,河南,河南省,山西,山西省,陕西,陕西省,甘肃,甘肃省,青海,青海省,新疆,新疆维吾尔自治区,河北,河北省,天津,天津市,北京,北京市,内蒙古,内蒙古自治区,辽宁,辽宁省,吉林,吉林省,黑龙江,黑龙江省,宁夏,宁夏回族自治区";

    /**
     * ip地址转换省市区
     *
     * @param ip
     * @return
     * @throws IOException
     */
    public synchronized static Map<String, String> ipParse(String ip) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(ip) && !"127.0.0.1".equals(ip) && !"0:0:0:0:0:0:0:1".equals(ip)) {
                map = getAddressWhois(ip);
                if (org.apache.commons.lang.StringUtils.isEmpty(map.get("province"))) {
                    map = getAddress126(ip);
                    if (org.apache.commons.lang.StringUtils.isEmpty(map.get("province"))) {
                        map = getAddressTaobao(ip);
                    }
                }
                if (northpro.contains(map.get("province"))) {
                    map.put("country", "北方");
                } else if (southhpro.contains(map.get("province"))) {
                    map.put("country", "南方");
                } else {
                    map.put("country", map.get("province"));
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * ip转地址 淘宝ip解析接口 容易被封 慎用
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddressTaobao(String ip) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        HttpRespons hr = sendGet("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
        if (hr.code == 200) {
//		{"code":0,"data":{"ip":"202.36.60.35","country":"柬埔寨","area":"","region":"XX","city":"XX","county":"XX","isp":"XX","country_id":"KH","area_id":"","region_id":"xx","city_id":"xx","county_id":"xx","isp_id":"xx"}}
//		{"code":0,"data":{"ip":"114.218.96.52","country":"中国","area":"","region":"江苏","city":"苏州","county":"XX","isp":"电信","country_id":"CN","area_id":"","region_id":"320000","city_id":"320500","county_id":"xx","isp_id":"100017"}}
            String content = hr.content;
            String[] strs = content.split(",");
            String province = strs[4].split(":")[1].replaceAll("\"", "");// .substring(10);
            String city = strs[5].split(":")[1].replaceAll("\"", "");
            String country = strs[2].split(":")[1].replaceAll("\"", "");
            map.put("country", country);
            map.put("province", province);
            map.put("city", city);
            return map;
        }

        return map;
    }

    /**
     * ip转地址 国外ip无法识别
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddress126(String ip) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://ip.ws.126.net/ipquery?ip=" + ip);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(2000).setSocketTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "text/html;charset=GBK");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            String[] strs = content.split("localAddress=")[1].split(",");
            String province = strs[1].split(":")[1].replaceAll("\"", "").replaceAll("}", "");
            String city = strs[0].split(":")[1].replaceAll("\"", "");
            map.put("province", province);
            map.put("city", city);
        } catch (Exception e) {
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {

            }
        }

        return map;
    }

    /**
     * ip转地址 国内国外通用
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddressWhois(String ip) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(2000).setSocketTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "text/html;charset=GBK");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity).trim();
            HttpUtils.WhoisBean wb = JsonUtil.fromJson(content, HttpUtils.WhoisBean.class);
            if (wb != null) {
                map.put("province", org.apache.commons.lang.StringUtils.isEmpty(wb.getPro()) ? wb.getAddr() : wb.getPro());
                map.put("city", wb.getCity());
            }
        } catch (Exception e) {
            logger.error("getAddressWhois occur error. ip:{}", ip, e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {

            }
        }

        return map;
    }

    static class WhoisBean {
        // 省
        private String pro;
        // 市
        private String city;
        // 地址(国外只有国家名称)
        private String addr;

        public WhoisBean() {
            super();
        }

        public WhoisBean(String pro, String city, String addr) {
            super();
            this.pro = pro;
            this.city = city;
            this.addr = addr;
        }

        public String getPro() {
            return pro;
        }

        public void setPro(String pro) {
            this.pro = pro;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

    }

    /**
     * httpclient访问
     *
     * @param url
     * @param timeout
     * @param charset
     * @return
     * @throws Exception
     */
    public synchronized static String clientGet(String url, int timeout, String charset) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "text/html;charset=GBK" + (org.apache.commons.lang.StringUtils.isEmpty(charset) ? "GBK" : charset));
        CloseableHttpResponse httpResponse = null;
        String content = "";
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity).trim();
        } catch (Exception e) {
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
            }
        }
        return content;
    }


    public static String postJSON(String url, String jsonString) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
