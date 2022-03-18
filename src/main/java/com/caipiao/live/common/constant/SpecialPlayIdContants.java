package com.caipiao.live.common.constant;


import com.caipiao.live.common.util.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 风控页面计算投注额时，部分玩法需要特殊计算
 */
public class SpecialPlayIdContants {

    public static final List<Integer> SPECIAL_PLAY_IDS = Arrays.asList(170101, 170303, 170302, 170301, 170201, 170202, 180201,
            180202, 180203, 180206, 150104, 150204, 180301, 180101, 120111, 120121, 120411, 120421, 120311, 120321, 120211, 120221);

    public static final List<Integer> NEED_SPLIT_SPECIAL_PLAY_IDS = Arrays.asList(170304, 170305, 170203, 170204, 180210);
    public static Map<Integer, List<String>> playPropertyMap = new HashMap<>();

    public static Map<String, List<String>> specialPlayMap = new HashMap<>();

    static {
        List<String> shuxings = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
        List<String> sscOneToTens = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        List<String> oneToTens = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        List<String> liangmiangyh = Arrays.asList("冠亚大", "冠亚小", "冠亚单", "冠亚双");  //冠亚和   -pk系列，幸运飞艇系列
        List<String> liangmian15 = Arrays.asList("大", "小", "单", "双", "龙", "虎"); //1-5名
        List<String> liangmian610 = Arrays.asList("大", "小", "单", "双"); //6-10名

        List<String> liangmianzhlh = Arrays.asList("总和大", "总和小", "总和单", "总和双", "龙", "虎", "和");  //总和、龙虎        澳洲时时彩
        List<String> liangmianoneFive = Arrays.asList("大", "小", "单", "双");  //第一球到第五球        澳洲时时彩

        specialPlayMap.put("110103", sscOneToTens);
//		specialPlayMap.put("110104", sscOneToTens);
        specialPlayMap.put("110203", sscOneToTens);
//		specialPlayMap.put("110204", sscOneToTens);
        specialPlayMap.put("110303", sscOneToTens);
//		specialPlayMap.put("110304", sscOneToTens);
        specialPlayMap.put("110403", sscOneToTens);
//		specialPlayMap.put("110404", sscOneToTens);
        specialPlayMap.put("110503", sscOneToTens);
//		specialPlayMap.put("110504", sscOneToTens);
        specialPlayMap.put("110603", sscOneToTens);
//		specialPlayMap.put("110604", sscOneToTens);

        specialPlayMap.put("130101", liangmian610);
        specialPlayMap.put("130201", liangmian610);
        specialPlayMap.put("130301", liangmian610);
        specialPlayMap.put("130401", liangmian610);
        specialPlayMap.put("140101", liangmian610);
        specialPlayMap.put("140201", liangmian610);

        specialPlayMap.put("13010101", liangmiangyh);
        specialPlayMap.put("13010102", liangmian15);

        specialPlayMap.put("220201", liangmianoneFive);
        specialPlayMap.put("22020101", liangmianzhlh);

        specialPlayMap.put("130103", oneToTens);
        specialPlayMap.put("130104", oneToTens);
        specialPlayMap.put("130203", oneToTens);
        specialPlayMap.put("130204", oneToTens);
        specialPlayMap.put("130303", oneToTens);
        specialPlayMap.put("130304", oneToTens);
        specialPlayMap.put("130403", oneToTens);
        specialPlayMap.put("130404", oneToTens);
        specialPlayMap.put("140103", oneToTens);
        specialPlayMap.put("140203", oneToTens);
        specialPlayMap.put("140104", oneToTens);
        specialPlayMap.put("140204", oneToTens);
        specialPlayMap.put("170305", shuxings);
        specialPlayMap.put("170304", Arrays.asList("大", "小", "单", "双"));
        specialPlayMap.put("170203", Arrays.asList("大", "小", "单", "双"));
        specialPlayMap.put("170204", shuxings);
        specialPlayMap.put("180210", Arrays.asList("大", "小", "单", "双"));
        specialPlayMap.put("180204", shuxings);
        specialPlayMap.put("180205", shuxings);
        specialPlayMap.put("180206", shuxings);
        specialPlayMap.put("220303", oneToTens);
        specialPlayMap.put("220304", oneToTens);
        specialPlayMap.put("150104", Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));
        specialPlayMap.put("150204", Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));

        specialPlayMap.put("120143", Arrays.asList("1-2球", "1-3球", "1-4球", "1-5球", "1-6球", "2-3球", "2-4球", "2-5球", "2-6球", "3" +
                "-4球", "3-5球", "3-6球", "4-5球", "4-6球", "5-6球"));

    }

    //风控页面 部分玩法需要特殊处理展示
    static {

        playPropertyMap.put(120104, Arrays.asList("正码一", "正码二", "正码三", "正码四", "正码五", "正码六"));
        playPropertyMap.put(120204, Arrays.asList("正码一", "正码二", "正码三", "正码四", "正码五", "正码六"));
        playPropertyMap.put(120304, Arrays.asList("正码一", "正码二", "正码三", "正码四", "正码五", "正码六"));
        playPropertyMap.put(120404, Arrays.asList("正码一", "正码二", "正码三", "正码四", "正码五", "正码六"));
        playPropertyMap.put(120504, Arrays.asList("正码一", "正码二", "正码三", "正码四", "正码五", "正码六"));
        /**
         * ssc
         */
        playPropertyMap.put(110101, Arrays.asList("总和、龙虎", "第一球", "第二球", "第三球", "第四球", "第五球"));


        playPropertyMap.put(120143, Arrays.asList("龙", "虎"));

        playPropertyMap.put(110103, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110104, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(110203, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110204, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(110303, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110304, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(110403, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110404, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(110503, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110504, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(110603, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(110604, Arrays.asList("前三", "中三", "后三"));
        /******************PK10 START*************************/
        playPropertyMap.put(130103, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(130104, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(130203, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(130304, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(130303, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(130304, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(130403, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(130404, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));
        /******************PK10 END*************************/

        playPropertyMap.put(130101, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        /******************分分彩 START*************************/
        playPropertyMap.put(160103, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(160104, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(160101, Arrays.asList("总和、龙虎", "第一球", "第二球", "第三球", "第四球", "第五球"));

        /******************分分彩 START*************************/
        /******************幸运飞艇 START*************************/
        playPropertyMap.put(140101, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(140201, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(140103, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(140203, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(140104, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(140204, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));

        /******************幸运飞艇 END*************************/
        /**
         * 海南七星彩
         */
        playPropertyMap.put(170305, Arrays.asList("千位", "百位", "十位", "个位"));
        playPropertyMap.put(170304, Arrays.asList("千位", "百位", "十位", "个位", "和值"));
        /**
         * 排列3/5
         */
        playPropertyMap.put(170203, Arrays.asList("万位", "千位", "百位", "十位", "个位", "排列3和值", "排列5和值"));
        playPropertyMap.put(170204, Arrays.asList("万位", "千位", "百位", "十位", "个位"));
        /**
         * 福彩3D
         */
        playPropertyMap.put(180210, Arrays.asList("和数", "百位", "十位", "个位"));
        playPropertyMap.put(180206, Arrays.asList("百位", "十位", "个位"));
        /**
         * PC蛋蛋
         */
        /**
         * 幸运飞艇
         */
        playPropertyMap.put(140101, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(140201, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        /**
         * 澳洲
         */

        playPropertyMap.put(220201, Arrays.asList("总和、龙虎", "第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(220203, Arrays.asList("第一球", "第二球", "第三球", "第四球", "第五球"));
        playPropertyMap.put(220204, Arrays.asList("前三", "中三", "后三"));
        playPropertyMap.put(220301, Arrays.asList("冠亚和", "冠军", "亚军", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"));
        playPropertyMap.put(220303, Arrays.asList("冠军", "亚军", "第三名", "第四名", "第五名"));
        playPropertyMap.put(220304, Arrays.asList("第六名", "第七名", "第八名", "第九名", "第十名"));


    }

    public static Set<String> getShuXing(Integer playId, String propertyName) {
        Set<String> shuxings = null;
        String oneToFive[] = {"冠军", "亚军", "第三名", "第四名", "第五名", "第3名", "第4名", "第5名"};
        boolean oneToFiveTrue = false;// 说明是两面里的第一道第五名
        for (String sin : oneToFive) {
            if (propertyName.contains(sin)) {
                oneToFiveTrue = true;
            }
        }

        if ("170203".equals(playId.toString()) && ("排列3和值".equals(propertyName) || "排列5和值".equals(propertyName))) {
            shuxings = Arrays.asList("大单", "大双", "小单", "小双").stream().collect(Collectors.toSet());
        } else if ("220201".equals(playId.toString())) { // 澳洲时时彩  两面
            if (propertyName.contains("总和")) {
                shuxings = SpecialPlayIdContants.specialPlayMap.get(String.valueOf("22020101")).stream().collect(Collectors.toSet());
            } else {
                shuxings = SpecialPlayIdContants.specialPlayMap.get(playId.toString()).stream().collect(Collectors.toSet());
            }
        } else {
            int pkxyPlayId[] = {130101, 130201, 130301, 130401, 140101, 140201};
            if (ArrayUtils.toList(pkxyPlayId).contains(playId) && "冠亚和".equals(propertyName)) { // pk10和幸运飞艇的 两面冠亚和
                shuxings = SpecialPlayIdContants.specialPlayMap.get(String.valueOf("13010101")).stream().collect(Collectors.toSet());
            } else if (ArrayUtils.toList(pkxyPlayId).contains(playId) && oneToFiveTrue) {
                shuxings = SpecialPlayIdContants.specialPlayMap.get(String.valueOf("13010102")).stream().collect(Collectors.toSet());
            } else {
                shuxings = SpecialPlayIdContants.specialPlayMap.get(playId.toString()).stream().collect(Collectors.toSet());
            }
        }
        return shuxings;
    }


}
