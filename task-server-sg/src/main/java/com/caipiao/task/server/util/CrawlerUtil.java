package com.caipiao.task.server.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerUtil {

    /**
     * @param charset 字符编码
     * @return
     */
    public static List<String> getH(String charset) {
        String url = "http://www.off0.com/index.php";
        String urlAll = new StringBuffer(url).toString();
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// 模拟浏览器
        try {
            URL httpUrl = new URL(urlAll);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setRequestProperty("User-agent", userAgent);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String regex = "(<td[^>]*>[^<]*</td>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        List<String> list = new ArrayList<>();
        int count = 0;
        while (matcher.find()) {
            String group = matcher.group();
            list.add(group.substring(4, group.length()-5));
            ++count;
            if (count == 6) {
                break;
            }
        }
        return list;
    }

}
