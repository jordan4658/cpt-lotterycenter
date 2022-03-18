package com.caipiao.task.server.util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisBusinessUtil;
import com.mapper.domain.AusactLotterySg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.Proxy.Type;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 获取赛果接口数据工具类
 *
 * @author lzy
 * @create 2018-07-26 23:07
 */

@Configuration
//@ConfigurationProperties("sg")
//@PropertySource("classpath:application.properties")
public class GetHttpsgUtil implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GetHttpsgUtil.class);

    @Value("${sg.kcwtoken}")
    private String kcwtoken;

    @Value("${sg.cpktoken}")
    private String cpktoken;

    @Value("${sg.cpkuid}")
    private String cpkuid;

    @Value("${sg.bywtoken}")
    private String bywtoken;

    @Value("${sg.proxy_ip}")
    private String proxy_ip;

    @Value("${sg.proxy_port}")
    private String proxy_port;

    @Value("${sg.proxy_ip1}")
    private String proxy_ip1;

    @Value("${sg.proxy_port1}")
    private String proxy_port1;

    /**
     * 获取【彩票控】赛果记录
     *
     * @param code 彩种编号
     * @param num  记录条数
     * @return
     */
    public static List<LotterySgModel> getCpkSg(String code, int num) {
        ArrayList<LotterySgModel> sgModels = new ArrayList<>();
        //     String uid = "1039254"; // 用户ID
        //    String token = "e9f25742da0f490e24243acd2bf90c9c9dfa56cb"; // token
        String charset = "UTF-8";

        // 拼装URL
        String url = "http://api.caipiaokong.com/lottery/?"
                + "name=" + code
                + "&format=json" // 数据格式，此文件仅支持json
                + "&uid=" + CPKUID
                + "&token=" + CPKTOKEN
                + "&num=" + num; // 默认只获取一条赛果记录

        try {
            String jsonStr = get(url, charset); // 得到JSON字符串
            if (StringUtils.isBlank(jsonStr)) {
                return sgModels;
            }
            JSONObject object = JSONObject.parseObject(jsonStr); // 转化为JSON类

            Set<Map.Entry<String, Object>> entries = object.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                String entryKey = entry.getKey();
                Map<String, String> value = (Map<String, String>) entry.getValue();
                // 判断是否有开奖号码
                if (StringUtils.isBlank(value.get("number"))) {
                    return sgModels;
                }
                LotterySgModel sgModel = new LotterySgModel();
                sgModel.setIssue(entryKey);
                sgModel.setSg(value.get("number"));
                sgModel.setDate(value.get("dateline"));
                sgModels.add(sgModel);
            }
        } catch (JSONException e) {
            logger.error("【彩票控】获取赛果失败！" + code + e);
            e.printStackTrace();
        }
        return sgModels;
    }

    /**
     * 获取【博易网】赛果记录
     *
     * @param code 彩种编号
     * @param num  记录条数
     * @return
     */
    public static List<LotterySgModel> getBywSg(String code, int num) {
        ArrayList<LotterySgModel> sgModels = new ArrayList<>();
        //     String uid = "1039254"; // 用户ID
        //    String token = "e9f25742da0f490e24243acd2bf90c9c9dfa56cb"; // token
        String charset = "UTF-8";
//        http://api.b1api.com/api?p=json&t=cqssc&limit=1&token=99AFFE5A0B80F3BE
        // 拼装URL token=99B7713E7D02DDD0&code=xjssc&rows=5&format=json
        String url = "http://api2.php2019.com/api?"
                + "format=json" // 数据格式，此文件仅支持json
                + "&code=" + code
                + "&rows=" + num
                + "&token=" + BYWTOKEN;   // 默认只获取一条赛果记录
        try {
            String jsonStr = get(url, charset); // 得到JSON字符串
            if (StringUtils.isBlank(jsonStr)) {
                return sgModels;
            }
            JSONObject object = JSONObject.parseObject(jsonStr); // 转化为JSON类

//            Set<Map.Entry<String, Object>> entries = object.getJSONArray("data");
            JSONArray array = object.getJSONArray("data");

            for (int i = 0; i < array.size(); i++) {
                JSONObject entry = array.getJSONObject(i);
//                Map<String, String> value = (Map<String, String>) entry.getValue();
                // 判断是否有开奖号码
                if (StringUtils.isBlank(entry.getString("opencode"))) {
                    return sgModels;
                }
                LotterySgModel sgModel = new LotterySgModel();
                sgModel.setIssue(entry.getString("expect"));
                sgModel.setSg(entry.getString("opencode"));
                sgModel.setDate(entry.getString("opentime"));
                sgModels.add(sgModel);
            }
        } catch (JSONException e) {
            logger.error("博易网获取赛果失败！" + code + e);
        }
        return sgModels;
    }

//    /**
//     * 获取【彩票控】通过代理赛果记录
//     *
//     * @param code 彩种编号
//     * @param num  记录条数
//     * @return
//     */
//    public static List<LotterySgModel> getAgencyCpkSg(String code, int num) {
//        List<LotterySgModel> list = new ArrayList<>();
//        SocketAddress address = new InetSocketAddress(cpk_proxy_ip, Integer.valueOf(cpk_proxy_port));
////        SocketAddress address = new InetSocketAddress("54.206.99.68", 3128);
//        Proxy proxy = new Proxy(Type.HTTP, address);
//        ArrayList<LotterySgModel> sgModels = new ArrayList<>();
//
//        try {
//            // 拼装URL
//            String urlString = "http://api.caipiaokong.com/lottery/?"
//                    + "name=" + code
//                    + "&format=json" // 数据格式，此文件仅支持json
//                    + "&uid=" + CPKUID
//                    + "&token=" + CPKTOKEN
//                    + "&num=" + num; // 默认只获取一条赛果记录
////            String content = HttpClientUtil.httpGet(urlString, getProxyHost());
//
//
//            URL url = new URL(urlString);
//            URLConnection connection = url.openConnection(proxy);
//            connection.setConnectTimeout(30000);
//            connection.setReadTimeout(30000);
//            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
//
//            String jsonStr = ParseStream(connection.getInputStream());
//            if (StringUtils.isBlank(jsonStr)) {
//                return sgModels;
//            }
//            JSONObject object = JSONObject.parseObject(jsonStr); // 转化为JSON类
//            try {
//                Set<Map.Entry<String, Object>> entries = object.entrySet();
//                for (Map.Entry<String, Object> entry : entries) {
//                    String entryKey = entry.getKey();
//                    Map<String, String> value = (Map<String, String>) entry.getValue();
//                    // 判断是否有开奖号码
//                    if (StringUtils.isBlank(value.get("number"))) {
//                        return sgModels;
//                    }
//                    LotterySgModel sgModel = new LotterySgModel();
//                    sgModel.setIssue(entryKey);
//                    sgModel.setSg(value.get("number"));
//                    sgModel.setDate(value.get("dateline"));
//                    sgModels.add(sgModel);
//                }
//            } catch (JSONException e) {
//                logger.error("【彩票控】获取赛果通过代理失败！" + code + e);
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            logger.error("【彩票控】获取赛果通过代理失败！" + code + e);
//            e.printStackTrace();
//        }
//        return sgModels;
//    }


    /**
     * 获取【开彩网】赛果记录
     *
     * @param code 彩种编号
     * @param num  记录条数
     * @return
     */
    public static List<LotterySgModel> getKcwSg(String code, int num) {
        List<LotterySgModel> results = new ArrayList<>();
        //     String token = "t0248909c3620ff1bk";
        String charset = "UTF-8";
        String url = "http://wd.apiplus.net/newly.do?"
                + "token=" + KCWTOKEN
                + "&code=" + code
                + "&rows=" + num
                + "&format=json";
        try {
            String jsonStr = get(url, charset);
            if (StringUtils.isBlank(jsonStr)) {
                return results;
            }
            // 解析返回的json字符串
            JSONObject object = JSONObject.parseObject(jsonStr); // 转化为JSON类
            // 获取开奖期数信息
            JSONArray data = object.getJSONArray("data");
            LotterySgModel model;
            // 遍历获取出的赛果
            for (int i = 0; i < data.size(); i++) {
                model = new LotterySgModel();
                JSONObject jsonObject = data.getJSONObject(i);
                model.setIssue(jsonObject.getString("expect"));
                model.setDate(jsonObject.getString("opentime"));
                model.setSg(jsonObject.getString("opencode"));
                results.add(model);
            }
        } catch (Exception e) {
            logger.error("【开彩网】获取赛果失败！" + code + e);
        }
        return results;
    }

    /**
     * 请求接口获取json格式数据
     *
     * @param url     彩种
     * @param charset 字符编码
     * @return 返回json结果
     */
    public static String get(String url, String charset) {
        String result = "";
        try {
            BufferedReader reader;
            StringBuilder sbf = new StringBuilder();
            String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setRequestProperty("User-agent", userAgent);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            logger.error("抓取地址出错{}，{}", url, e);
            e.printStackTrace();
        }
        return result;
    }

    public static List<LotterySgModel> getAct(AusactLotterySg ausactLotterySg) {
        List<LotterySgModel> list = new ArrayList<>();

        //当1小时内代理ip抓取超时 超过10次
        String proxyIpThis = null;
        proxyIpThis = PROXYIP;

        //当小于150秒的时候，不需要抓取，减少对澳洲网站访问频率，查询act最近1条数据， 如果最近1条数据是预期数据，且当前时间小于预期的开奖时间，
        //则跳过，否则则爬虫抓取，
        if (StringUtils.isNotEmpty(ausactLotterySg.getOpenStatus()) && ausactLotterySg.getOpenStatus().equals(Constants.STATUS_WAIT)) {
            if (ausactLotterySg.getIdealTime().compareTo(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) > 0) {
                logger.info("澳洲系列抓取跳过");
                return list;
            }
        }

        //ip代理轮询切换抓取数据
        if (RedisBusinessUtil.redisTemplate.hasKey("proxyIpZx") && RedisBusinessUtil.redisTemplate.opsForValue().get("proxyIpZx").equals(PROXYIP)) {
            proxyIpThis = PROXYIP1;
        } else if (RedisBusinessUtil.redisTemplate.hasKey("proxyIpZx") && !RedisBusinessUtil.redisTemplate.opsForValue().get("proxyIpZx").equals(PROXYIP1)) {
            proxyIpThis = PROXYIP;
        }

        //最近执行的ip
        RedisBusinessUtil.redisTemplate.opsForValue().set("proxyIpZx", proxyIpThis);
        SocketAddress address = new InetSocketAddress(proxyIpThis, Integer.valueOf(PROXYPORT));

        Proxy proxy = new Proxy(Type.HTTP, address);

        try {
            URL url = new URL("https://api-info-act.keno.com.au/v2/info/history?jurisdiction=ACT");

//            SSLContext sslContext = SSLContext.getInstance("SSL");
////            sslContext.init(null, new TrustManager[][] {}, null);
//            sslContext.init(null,new TrustManager[]{},null);
//            SSLSocketFactory sslFactory = sslContext.getSocketFactory();
//            URLConnection httpURLConnection = (URLConnection) url.openConnection(proxy);
////            String encoded = new sun.misc.BASE64Encoder().encodeBuffer(("cptproxy" + ":" + "Yx75AQp4u*3G8").getBytes()).replace("\r\n", "");
//            String encoded = "Basic " + Base64.getEncoder().encodeToString("cptproxy:Yx75AQp4u*3G8".getBytes());
////            httpURLConnection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
////            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
//            httpURLConnection.setRequestProperty("Proxy-Authorization", encoded);
////            httpURLConnection.setDoInput(true);
////            httpURLConnection.setHostnameVerifier((v1, v2) -> true);
////            httpURLConnection.setSSLSocketFactory(sslFactory);
////            httpURLConnection.setRequestMethod("GET");
////            httpURLConnection.connect();
//            InputStream is = httpURLConnection.getInputStream();
//            String jsonOne = ParseStream(is);

            URLConnection connection = url.openConnection(proxy);
            connection.setConnectTimeout(7000);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
//            connection.getContent();
            URL url2 = new URL("https://api-info-act.keno.com.au/v2/games/kds?jurisdiction=ACT");
//            String text1 = doProxy("https://api-info-act.keno.com.au/v2/info/history?jurisdiction=ACT");


            URLConnection connection2 = url2.openConnection(proxy);
            connection2.setConnectTimeout(7000);
            connection2.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
//            connection2.getContent();
            String json = ParseStream(connection.getInputStream());
            String jsonnew = ParseStream(connection2.getInputStream());
            JSONObject current = null;
            JSONObject selling = null;
            if (StringUtils.isNotBlank(jsonnew)) {
                JSONObject actobj = JSONObject.parseObject(jsonnew);
                current = actobj.getJSONObject("current");
                selling = actobj.getJSONObject("selling");
            }

            if (StringUtils.isNotBlank(json)) {
                JSONObject actobj = JSONObject.parseObject(json);
                JSONArray actarry = actobj.getJSONArray("items");
                if (current != null) {
                    actarry.add(current);
                }
                for (Object fl : actarry) {
                    int issue = ((JSONObject) fl).getInteger("game-number");
                    JSONArray draw = ((JSONObject) fl).getJSONArray("draw");
                    String sg = "";
                    for (Object num : draw) {
                        sg += num + ",";
                    }
                    String date = ((JSONObject) fl).getString("closed");
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    //设置时区UTC
                    df.setTimeZone(TimeZone.getTimeZone("UTC"));
                    //格式化，转当地时区时间
                    Date after = df.parse(date);


                    df.applyPattern(DateUtils.fullDatePattern);
                    //默认时区
                    df.setTimeZone(TimeZone.getDefault());
                    String dateact = df.format(after);
                    df.applyPattern("yyyyMMdd");
                    LotterySgModel model = new LotterySgModel();
                    model.setIssue(df.format(after) + String.format("%03d", issue));
                    model.setSg(sg.substring(0, sg.length() - 1));
                    model.setDate(dateact);
                    model.setStatus("AUTO");
                    model.setOpenTime(dateact);
                    list.add(model);
                }

                //添加一个 预期数据
                int issue = selling.getInteger("game-number");
                String date = selling.getString("closing");

                System.out.println(current.getInteger("game-number") + "," + issue);

                LotterySgModel model = new LotterySgModel();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


                //设置时区UTC
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                //格式化，转当地时区时间
                Date dDate = df.parse(date);

                df.applyPattern(DateUtils.fullDatePattern);
                //默认时区
                df.setTimeZone(TimeZone.getDefault());
                String dateShiqu = df.format(dDate);

                df.applyPattern("yyyyMMdd");
                model.setDate(dateShiqu);
                model.setIssue(df.format(dDate) + String.format("%03d", issue));
                list.add(model);

            }
        } catch (Exception e) {
            //抓取策略， 部署两个代理ip，轮询抓取，如果一个1个小时内，某个代理超时次数超过等于10次，则认为此代理异常，屏蔽此代理
            //初始化，清空缓存proxyIpZx,yichangIp,yichangIp1

            int numberTimeOut = 10; //设置的阈值，超过多少次就认为此代理异常，屏蔽掉
            String valueLast = "";
            String keyLast = "";
            int chaoshiNumber = 0; //第一个ip超时次数
            int chaoshiNumber1 = 0;
            if (proxyIpThis.equals(PROXYIP)) {
                keyLast = "yichangIp";
            } else {
                keyLast = "yichangIp1";
            }

            String key = "yichangIp";
            if (RedisBusinessUtil.redisTemplate.hasKey(key)) {
                String value = String.valueOf(RedisBusinessUtil.redisTemplate.opsForValue().get(key));
                String valueArray[] = value.split(";");
                chaoshiNumber = Integer.valueOf(valueArray[0]);
            }
            if (chaoshiNumber >= numberTimeOut) {
                PROXYIP = PROXYIP1;
            }


            String key1 = "yichangIp1";
            if (RedisBusinessUtil.redisTemplate.hasKey(key1)) {
                String value = String.valueOf(RedisBusinessUtil.redisTemplate.opsForValue().get(key1));
                String valueArray[] = value.split(";");
                chaoshiNumber1 = Integer.valueOf(valueArray[0]);
            }
            if (chaoshiNumber1 >= numberTimeOut) {
                PROXYIP1 = PROXYIP;
            }


            if (chaoshiNumber < numberTimeOut && chaoshiNumber1 < numberTimeOut) {
                if (RedisBusinessUtil.redisTemplate.hasKey(keyLast)) {
                    String value = String.valueOf(RedisBusinessUtil.redisTemplate.opsForValue().get(keyLast));
                    String valueArray[] = value.split(";");
                    String thisDateS = valueArray[1];
                    Date thisDate = DateUtils.parseDate(thisDateS, DateUtils.fullDatePattern);
                    //小于1个小时，则累加，如果大于1个小时，则清零

                    if (new Date().getTime() - thisDate.getTime() > 3600 * 1000) {
                        RedisBusinessUtil.redisTemplate.opsForValue().set(keyLast, 1 + ";" + DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                    } else {
                        logger.info("时间间隔：{}", new Date().getTime() - thisDate.getTime());
                        RedisBusinessUtil.redisTemplate.opsForValue().set(keyLast, (Integer.valueOf(valueArray[0]) + 1) + ";" + thisDateS);
                    }

                } else {
                    //value:次数+;+时间
                    RedisBusinessUtil.redisTemplate.opsForValue().set(keyLast, 1 + ";" + DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                }
            }
            logger.error("澳洲系列抓取失败{}", e);
        }

        return list;
    }


//    /**
//     * 通过系统变量方式实现代理
//     *
//     * @param url
//     * @return
//     */
//    public static String doProxy(String url) {
//        // 设置系统变量
//
//        System.setProperty("http.proxySet", "true");
//        System.setProperty("http.proxyHost", "54.206.99.68");
//        System.setProperty("http.proxyPort", "" + "3128");
//        // 针对https也开启代理
//        System.setProperty("https.proxyHost", "54.206.99.68");
//        System.setProperty("https.proxyPort", "" + "3128");
//        // 设置默认校验器
//        setDefaultAuthentication();
//
//        //开始请求
//        try {
//            URL u = new URL(url);
//            URLConnection conn = u.openConnection();
//            HttpsURLConnection httpsCon = (HttpsURLConnection) conn;
//            httpsCon.setFollowRedirects(true);
//
//            String encoding = conn.getContentEncoding();
//            if (StringUtils.isEmpty(encoding)) {
//                encoding = "UTF-8";
//            }
//            InputStream is = conn.getInputStream();
////            toString(is, encoding);
//            String content = ParseStream(is);
////            String content = IOUtils.toString(is, encoding);
//
//            return content;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
//
//    /**
//     * 设置全局校验器对象
//     */
//    public static void setDefaultAuthentication() {
//        BasicAuthenticator auth = new BasicAuthenticator("cptproxy", "Yx75AQp4u*3G8");
//        Authenticator.setDefault(auth);
//    }

    public static String ParseStream(InputStream stream) {
        StringBuilder builder = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String strtmp;
            try {
                strtmp = reader.readLine();
                while (null != strtmp) {
                    builder.append(strtmp);
                    builder.append("\n");
                    strtmp = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String CPKTOKEN;
    public static String KCWTOKEN;
    public static String CPKUID;
    public static String BYWTOKEN;
    public static String PROXYIP;
    public static String PROXYPORT;
    public static String PROXYIP1;
    public static String PROXYPORT1;

    @Override
    public void afterPropertiesSet() {
        CPKTOKEN = this.cpktoken;
        KCWTOKEN = this.kcwtoken;
        CPKUID = this.cpkuid;
        BYWTOKEN = this.bywtoken;
        PROXYIP = this.proxy_ip;
        PROXYPORT = this.proxy_port;
        PROXYIP1 = this.proxy_ip1;
        PROXYPORT1 = this.proxy_port1;
    }
}
