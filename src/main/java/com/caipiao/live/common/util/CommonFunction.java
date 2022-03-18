package com.caipiao.live.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author abu
 * @version : 1.00 Create Time : 2015-10-13 Description : 常用静态方法类
 */


public class CommonFunction {

    /**
     * 验证字符串是否为空，为空返回false，否则返回true
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotNull(String str) {
        return !(str == null || "".equals(str) || str.length() == 0);
    }

    /**
     * 验证一个对象是否为null，为空返回false，否则返回true
     *
     * @param obj 需要验证的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * 返回trim后的字符串
     *
     * @param str 需要做trim操作的字符串
     * @return String
     */
    public static String trim(String str) {
        return isNotNull(str) ? str.trim() : "";
    }

    // 替换字符串中的非法数据，key为替换目标，value为替换值，搜索页面专用
    private static final Map<String, String> filterStrForSearch = new HashMap<String, String>();

    // 初始化替换数据
    static {
        filterStrForSearch.put("<", "");
        filterStrForSearch.put(">", "");
        filterStrForSearch.put("'", "");
        filterStrForSearch.put("\"", "");
        filterStrForSearch.put("+", "");
        filterStrForSearch.put("_", "");
        filterStrForSearch.put("%", "");
        filterStrForSearch.put("\\r", "");
        filterStrForSearch.put("\\n", "");
        filterStrForSearch.put("\\t", "");
    }

    /**
     * 过滤传入对象中的字符串的非法字符
     *
     * @param value
     * @return
     */
    public static String filterString(String value) {
        // 去除空格
        value = trim(value);
        // 循环将当前字符串的值进行替换
        for (Map.Entry<String, String> entry : filterStrForSearch.entrySet()) {
            value = value.replace(entry.getKey(), entry.getValue());
        }
        return value;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @return uuid
     * @Title: getUUID
     * @Description: 得到一个uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param money 钱
     * @return 结果
     * @Title: viewMoneyToDB
     * @Description: 将显示的钱转换成数据库的钱
     */
    public static Long viewMoneyToDB(String money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        bigDecimal = bigDecimal.multiply(new BigDecimal(1000));
        return bigDecimal.longValue();
    }

    /**
     * @param money 钱
     * @return 结果
     * @Title: dbMoneyToView
     * @Description: 将数据库的钱转换成显示的钱
     */
    public static String dbMoneyToView(Long money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        bigDecimal = bigDecimal.divide(new BigDecimal(1000));
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue() + "";
    }

    private static String hexString = "0123456789ABCDEF";

    /**
     * @param str 要转换的字符串
     * @return 结果
     * @throws UnsupportedEncodingException
     * @Title: encodeHex
     * @Description: 将字符串转换为十六进制
     */
    public static String encodeHex(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = {};
        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * @Title: getSixRandomSmsCode @Description: 产生随机的4六位数 @return String
     * 返回类型 @throws
     */
    public static String getFourRandomSmsCode() {
        Random rad = new Random();
        String resultStr = String.valueOf(rad.nextInt(10000));
        int num = 4;
        if (resultStr.length() != num) {
            return getFourRandomSmsCode();
        }
        return resultStr;
    }

    /**
     * 6位数邀请码 大小写加数字
     *
     * @return
     */
    public static String inviteCodeForKey() {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = s + S + i;
        // 获取包含26个字母大小写和数字的字符数组
        char[] c = word.toCharArray();
        // char[] c= charArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 6; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return code;
    }


    /**
     * 6位数邀请码 大写加数字
     *
     * @return
     */
    public static String inviteCode() {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = S + i;
        // 获取包含26个字母大写和数字的字符数组
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 6; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return code;
    }


    /**
     * listToTree
     * <p>方法说明<p>
     * 将JSONArray数组转为树状结构
     *
     * @param arr   需要转化的数据
     * @param id    数据唯一的标识键值
     * @param pid   父id唯一标识键值
     * @param child 子节点键值
     * @return JSONArray
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);

            //判空
            if (aVal.get(pid) == null) {
                r.add(aVal);
                continue;
            }
            //在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVP != null) {
                //检查是否有child属性
                if (hashVP.get(child) != null) {
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            }
            //去掉 没有父节点 就生产根结点
			/*else{
			 r.add(aVal);
			}*/

        }
        return r;
    }

    public static boolean imageSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp")
                || fileName.endsWith(".gif") || fileName.endsWith(".png")|| fileName.endsWith(".svga")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean videoSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        // 视频格式主要有rm,rmvb,mpeg1-4 mov mtv dat wmv avi 3gp amv dmv flv
        if (fileName.endsWith(".mp4") || fileName.endsWith(".flv") || fileName.endsWith(".3gp")
                || fileName.endsWith(".avi") || fileName.endsWith(".rmvb") || fileName.endsWith(".wmv")) {
            return true;
        } else {
            return false;
        }
    }


    // 加密
    public static String jiaMi(String s, String key) {
        String str = "";
        int ch;
        if (key.length() == 0) {
            return s;
        } else if (!s.equals(null)) {
            for (int i = 0, j = 0; i < s.length(); i++, j++) {
                if (j > key.length() - 1) {
                    j = j % key.length();
                }
                ch = s.codePointAt(i) + key.codePointAt(j);
                if (ch > 65535) {
                    ch = ch % 65535;// ch - 33 = (ch - 33) % 95 ;
                }
                str += (char) ch;
            }
        }
        return str;

    }

    // 解密
    public static String jieMi(String s, String key) {
        String str = "";
        int ch;
        if (key.length() == 0) {
            return s;
        } else if (!s.equals(key)) {
            for (int i = 0, j = 0; i < s.length(); i++, j++) {
                if (j > key.length() - 1) {
                    j = j % key.length();
                }
                ch = (s.codePointAt(i) + 65535 - key.codePointAt(j));
                if (ch > 65535) {
                    ch = ch % 65535;// ch - 33 = (ch - 33) % 95 ;
                }
                str += (char) ch;
            }
        }
        return str;
    }


    // 参数类型是Map<String,String> 因为支付只能用string的参数。如果诸君还需要修改的话，那也可以适当的做调整

    /**
     * map转str
     *
     * @param map
     * @return
     */
    public static String getMapToString(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        // 将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        // 给数组排序(升序)
        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if (map.get(keyArray[i]).trim().length() > 0) {
                sb.append(keyArray[i]).append("=").append(map.get(keyArray[i]).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * map 转 str
     * 用于ag
     *
     * @param map
     * @return
     */
    public static String getMapToStringForAG(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        // 将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        // 给数组排序(升序)
        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if (map.get(keyArray[i]).trim().length() > 0) {
                sb.append(keyArray[i]).append("=").append(map.get(keyArray[i]).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append("/\\\\\\\\/");
            }
        }
        return sb.toString();
    }


    public static List<String> jiandanPwd() {
        List<String> list = new ArrayList<String>();
        // 123456
        list.add("e10adc3949ba59abbe56e057f20f883e");
        // 111111
        list.add("96e79218965eb72c92a549dd5a330112");
        // 222222
        list.add("e3ceb5881a0a1fdaad01296d7554868d");
        // 888888
        list.add("21218cca77804d2ba1922c33e0151105");
        // 666666
        list.add("f379eaf3c831b04de153469d1bec345e");
        // 444444
        list.add("73882ab1fa529d7273da0db6b49cc4f3");
        // 333333
        list.add("1a100d2c0dab19c4430e7d73762b3423");
        // 222222
        list.add("e3ceb5881a0a1fdaad01296d7554868d");
        // 000000
        list.add("670b14728ad9902aecba32e22fa4f6bd");
        // 555555
        list.add("5b1b68a9abf4d2cd155c81a9225fd158");
        // 777777
        list.add("f63f4fbc9f8c85d409f2f59f2b9e12d5");
        // 1234567
        list.add("fcea920f7412b5da7be0cf42b8c93759");
        // 12345678
        list.add("25d55ad283aa400af464c76d713c07ad");
        // 123456789
        list.add("25f9e794323b453885f5181f1b624d0b");
        // 1234567890
        list.add("e807f1fcf82d132f9bb018ca6738a19f");
        // 0123456789
        list.add("781e5e245d69b566979b86e28d23f2c7");
        return list;
    }


    /**
     * 字符串自能是字母和数字
     *
     * @param str
     * @return
     */
    public static boolean numbersAndletters(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
}
