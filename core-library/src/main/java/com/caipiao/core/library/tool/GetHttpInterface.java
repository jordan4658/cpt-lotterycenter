package com.caipiao.core.library.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取http接口数据工具类
 * @author lzy
 * @create 2018-07-26 23:07
 */
public class GetHttpInterface {

	private static final Logger logger = LoggerFactory.getLogger(GetHttpInterface.class);

    public static void main(String[] args) {
//        // 此处需要传递的3个参数，必填项
//        String name = "cqssc";// 彩种
//        String uid = "1039254";// 用户ID
//        String token = "e9f25742da0f490e24243acd2bf90c9c9dfa56cb";// token
//        String charset = "UTF-8";
//
//        // 拼装URL
//        String url = "http://api.caipiaokong.com/lottery/?"
//                + "name=" + name
//                + "&format=json" // 数据格式，此文件仅支持json
//                + "&uid=" + uid
//                + "&token=" + token
//                + "&num=1"; // 默认只获取一条赛果记录
//
//        String jsonResult = get(url, charset);// 得到JSON字符串
//        JSONObject object = JSONObject.parseObject(jsonResult);// 转化为JSON类
//        try {
//            Set<Map.Entry<String, Object>> entries = object.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                String key  = entry.getKey();
//                Map<String, String> value1 = (Map<String, String>)entry.getValue();
//                String outputStr = "id:" + key;
//                outputStr += " number:" + value1.get("number");
//                outputStr += " dateline:" + value1.get("dateline");
//                System.out.println(outputStr);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        List<LotterySgModel> list = getKcwSg("bjkl8", 5);
        for (LotterySgModel sgModel : list) {
            logger.info(sgModel.getIssue() + "=======" + sgModel.getSg() + "=======" + sgModel.getDate());
        }
    }

    /**
     * 获取【彩票控】赛果记录
     * @param code 彩种编号
     * @param num 记录条数
     * @return
     */
    public static List<LotterySgModel> getCpkSg(String code, int num) {
        ArrayList<LotterySgModel> sgModels = new ArrayList<>();
        String uid = "1039254"; // 用户ID
        String token = "e9f25742da0f490e24243acd2bf90c9c9dfa56cb"; // token
        String charset = "UTF-8";

        // 拼装URL
        String url = "http://api.caipiaokong.com/lottery/?"
                + "name=" + code
                + "&format=json" // 数据格式，此文件仅支持json
                + "&uid=" + uid
                + "&token=" + token
                + "&num=" + num; // 默认只获取一条赛果记录

        String jsonStr = get(url, charset); // 得到JSON字符串
        if (StringUtils.isBlank(jsonStr)) {
            return sgModels;
        }
        JSONObject object = JSONObject.parseObject(jsonStr); // 转化为JSON类
        try {
            Set<Map.Entry<String, Object>> entries = object.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                String entryKey  = entry.getKey();
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
            e.printStackTrace();
        }
        return sgModels;
    }

    /**
     * 获取【开彩网】赛果记录
     * @param code 彩种编号
     * @param num 记录条数
     * @return
     */
    public static List<LotterySgModel> getKcwSg(String code, int num) {
        List<LotterySgModel> results = new ArrayList<>();
        String token = "t0248909c3620ff1bk";
        String charset = "UTF-8";
        String url = "http://wd.apiplus.net/newly.do?"
                   + "token=" + token
                   + "&code=" + code
                   + "&rows=" + num
                   + "&format=json";

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
        return results;
    }

    /**
     * 请求接口获取json格式数据
     * @param url 彩种
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
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
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
            e.printStackTrace();
        }
        return result;
    }

}
