package com.caipiao.live.common.util.httpclient;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author 瑞夫
 * @version 1.0
 * @date 2020/6/11
 */
public class HttpClients {

    private static final String USER_AGENT_VALUE =
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";

    private static final String JKS_CA_FILENAME =
            "tenpay_cacert.jks";

    private static final String JKS_CA_ALIAS = "tenpay";

    private static final String JKS_CA_PASSWORD = "";


    /**
     * ca证书文件
     */
    private File caFile;

    /**
     * 证书文件
     */
    private File certFile;

    /**
     * 证书密码
     */
    private String certPasswd;

    /**
     * 请求内容，无论post和get，都用get方式提供
     */
    private String reqContent;

    /**
     * 应答内容
     */
    private String resContent;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 错误信息
     */
    private String errInfo;

    /**
     * 超时时间,以秒为单位
     */
    private int timeOut;

    /**
     * http应答编码
     */
    private int responseCode;

    /**
     * 字符编码
     */
    private String charset;

    private InputStream inputStream;

    public HttpClients() {
        this.caFile = null;
        this.certFile = null;
        this.certPasswd = "";

        this.reqContent = "";
        this.resContent = "";
        this.method = "POST";
        this.errInfo = "";
        this.timeOut = 30;//30秒

        this.responseCode = 0;
        this.charset = "UTF-8";

        this.inputStream = null;
    }

    public HttpClients(String url, String method, int timeOut, String charset) {
        this.caFile = null;
        this.certFile = null;
        this.certPasswd = "";

        this.reqContent = url;
        this.resContent = "";
        this.method = method;
        this.errInfo = "";
        this.timeOut = timeOut;//30秒

        this.responseCode = 0;
        this.charset = charset;

        this.inputStream = null;
    }

    /**
     * 设置证书信息
     *
     * @param certFile   证书文件
     * @param certPasswd 证书密码
     */
    public void setCertInfo(File certFile, String certPasswd) {
        this.certFile = certFile;
        this.certPasswd = certPasswd;
    }

    /**
     * 设置ca
     *
     * @param caFile
     */
    public void setCaInfo(File caFile) {
        this.caFile = caFile;
    }

    /**
     * 设置请求内容
     *
     * @param reqContent 表求内容
     */
    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    /**
     * 获取结果内容
     *
     * @return String
     * @throws IOException
     */
    public String getResContent() {
        try {
            this.doResponse();
        } catch (IOException e) {
            this.errInfo = e.getMessage();
            //return "";
        }

        return this.resContent;
    }

    /**
     * 设置请求方法post或者get
     *
     * @param method 请求方法post/get
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * 设置超时时间,以秒为单位
     *
     * @param timeOut 超时时间,以秒为单位
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 获取http状态码
     *
     * @return int
     */
    public int getResponseCode() {
        return this.responseCode;
    }


    /**
     * 发起HTTP/HTTPS请求(method=POST)
     * @param url
     * @return
     */
    public static String call4Post(String url) {
        try {
            URL url1 = new URL(url);
            if("https".equals(url1.getProtocol())) {
                return HttpClients.callHttpsPost(url);
            }else if("http".equals(url1.getProtocol())) {
                return HttpClients.callHttpPost(url);
            }else {
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 发起HTTP/HTTPS请求(method=GET)
     * @param url
     * @return
     */
    public static String call4Get(String url) {
        try {
            URL url1 = new URL(url);
            if("https".equals(url1.getProtocol())) {
                return HttpClients.callHttpsGet(url);
            }else if("http".equals(url1.getProtocol())) {
                return HttpClients.callHttpGet(url);
            }else {
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 执行http调用。true:成功 false:失败
     *
     * @return boolean
     */
    public boolean call() {

        boolean isRet = false;

        //http
        if (null == this.caFile && null == this.certFile) {
            try {
                this.callHttp();
                isRet = true;
            } catch (IOException e) {
                this.errInfo = e.getMessage();
            } catch (Exception e) {
                this.errInfo = e.getMessage();
            }
            return isRet;
        }

        //https
        return calls();

    }

    public boolean calls() {

        boolean isRet = false;

        //https
        try {
            this.callHttps();
            isRet = true;
        } catch (UnrecoverableKeyException e) {
            this.errInfo = e.getMessage();
        } catch (KeyManagementException e) {
            this.errInfo = e.getMessage();
        } catch (CertificateException e) {
            this.errInfo = e.getMessage();
        } catch (KeyStoreException e) {
            this.errInfo = e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            this.errInfo = e.getMessage();
        } catch (IOException e) {
            this.errInfo = e.getMessage();
        } catch (Exception e) {
            this.errInfo = e.getMessage();
        }
        return isRet;

    }

    protected void callHttp() throws IOException {

        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = HttpClientUtil.getQueryString(this.reqContent);
            byte[] postData = queryString.getBytes(this.charset);
            this.httpPostMethod(url, postData);

            return;
        }

        this.httpGetMethod(this.reqContent);

    }

    protected void callHttps() throws IOException, CertificateException,
            KeyStoreException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {

        // ca目录
        /*String caPath = this.caFile.getParent();

        File jksCAFile = new File(caPath + "/"
                + HttpClients.JKS_CA_FILENAME);
        if (!jksCAFile.isFile()) {
            X509Certificate cert = (X509Certificate) HttpClientUtil
                    .getCertificate(this.caFile);

            FileOutputStream out = new FileOutputStream(jksCAFile);

            // store jks file
            HttpClientUtil.storeCACert(cert, HttpClients.JKS_CA_ALIAS,
                    HttpClients.JKS_CA_PASSWORD, out);

            out.close();

        }

        FileInputStream trustStream = new FileInputStream(jksCAFile);
        FileInputStream keyStream = new FileInputStream(this.certFile);*/

		/*SSLContext sslContext = HttpClientUtil.getSSLContext(trustStream,
                HttpClients.JKS_CA_PASSWORD, keyStream, this.certPasswd);*/

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                new SecureRandom());

        //关闭流
        //keyStream.close();
        //trustStream.close();

        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = HttpClientUtil.getQueryString(this.reqContent);
            byte[] postData = queryString.getBytes(this.charset);

            this.httpsPostMethod(url, postData, sslContext);

            return;
        }

        this.httpsGetMethod(this.reqContent, sslContext);

    }

    /**
     * 以http post方式通信
     *
     * @param url
     * @param postData
     * @throws IOException
     */
    protected void httpPostMethod(String url, byte[] postData)
            throws IOException {

        HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);

        this.doPost(conn, postData);
    }

    /**
     * 以http get方式通信
     *
     * @param url
     * @throws IOException
     */
    protected void httpGetMethod(String url) throws IOException {

        HttpURLConnection httpConnection =
                HttpClientUtil.getHttpURLConnection(url);

        this.setHttpRequest(httpConnection);

        httpConnection.setRequestMethod("GET");

        this.responseCode = httpConnection.getResponseCode();

        this.inputStream = httpConnection.getInputStream();

    }

    /**
     * 以https get方式通信
     *
     * @param url
     * @param sslContext
     * @throws IOException
     */
    protected void httpsGetMethod(String url, SSLContext sslContext)
            throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doGet(conn);

    }

    protected void httpsPostMethod(String url, byte[] postData,
                                   SSLContext sslContext) throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doPost(conn, postData);

    }

    /**
     * 设置http请求默认属性
     *
     * @param httpConnection
     */
    protected void setHttpRequest(HttpURLConnection httpConnection) {

        //设置连接超时时间
        httpConnection.setConnectTimeout(this.timeOut * 1000);

        //User-Agent
        httpConnection.setRequestProperty("User-Agent",
                HttpClients.USER_AGENT_VALUE);

        //不使用缓存
        httpConnection.setUseCaches(false);

        //允许输入输出
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);

    }

    /**
     * 处理应答
     *
     * @throws IOException
     */
    protected void doResponse() throws IOException {

        if (null == this.inputStream) {
            return;
        }

        //获取应答内容
        this.resContent = HttpClientUtil.InputStreamTOString(this.inputStream, this.charset);

        //关闭输入流
        this.inputStream.close();

    }

    /**
     * post方式处理
     *
     * @param conn
     * @param postData
     * @throws IOException
     */
    protected void doPost(HttpURLConnection conn, byte[] postData)
            throws IOException {

        // 以post方式通信
        conn.setRequestMethod("POST");

        // 设置请求默认属性
        this.setHttpRequest(conn);

        // Content-Type
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");


        BufferedOutputStream out = new BufferedOutputStream(conn
                .getOutputStream());

        final int len = 1024; // 1KB
        HttpClientUtil.doOutput(out, postData, len);



        /*PrintWriter out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(new String(postData));
        // flush输出流的缓冲
        out.flush();*/


        // 关闭流
        out.close();

        // 获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        // 获取应答输入流
        this.inputStream = conn.getInputStream();

    }

    /**
     * get方式处理
     *
     * @param conn
     * @throws IOException
     */
    protected void doGet(HttpURLConnection conn) throws IOException {

        //以GET方式通信
        conn.setRequestMethod("GET");

        //设置请求默认属性
        this.setHttpRequest(conn);

        //获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        //获取应答输入流
        this.inputStream = conn.getInputStream();
    }

    public static String callHttpPost(String url) {
        return callHttpPost(url, 60); // 默认超时时间60秒
    }

    public static String callHttpPost(String url, int connect_timeout) {
        return callHttpPost(url, connect_timeout, "UTF-8"); // 默认编码 UTF-8
    }

    public static String callHttpPost(String url, int connect_timeout, String encode) {
        HttpClients client = new HttpClients(url, "POST", connect_timeout, encode);
        client.call();
        return client.getResContent();
    }

    public static String callHttpsPost(String url) {

        HttpClients client = new HttpClients(url, "POST", 60, "UTF-8");
        client.calls();
        return client.getResContent();

    }

    public static String callHttpGet(String url) {
        return callHttpGet(url, 60,"UTF-8"); // 默认超时时间60秒
    }

    public static String callHttpGet(String url, int connect_timeout, String encode) {
        HttpClients client = new HttpClients(url, "GET", connect_timeout, encode);
        client.call();
        return client.getResContent();
    }

    public static String callHttpsGet(String url) {

        HttpClients client = new HttpClients(url, "GET", 60, "UTF-8");
        client.calls();
        return client.getResContent();

    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
    public static class HttpClientUtil {

        public static final String SunX509 = "SunX509";
        public static final String JKS = "JKS";
        public static final String PKCS12 = "PKCS12";
        public static final String TLS = "TLS";

        private static final String encoding = "UTF-8";

        /**
         * get HttpURLConnection
         * @param strUrl url地址
         * @return HttpURLConnection
         * @throws IOException
         */
        public static HttpURLConnection getHttpURLConnection(String strUrl)
                throws IOException {
            URL url = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            return httpURLConnection;
        }

        /**
         * get HttpsURLConnection
         * @param strUrl url地址
         * @return HttpsURLConnection
         * @throws IOException
         */
        public static HttpsURLConnection getHttpsURLConnection(String strUrl)
                throws IOException {
            URL url = new URL(strUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
                    .openConnection();
            return httpsURLConnection;
        }

        /**
         * 获取不带查询串的url
         * @param strUrl
         * @return String
         */
        public static String getURL(String strUrl) {

            if(null != strUrl) {
                int indexOf = strUrl.indexOf("?");
                if(-1 != indexOf) {
                    return strUrl.substring(0, indexOf);
                }

                return strUrl;
            }

            return strUrl;

        }

        /**
         * 获取查询串
         * @param strUrl
         * @return String
         */
        public static String getQueryString(String strUrl) {

            if(null != strUrl) {
                int indexOf = strUrl.indexOf("?");
                if(-1 != indexOf) {
                    return strUrl.substring(indexOf+1, strUrl.length());
                }
                return "";
            }

            return strUrl;
        }

        /**
         * 查询字符串转换成Map<br/>
         * name1=key1&name2=key2&...
         * @param queryString
         * @return
         */
        public static Map queryString2Map(String queryString) {
            if(null == queryString || "".equals(queryString)) {
                return null;
            }

            Map m = new HashMap();
            String[] strArray = queryString.split("&");
            for(int index = 0; index < strArray.length; index++) {
                String pair = strArray[index];
                HttpClientUtil.putMapByPair(pair, m);
            }

            return m;

        }

        /**
         * 把键值添加至Map<br/>
         * pair:name=value
         * @param pair name=value
         * @param m
         */
        public static void putMapByPair(String pair, Map m) {

            if(null == pair || "".equals(pair)) {
                return;
            }

            int indexOf = pair.indexOf("=");
            if(-1 != indexOf) {
                String k = pair.substring(0, indexOf);
                String v = pair.substring(indexOf+1, pair.length());
                if(null != k && !"".equals(k)) {
                    m.put(k, v);
                }
            } else {
                m.put(pair, "");
            }
        }

        /**
         * BufferedReader转换成String<br/>
         * 注意:流关闭需要自行处理
         * @param reader
         * @return String
         * @throws IOException
         */
        public static String bufferedReader2String(BufferedReader reader) throws IOException {
            StringBuffer buf = new StringBuffer();
            String line = null;
            while( (line = reader.readLine()) != null) {
                buf.append(line);
                buf.append("\r\n");
            }

            return buf.toString();
        }

        /**
         * 处理输出<br/>
         * 注意:流关闭需要自行处理
         * @param out
         * @param data
         * @param len
         * @throws IOException
         */
        public static void doOutput(OutputStream out, byte[] data, int len)
                throws IOException {
            int dataLen = data.length;
            int off = 0;
            while (off < data.length) {
                if (len >= dataLen) {
                    out.write(data, off, dataLen);
                    off += dataLen;
                } else {
                    out.write(data, off, len);
                    off += len;
                    dataLen -= len;
                }

                // 刷新缓冲区
                out.flush();
            }

        }

        /**
         * 获取SSLContext
         * @param trustPasswd
         * @param keyPasswd
         * @return
         * @throws NoSuchAlgorithmException
         * @throws KeyStoreException
         * @throws IOException
         * @throws CertificateException
         * @throws UnrecoverableKeyException
         * @throws KeyManagementException
         */
        public static SSLContext getSSLContext(
                FileInputStream trustFileInputStream, String trustPasswd,
                FileInputStream keyFileInputStream, String keyPasswd)
                throws NoSuchAlgorithmException, KeyStoreException,
                CertificateException, IOException, UnrecoverableKeyException,
                KeyManagementException {

            // ca
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
            KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
            trustKeyStore.load(trustFileInputStream, HttpClientUtil
                    .str2CharArray(trustPasswd));
            tmf.init(trustKeyStore);

            final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
            KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
            ks.load(keyFileInputStream, kp);
            kmf.init(ks, kp);

            SecureRandom rand = new SecureRandom();
            SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

            return ctx;
        }

        /**
         * 获取CA证书信息
         * @param cafile CA证书文件
         * @return Certificate
         * @throws CertificateException
         * @throws IOException
         */
        public static Certificate getCertificate(File cafile)
                throws CertificateException, IOException {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream in = new FileInputStream(cafile);
            Certificate cert = cf.generateCertificate(in);
            in.close();
            return cert;
        }

        /**
         * 字符串转换成char数组
         * @param str
         * @return char[]
         */
        public static char[] str2CharArray(String str) {
            if(null == str) return null;

            return str.toCharArray();
        }

        /**
         * 存储ca证书成JKS格式
         * @param cert
         * @param alias
         * @param password
         * @param out
         * @throws KeyStoreException
         * @throws NoSuchAlgorithmException
         * @throws CertificateException
         * @throws IOException
         */
        public static void storeCACert(Certificate cert, String alias,
                                       String password, OutputStream out) throws KeyStoreException,
                NoSuchAlgorithmException, CertificateException, IOException {
            KeyStore ks = KeyStore.getInstance("JKS");

            ks.load(null, null);

            ks.setCertificateEntry(alias, cert);

            // store keystore
            ks.store(out, HttpClientUtil.str2CharArray(password));

        }

        public static InputStream String2Inputstream(String str) {
            return new ByteArrayInputStream(str.getBytes());
        }

        /**
         * InputStream转换成Byte
         * 注意:流关闭需要自行处理
         * @param in
         * @return byte
         * @throws Exception
         */
        public static byte[] InputStreamTOByte(InputStream in) throws IOException{

            int BUFFER_SIZE = 4096;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;

            while((count = in.read(data,0,BUFFER_SIZE)) != -1)
                outStream.write(data, 0, count);

            data = null;
            byte[] outByte = outStream.toByteArray();
            outStream.close();

            return outByte;
        }

        /**
         * InputStream转换成String
         * 注意:流关闭需要自行处理
         * @param in
         * @param encoding 编码
         * @return String
         * @throws Exception
         */
        public static String InputStreamTOString(InputStream in,String encoding) throws IOException{
            return new String(InputStreamTOByte(in),encoding);
        }


    }
}
