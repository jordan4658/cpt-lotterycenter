package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.util.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 此为大神计划所有的玩法中奖判断规则
 *
 * @author peter
 * @version 1.0.0
 * @date 2020/2/19 11:08
 */
public class GodPlanUtil {
    private static final Logger logger = LoggerFactory.getLogger(GodPlanUtil.class);
    /** 生肖排序 */
    public static final ArrayList<String> SHENGXIAO = Lists.newArrayList("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪");
    //红波
    public static final List<Integer> RED = Lists.newArrayList(1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46);
    //蓝波
    public static final List<Integer> BLUE = Lists.newArrayList(3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48);
    //绿波
    public static final List<Integer> GREEN = Lists.newArrayList(5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49);


    /**
     * 判断六合彩平特是否中奖
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    public static Boolean isWinByPT(String betNumber, String sgNumber, String dateStr) {
        String[] betNumArr = betNumber.split(",");
        String[] sgArr = sgNumber.split(",");
        StringBuffer winStr = new StringBuffer();
        // 平特
        List<String> sgList = LhcUtils.getNumberShengXiao(sgNumber, dateStr);
        String sgs = "";
        for (String sg : sgList) {
            sgs = sgs + sg;
        }
        for (String betNum : betNumArr) {
            if (sgs.contains(betNum)) {
                winStr.append(betNum).append(",");
            }
        }

        StringBuffer winStr1 = new StringBuffer();
        for (String betNum : betNumArr) {
            if (sgList.contains(betNum)) {
                winStr1.append(betNum).append(",");
            }
        }
        if (!winStr.toString().equals(winStr1.toString())) {
            logger.info("两边判断信息不准{}，{}", winStr, winStr1);
        }
        logger.info("平特信息：{},{},{},{},{},{},{},{},{}", sgNumber, sgs, winStr, betNumber, sgNumber, dateStr);
        String nums = "";
        for (String num : betNumArr) {
            nums = nums + num;
        }
        return CommonUtils.isNotNull(winStr.toString());
    }

    /**
     * 判断六合彩波色是否中奖
     *
     * @param number
     * @param betNumArr
     * @return
     */
    public static Boolean isWinByBS(String[] betNumArr, String number) {
        StringBuffer winStr = new StringBuffer();
        // 半波红波,蓝波,绿波
        Integer boNumber = Integer.valueOf(number.split(",")[6]);
        String strBo = "";
        if (RED.contains(boNumber)) {
            strBo = "红波";
        } else if (BLUE.contains(boNumber)) {
            strBo = "蓝波";
        } else if (GREEN.contains(boNumber)) {
            strBo = "绿波";
        }
        if (CommonUtils.isNotNull(strBo)) {
            for (String betNum : betNumArr) {
                if (strBo.contains(betNum)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 根据公历日期获取号码对应的生肖
     *
     * @param num     号码
     * @param dateStr 日期yyyy-MM-dd
     * @return
     */
    public static String getShengXiao(int num, String dateStr) {
        //获取该日期的年份生肖
        String animalsYear = getShengXiao(dateStr);
        int index = SHENGXIAO.indexOf(animalsYear);
        if (index < 0) {
            return "";
        }
        int postion = (49 + index - num) % 12;
        return SHENGXIAO.get(postion);
    }

    /**
     * 根据公历日期获取对应的生肖
     *
     * @param dateStr 日期yyyy-MM-dd
     * @return
     */
    public static String getShengXiao(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return Constants.DEFAULT_NULL;
        }
        try {
            Integer year = CalendarUtil.solarToLunar(dateStr.replace("-", ""));
            String animalsYear = SHENGXIAO.get((year - 4) % 12);
            return animalsYear;
        } catch (Exception e) {
            logger.error("获取生肖出错:{}", e);
            for (int i = 0; i < 3; i++) {
                try {
                    Integer year = CalendarUtil.solarToLunar(dateStr.replace("-", ""));
                    String animalsYear = SHENGXIAO.get((year - 4) % 12);
                    return animalsYear;
                } catch (Exception e1) {
                    logger.error("再次获取生肖出错:{},{}", i, e);
                }
            }
            return Constants.DEFAULT_NULL;
        }
    }


//    /**
//     * 判断分分彩是否中奖
//     *
//     * @param strBetNumber
//     * @param number
//     * @param playShowName
//     * @param playName
//     * @return
//     */
//    public static Boolean isWin(Integer lotteryId, String strBetNumber, String number, String playShowName, String playName, String date) {
//        // 支持所有彩种
//        Boolean result = false;
//        if (playName.contains("两面") || LMPLAYLIST.stream().anyMatch(lmplay -> strBetNumber.contains(lmplay))) {
//            /* 匹配任一投注值是否符合两面玩法值中的一个 */
//            String[] betNumbers = strBetNumber.split(",");
//            for (String betNumber : betNumbers) {
//                result = isWinByLm(betNumber, number, playShowName, playName);
//                if (result == true) {
//                    break;
//                }
//            }
//            return result;
//        } else if (playName.contains("五行")) {
//            String[] betNumbers = strBetNumber.split(",");
//            for (String betNumber : betNumbers) {
//                result = isWinByWx(betNumber, number);
//                if (result == true) {
//                    break;
//                }
//            }
//            return result;
//        } else if (playName.contains("平特")) {
//            return isWinByPT(strBetNumber, number, date);
//        } else if (playName.contains("独胆")) {
//            return ksDudanPlayOpen(strBetNumber, number.split(","));
//        } else if (strBetNumber.contains(",") || playShowName.contains("定位")) {
//            // 定位胆(前面过滤完之后剩下的才是定位胆)
//            String[] betNumbers = strBetNumber.split(",");
//            for (String betNumber : betNumbers) {
//                result = isWinByLocation(betNumber, number, playShowName, playName);
//                if (result == true) {
//                    break;
//                }
//            }
//            return result;
//        } else {
//            logger.error("大神计划遗漏玩法规则：{}，显示名称{}", playName, playShowName);
//            return false;
//        }
//    }


    public static String substringIssue(String issue, Integer lotteryId) {
        if (Arrays.asList(1101, 1104, 1105, 1202, 1205, 1401, 2201, 2202, 2203, 2301).contains(lotteryId)) {
            if (issue.length() == 11) {
                /* 适用于2201，1401，*/
                return StringUtils.substring(issue, 8);
            }
        } else if (Arrays.asList(1106, 1402, 1502, 2302).contains(lotteryId)) {
            if (issue.length() == 12) {
                /* 适用于1502 */
                return StringUtils.substring(issue, 8);
            }
        } else if (Arrays.asList(1601).contains(lotteryId)) {
            if (issue.length() == 13) {
                /* 适用于1601， */
                return StringUtils.substring(issue, 9);
            }
        } else if (Arrays.asList(1102, 1103).contains(lotteryId)) {
            if (issue.length() == 10) {
                /* 适用于1601， */
                return StringUtils.substring(issue, 8);
            }
        } else {
            if (issue.length() > 4) {
                return StringUtils.substring(issue, issue.length() - 4);
            }
        }
        return issue;
    }

//    public static Boolean ksDudanPlayOpen(String betNumber, String[] numbers) {
//        Boolean result = false;
//        for (String number : numbers) {
//            if (betNumber.equals(number)) {
//                result = true;
//            }
//        }
//        return result;
//    }


    public static Boolean isWin(Integer lotteryId, String strBetNumber, String number, String playShowName, String playName, String dateStr) {
        boolean isWin = false;
        String numnberArray[] = number.split(",");
        String betNumberArray[] = strBetNumber.split(",");
        //时时彩系列
        int sscXl[] = {Constants.LOTTERY_CQSSC, Constants.LOTTERY_XJSSC, Constants.LOTTERY_TJSSC, Constants.LOTTERY_TENSSC, Constants.LOTTERY_FIVESSC, Constants.LOTTERY_DZSSC, Constants.LOTTERY_TXFFC
                , Constants.LOTTERY_AUSSSC};
        //六合彩彩系列
        int lhcXl[] = {Constants.LOTTERY_ONELHC, Constants.LOTTERY_FIVELHC, Constants.LOTTERY_XJPLHC, Constants.LOTTERY_AMLHC};
        //pks彩系列+幸运飞艇系列+澳洲f1
        int pksXl[] = {Constants.LOTTERY_BJPKS, Constants.LOTTERY_TENPKS, Constants.LOTTERY_FIVEPKS, Constants.LOTTERY_DZPKS, Constants.LOTTERY_XYFT, Constants.LOTTERY_DZXYFT, Constants.LOTTERY_AUSPKS};

        if (ArrayUtils.toList(sscXl).contains(lotteryId)) {
            int sum = 0;
            for (String thisNumber : numnberArray) {
                sum += Integer.valueOf(thisNumber);
            }
            if ("总和大小".equals(playShowName)) {
                if (sum >= 23 && strBetNumber.contains("大")) {
                    return true;
                } else if (sum <= 22 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("总和单双".equals(playShowName)) {
                if (sum % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (sum % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("总和龙虎".equals(playShowName)) {
                if (Integer.valueOf(numnberArray[0]) > Integer.valueOf(numnberArray[4]) && strBetNumber.contains("龙")) {
                    return true;
                } else if (Integer.valueOf(numnberArray[0]) < Integer.valueOf(numnberArray[4]) && strBetNumber.contains("虎")) {
                    return true;
                }
            } else {  //第一球到第五球
                int count = 0;
                int selectNumber = 0;
                if (playShowName.contains("一")) {
                    count = 0;
                } else if (playShowName.contains("二")) {
                    count = 1;
                } else if (playShowName.contains("三")) {
                    count = 2;
                } else if (playShowName.contains("四")) {
                    count = 3;
                } else if (playShowName.contains("五")) {
                    count = 4;
                }
                selectNumber = Integer.valueOf(numnberArray[count]);
                if (playShowName.contains("定位")) {
                    for (String betNumber : betNumberArray) {
                        if (selectNumber == Integer.valueOf(betNumber)) {
                            return true;
                        }
                    }
                } else if (playShowName.contains("大小")) {
                    if (selectNumber >= 5 && strBetNumber.contains("大")) {
                        return true;
                    } else if (selectNumber <= 4 && strBetNumber.contains("小")) {
                        return true;
                    }
                } else if (playShowName.contains("单双")) {
                    if (selectNumber % 2 == 0 && strBetNumber.contains("双")) {
                        return true;
                    } else if (selectNumber % 2 != 0 && strBetNumber.contains("单")) {
                        return true;
                    }
                }
            }
        } else if (ArrayUtils.toList(lhcXl).contains(lotteryId)) {
            /** 家禽生肖 */
            ArrayList<String> JIAQIN = Lists.newArrayList("牛", "马", "羊", "鸡", "狗", "猪");
            ArrayList<String> YESHOU = Lists.newArrayList("鼠", "虎", "兔", "龙", "蛇", "猴");
            if ("平特".equals(playShowName)) {
                return isWinByPT(strBetNumber, number, dateStr);
            } else if ("大小".equals(playShowName)) {
                if (Integer.valueOf(numnberArray[6]) == 49) {
                    return true;
                }
                if (Integer.valueOf(numnberArray[6]) >= 25 && strBetNumber.contains("大")) {
                    return true;
                } else if (Integer.valueOf(numnberArray[6]) <= 24 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("单双".equals(playShowName)) {
                if (Integer.valueOf(numnberArray[6]) == 49) {
                    return true;
                }
                if (Integer.valueOf(numnberArray[6]) % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (Integer.valueOf(numnberArray[6]) % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("合单双".equals(playShowName)) {
                if (Integer.valueOf(numnberArray[6]) == 49) {
                    return true;
                }
                Integer heds = Integer.valueOf(numnberArray[6].substring(0, 1)) + Integer.valueOf(numnberArray[6].substring(1, 2));
                if (Integer.valueOf(heds) % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (Integer.valueOf(heds) % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("家禽野兽".equals(playShowName)) {
                String shengXiao = getShengXiao(Integer.valueOf(numnberArray[6]), DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD));
                if (JIAQIN.contains(shengXiao) && strBetNumber.contains("家禽")) {
                    return true;
                } else if (YESHOU.contains(shengXiao) && strBetNumber.contains("野兽")) {
                    return true;
                }
            } else if ("尾大尾小".equals(playShowName)) {
                if (Integer.valueOf(numnberArray[6]) == 49) {
                    return true;
                }
                String weiBigSmal = Integer.valueOf(numnberArray[6]) % 10 > 4 ? "尾大" : "尾小";
                if ("尾大".equals(weiBigSmal) && strBetNumber.contains("尾大")) {
                    return true;
                } else if ("尾小".equals(weiBigSmal) && strBetNumber.contains("尾小")) {
                    return true;
                }
            } else if ("波色".equals(playShowName)) {
                return isWinByBS(strBetNumber.split(","), number);
            }
        } else if (ArrayUtils.toList(pksXl).contains(lotteryId)) {
            int guanyahe = Integer.valueOf(numnberArray[0]) + Integer.valueOf(numnberArray[1]);
            if ("冠亚和大小".equals(playShowName)) {
                if (guanyahe > 11 && strBetNumber.contains("大")) {
                    return true;
                } else if (guanyahe <= 11 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("冠亚和单双".equals(playShowName)) {
                if (guanyahe % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (guanyahe % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else {  //第一球到第五球
                int count = 0;
                int selectNumber = 0;
                if (playShowName.contains("冠军")) {
                    count = 0;
                } else if (playShowName.contains("亚军")) {
                    count = 1;
                } else if (playShowName.contains("季军")) {
                    count = 2;
                } else if (playShowName.contains("第四名")) {
                    count = 3;
                } else if (playShowName.contains("第五名")) {
                    count = 4;
                } else if (playShowName.contains("第六名")) {
                    count = 5;
                } else if (playShowName.contains("第七名")) {
                    count = 6;
                } else if (playShowName.contains("第八名")) {
                    count = 7;
                } else if (playShowName.contains("第九名")) {
                    count = 8;
                } else if (playShowName.contains("第十名")) {
                    count = 9;
                }
                selectNumber = Integer.valueOf(numnberArray[count]);
                if (playShowName.contains("定位")) {
                    for (String betNumber : betNumberArray) {
                        if (selectNumber == Integer.valueOf(betNumber)) {
                            return true;
                        }
                    }
                } else if (playShowName.contains("大小")) {
                    if (selectNumber >= 6 && strBetNumber.contains("大")) {
                        return true;
                    } else if (selectNumber <= 5 && strBetNumber.contains("小")) {
                        return true;
                    }
                } else if (playShowName.contains("单双")) {
                    if (selectNumber % 2 == 0 && strBetNumber.contains("双")) {
                        return true;
                    } else if (selectNumber % 2 != 0 && strBetNumber.contains("单")) {
                        return true;
                    }
                } else if (playShowName.contains("龙虎")) {
                    if (count == 0) {
                        if (Integer.valueOf(numnberArray[0]) > Integer.valueOf(numnberArray[9]) && strBetNumber.contains("龙")) {
                            return true;
                        } else if (Integer.valueOf(numnberArray[0]) < Integer.valueOf(numnberArray[9]) && strBetNumber.contains("虎")) {
                            return true;
                        }
                    } else if (count == 1) {
                        if (Integer.valueOf(numnberArray[1]) > Integer.valueOf(numnberArray[8]) && strBetNumber.contains("龙")) {
                            return true;
                        } else if (Integer.valueOf(numnberArray[1]) < Integer.valueOf(numnberArray[8]) && strBetNumber.contains("虎")) {
                            return true;
                        }
                    } else if (count == 2) {
                        if (Integer.valueOf(numnberArray[2]) > Integer.valueOf(numnberArray[7]) && strBetNumber.contains("龙")) {
                            return true;
                        } else if (Integer.valueOf(numnberArray[2]) < Integer.valueOf(numnberArray[7]) && strBetNumber.contains("虎")) {
                            return true;
                        }
                    } else if (count == 3) {
                        if (Integer.valueOf(numnberArray[3]) > Integer.valueOf(numnberArray[6]) && strBetNumber.contains("龙")) {
                            return true;
                        } else if (Integer.valueOf(numnberArray[3]) < Integer.valueOf(numnberArray[6]) && strBetNumber.contains("虎")) {
                            return true;
                        }
                    } else if (count == 4) {
                        if (Integer.valueOf(numnberArray[4]) > Integer.valueOf(numnberArray[5]) && strBetNumber.contains("龙")) {
                            return true;
                        } else if (Integer.valueOf(numnberArray[4]) < Integer.valueOf(numnberArray[5]) && strBetNumber.contains("虎")) {
                            return true;
                        }
                    }
                    if (selectNumber % 2 == 0 && strBetNumber.contains("双")) {
                        return true;
                    } else if (selectNumber % 2 != 0 && strBetNumber.contains("单")) {
                        return true;
                    }
                }
            }
        } else if (lotteryId == Constants.LOTTERY_PCEGG || lotteryId == Constants.LOTTERY_DZPCEGG) {
            int sum = Integer.valueOf(numnberArray[0]) + Integer.valueOf(numnberArray[1]) + +Integer.valueOf(numnberArray[2]);
            int hongArray[] = {3, 6, 9, 12, 15, 18, 21, 24};
            int lanArray[] = {2, 5, 8, 11, 17, 20, 23, 26};
            int lvArray[] = {1, 4, 7, 10, 16, 19, 22, 25};
            List<Integer> hongList = ArrayUtils.toList(hongArray);
            List<Integer> lanList = ArrayUtils.toList(lanArray);
            List<Integer> lvList = ArrayUtils.toList(lvArray);
            if ("混合大小".equals(playShowName)) {
                if (sum >= 14 && strBetNumber.contains("大")) {
                    return true;
                } else if (sum <= 13 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("混合单双".equals(playShowName)) {
                if (sum % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (sum % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("色波".equals(playShowName)) {  //
                if (hongList.contains(sum) && strBetNumber.contains("红波")) {
                    return true;
                } else if (lanList.contains(sum) && strBetNumber.contains("蓝波")) {
                    return true;
                } else if (lvList.contains(sum) && strBetNumber.contains("绿波")) {
                    return true;
                }
            }
        } else if (lotteryId == Constants.LOTTERY_AUSACT) {
            int sum = 0;
            for (String single : numnberArray) {
                sum += Integer.valueOf(single);
            }
            if ("和大小".equals(playShowName)) {
                if (sum > 810 && strBetNumber.contains("大")) {
                    return true;
                } else if (sum < 810 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("和单双".equals(playShowName)) {
                if (sum % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (sum % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("五行".equals(playShowName)) {  //
                if (sum >= 210 && sum <= 695 && strBetNumber.contains("金")) {
                    return true;
                } else if (sum >= 696 && sum <= 763 && strBetNumber.contains("木")) {
                    return true;
                } else if (sum >= 764 && sum <= 855 && strBetNumber.contains("水")) {
                    return true;
                } else if (sum >= 856 && sum <= 923 && strBetNumber.contains("火")) {
                    return true;
                } else if (sum >= 924 && sum <= 1410 && strBetNumber.contains("土")) {
                    return true;
                }
            }
        } else if (lotteryId == Constants.LOTTERY_AZKS || lotteryId == Constants.LOTTERY_DZKS) {
            int sum = 0;

            for (String single : numnberArray) {
                sum += Integer.valueOf(single);
            }
            if ("和值大小".equals(playShowName)) {
                if (sum > 11 && sum < 17 && strBetNumber.contains("大")) {
                    return true;
                } else if (sum > 4 && sum < 10 && strBetNumber.contains("小")) {
                    return true;
                }
            } else if ("和值单双".equals(playShowName)) {
                if (sum % 2 == 0 && strBetNumber.contains("双")) {
                    return true;
                } else if (sum % 2 != 0 && strBetNumber.contains("单")) {
                    return true;
                }
            } else if ("独胆".equals(playShowName)) {  //
                for (String single : numnberArray) {
                    if (strBetNumber.equals(single)) {
                        return true;
                    }
                }
            }
        }
        return isWin;
    }


//    public static void main(String[] args) {
//
//        System.out.println(isWin(2301, "大", "1,2,3", "和值大小", "和值大小", "2020-03-16"));
//        System.out.println(isWin(2301, "小", "1,2,3", "和值大小", "和值大小", "2020-03-16"));
//        System.out.println(isWin(2301, "单", "1,2,3", "和值单双", "和值单双", "2020-03-16"));
//        System.out.println(isWin(2301, "双", "1,2,3", "和值单双", "和值单双", "2020-03-16"));
//        System.out.println(isWin(2301, "1", "1,2,3", "独胆", "独胆", "2020-03-16"));
//        System.out.println(isWin(2301, "2", "1,2,3", "独胆", "独胆", "2020-03-16"));
//        System.out.println(isWin(2301, "3", "1,2,3", "独胆", "独胆", "2020-03-16"));
//        System.out.println(isWin(2301, "4", "1,2,3", "独胆", "独胆", "2020-03-16"));
//        System.out.println(isWin(2301, "5", "1,2,3", "独胆", "独胆", "2020-03-16"));
//        System.out.println(isWin(2301, "6", "1,2,3", "独胆", "独胆", "2020-03-16"));
//
//        System.out.println(isWin(2201, "单", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "和单双", "和单双", "2020-03-16"));
//        System.out.println(isWin(2201, "双", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "和单双", "和单双", "2020-03-16"));
//        System.out.println(isWin(2201, "大", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "和大小", "和大小", "2020-03-16"));
//        System.out.println(isWin(2201, "小", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "和大小", "和大小", "2020-03-16"));
//        System.out.println(isWin(2201, "金", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "五行", "五行", "2020-03-16"));
//        System.out.println(isWin(2201, "木", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "五行", "五行", "2020-03-16"));
//        System.out.println(isWin(2201, "水", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "五行", "五行", "2020-03-16"));
//        System.out.println(isWin(2201, "火", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "五行", "五行", "2020-03-16"));
//        System.out.println(isWin(2201, "土", "18,72,36,78,11,13,63,79,45,2,27,53,60,26,57,76,3,67,28,43", "五行", "五行", "2020-03-16"));
//
//
//        System.out.println(isWin(1501, "大", "1,3,5", "混合大小", "混合大小", "2020-03-16"));
//        System.out.println(isWin(1501, "小", "1,3,5", "混合大小", "混合大小", "2020-03-16"));
//        System.out.println(isWin(1501, "单", "1,3,5", "混合单双", "混合单双", "2020-03-16"));
//        System.out.println(isWin(1501, "双", "1,3,5", "混合单双", "混合单双", "2020-03-16"));
//        System.out.println(isWin(1501, "绿波", "1,0,0", "色波", "色波", "2020-03-16"));
//        System.out.println(isWin(1501, "绿波", "2,0,0", "色波", "色波", "2020-03-16"));
//        System.out.println(isWin(1501, "蓝波", "1,1,0", "色波", "色波", "2020-03-16"));
//        System.out.println(isWin(1501, "蓝波", "1,1,1", "色波", "色波", "2020-03-16"));
//        System.out.println(isWin(1501, "红波", "2,2,2", "色波", "色波", "2020-03-16"));
//        System.out.println(isWin(1501, "红波", "2,2,3", "色波", "色波", "2020-03-16"));
//
//        System.out.println("第1球");
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "冠亚和大小", "冠亚和大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "冠亚和大小", "冠亚和大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "冠亚和单双", "冠亚和单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "冠亚和单双", "冠亚和单双", "2020-03-16"));
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "冠军定位", "冠军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "冠军定位", "冠军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "冠军大小", "冠军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "冠军大小", "冠军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "冠军单双", "冠军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "冠军单双", "冠军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "龙", "1,3,5,7,9,10,2,6,4,8", "冠军龙虎", "冠军龙虎", "2020-03-16"));
//        System.out.println(isWin(1401, "虎", "1,3,5,7,9,10,2,6,4,8", "冠军龙虎", "冠军龙虎", "2020-03-16"));
//        System.out.println("第2球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "亚军定位", "亚军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "亚军定位", "亚军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "亚军大小", "亚军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "亚军大小", "亚军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "亚军单双", "亚军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "亚军单双", "亚军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "龙", "1,3,5,7,9,10,2,6,4,8", "亚军龙虎", "亚军龙虎", "2020-03-16"));
//        System.out.println(isWin(1401, "虎", "1,3,5,7,9,10,2,6,4,8", "亚军龙虎", "亚军龙虎", "2020-03-16"));
//        System.out.println("第3球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "季军定位", "季军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "季军定位", "季军定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "季军大小", "季军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "季军大小", "季军大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "季军单双", "季军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "季军单双", "季军单双", "2020-03-16"));
//        System.out.println(isWin(1401, "龙", "1,3,5,7,9,10,2,6,4,8", "季军龙虎", "季军龙虎", "2020-03-16"));
//        System.out.println(isWin(1401, "虎", "1,3,5,7,9,10,2,6,4,8", "季军龙虎", "季军龙虎", "2020-03-16"));
//        System.out.println("第4球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第四名定位", "第四名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第四名定位", "第四名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第四名大小", "第四名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第四名大小", "第四名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第四名单双", "第四名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第四名单双", "第四名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "龙", "1,3,5,7,9,10,2,6,4,8", "第四名龙虎", "第四名龙虎", "2020-03-16"));
//        System.out.println(isWin(1401, "虎", "1,3,5,7,9,10,2,6,4,8", "第四名龙虎", "第四名龙虎", "2020-03-16"));
//        System.out.println("第5球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第五名定位", "第五名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第五名定位", "第五名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第五名大小", "第五名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第五名大小", "第五名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第五名单双", "第五名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第五名单双", "第五名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "龙", "1,3,5,7,9,10,2,6,4,8", "第五名龙虎", "第五名龙虎", "2020-03-16"));
//        System.out.println(isWin(1401, "虎", "1,3,5,7,9,10,2,6,4,8", "第五名龙虎", "第五名龙虎", "2020-03-16"));
//        System.out.println("第6球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第六名定位", "第六名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第六名定位", "第六名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第六名大小", "第六名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第六名大小", "第六名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第六名单双", "第六名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第六名单双", "第六名单双", "2020-03-16"));
//        System.out.println("第7球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第七名定位", "第七名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第七名定位", "第七名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第七名大小", "第七名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第七名大小", "第七名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第七名单双", "第七名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第七名单双", "第七名单双", "2020-03-16"));
//        System.out.println("第8球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第八名定位", "第八名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第八名定位", "第八名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第八名大小", "第八名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第八名大小", "第八名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第八名单双", "第八名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第八名单双", "第八名单双", "2020-03-16"));
//        System.out.println("第9球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第九名定位", "第九名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第九名定位", "第九名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第九名大小", "第九名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第九名大小", "第九名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第九名单双", "第九名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第九名单双", "第九名单双", "2020-03-16"));
//        System.out.println("第10球");
//        System.out.println(isWin(1401, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第十名定位", "第十名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第十名定位", "第十名定位", "2020-03-16"));
//        System.out.println(isWin(1401, "大", "1,3,5,7,9,10,2,6,4,8", "第十名大小", "第十名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "小", "1,3,5,7,9,10,2,6,4,8", "第十名大小", "第十名大小", "2020-03-16"));
//        System.out.println(isWin(1401, "单", "1,3,5,7,9,10,2,6,4,8", "第十名单双", "第十名单双", "2020-03-16"));
//        System.out.println(isWin(1401, "双", "1,3,5,7,9,10,2,6,4,8", "第十名单双", "第十名单双", "2020-03-16"));
//
//
//        System.out.println("第1球");
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "冠亚和大小", "冠亚和大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "冠亚和大小", "冠亚和大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "冠亚和单双", "冠亚和单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "冠亚和单双", "冠亚和单双", "2020-03-16"));
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "冠军定位", "冠军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "冠军定位", "冠军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "冠军大小", "冠军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "冠军大小", "冠军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "冠军单双", "冠军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "冠军单双", "冠军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "龙", "1,3,5,7,9,10,2,6,4,8", "冠军龙虎", "冠军龙虎", "2020-03-16"));
//        System.out.println(isWin(1301, "虎", "1,3,5,7,9,10,2,6,4,8", "冠军龙虎", "冠军龙虎", "2020-03-16"));
//        System.out.println("第2球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "亚军定位", "亚军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "亚军定位", "亚军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "亚军大小", "亚军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "亚军大小", "亚军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "亚军单双", "亚军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "亚军单双", "亚军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "龙", "1,3,5,7,9,10,2,6,4,8", "亚军龙虎", "亚军龙虎", "2020-03-16"));
//        System.out.println(isWin(1301, "虎", "1,3,5,7,9,10,2,6,4,8", "亚军龙虎", "亚军龙虎", "2020-03-16"));
//        System.out.println("第3球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "季军定位", "季军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "季军定位", "季军定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "季军大小", "季军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "季军大小", "季军大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "季军单双", "季军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "季军单双", "季军单双", "2020-03-16"));
//        System.out.println(isWin(1301, "龙", "1,3,5,7,9,10,2,6,4,8", "季军龙虎", "季军龙虎", "2020-03-16"));
//        System.out.println(isWin(1301, "虎", "1,3,5,7,9,10,2,6,4,8", "季军龙虎", "季军龙虎", "2020-03-16"));
//        System.out.println("第4球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第四名定位", "第四名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第四名定位", "第四名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第四名大小", "第四名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第四名大小", "第四名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第四名单双", "第四名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第四名单双", "第四名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "龙", "1,3,5,7,9,10,2,6,4,8", "第四名龙虎", "第四名龙虎", "2020-03-16"));
//        System.out.println(isWin(1301, "虎", "1,3,5,7,9,10,2,6,4,8", "第四名龙虎", "第四名龙虎", "2020-03-16"));
//        System.out.println("第5球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第五名定位", "第五名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第五名定位", "第五名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第五名大小", "第五名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第五名大小", "第五名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第五名单双", "第五名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第五名单双", "第五名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "龙", "1,3,5,7,9,10,2,6,4,8", "第五名龙虎", "第五名龙虎", "2020-03-16"));
//        System.out.println(isWin(1301, "虎", "1,3,5,7,9,10,2,6,4,8", "第五名龙虎", "第五名龙虎", "2020-03-16"));
//        System.out.println("第6球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第六名定位", "第六名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第六名定位", "第六名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第六名大小", "第六名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第六名大小", "第六名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第六名单双", "第六名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第六名单双", "第六名单双", "2020-03-16"));
//        System.out.println("第7球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第七名定位", "第七名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第七名定位", "第七名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第七名大小", "第七名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第七名大小", "第七名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第七名单双", "第七名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第七名单双", "第七名单双", "2020-03-16"));
//        System.out.println("第8球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第八名定位", "第八名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第八名定位", "第八名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第八名大小", "第八名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第八名大小", "第八名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第八名单双", "第八名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第八名单双", "第八名单双", "2020-03-16"));
//        System.out.println("第9球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第九名定位", "第九名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第九名定位", "第九名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第九名大小", "第九名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第九名大小", "第九名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第九名单双", "第九名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第九名单双", "第九名单双", "2020-03-16"));
//        System.out.println("第10球");
//        System.out.println(isWin(1301, "1,2,3,4,5", "1,3,5,7,9,10,2,6,4,8", "第十名定位", "第十名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "6,7,8,9,10", "1,3,5,7,9,10,2,6,4,8", "第十名定位", "第十名定位", "2020-03-16"));
//        System.out.println(isWin(1301, "大", "1,3,5,7,9,10,2,6,4,8", "第十名大小", "第十名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "小", "1,3,5,7,9,10,2,6,4,8", "第十名大小", "第十名大小", "2020-03-16"));
//        System.out.println(isWin(1301, "单", "1,3,5,7,9,10,2,6,4,8", "第十名单双", "第十名单双", "2020-03-16"));
//        System.out.println(isWin(1301, "双", "1,3,5,7,9,10,2,6,4,8", "第十名单双", "第十名单双", "2020-03-16"));
//
//
//        System.out.println(isWin(1202, "鼠", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "马", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "羊", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "猴", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "鸡", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "猪", "1,3,5,7,9,10,11", "平特", "平特", "2020-03-16"));
//        System.out.println(isWin(1202, "大", "1,3,5,7,9,10,11", "大小", "大小", "2020-03-16"));
//        System.out.println(isWin(1202, "小", "1,3,5,7,9,10,11", "大小", "大小", "2020-03-16"));
//        System.out.println(isWin(1202, "单", "1,3,5,7,9,10,11", "单双", "单双", "2020-03-16"));
//        System.out.println(isWin(1202, "双", "1,3,5,7,9,10,11", "单双", "单双", "2020-03-16"));
//        System.out.println(isWin(1202, "合单", "1,3,5,7,9,10,11", "合单双", "合单双", "2020-03-16"));
//        System.out.println(isWin(1202, "合双", "1,3,5,7,9,10,11", "合单双", "合单双", "2020-03-16"));
//        System.out.println(isWin(1202, "家禽", "1,3,5,7,9,10,11", "家禽野兽", "家禽野兽", "2020-03-16"));
//        System.out.println(isWin(1202, "野兽", "1,3,5,7,9,10,11", "家禽野兽", "家禽野兽", "2020-03-16"));
//        System.out.println(isWin(1202, "尾大", "1,3,5,7,9,10,49", "尾大尾小", "尾大尾小", "2020-03-16"));
//        System.out.println(isWin(1202, "尾小", "1,3,5,7,9,10,49", "尾大尾小", "尾大尾小", "2020-03-16"));
//        System.out.println(isWin(1202, "红波", "1,3,5,7,9,10,11", "波色", "波色", "2020-03-16"));
//        System.out.println(isWin(1202, "绿波", "1,3,5,7,9,10,11", "波色", "波色", "2020-03-16"));
//        System.out.println(isWin(1202, "蓝波", "1,3,5,7,9,10,11", "波色", "波色", "2020-03-16"));
//
//
//        System.out.println(isWin(1106, "和大", "1,3,5,7,9", "总和大小", "总和大小", ""));
//        System.out.println(isWin(1106, "和小", "1,3,5,7,9", "总和大小", "总和大小", ""));
//        System.out.println(isWin(1106, "和单", "1,3,5,7,9", "总和单双", "总和单双", ""));
//        System.out.println(isWin(1106, "和双", "1,3,5,7,9", "总和单双", "总和单双", ""));
//        System.out.println(isWin(1106, "和龙", "1,3,5,7,9", "总和龙虎", "总龙虎", ""));
//        System.out.println(isWin(1106, "和虎", "1,3,5,7,9", "总和龙虎", "总龙虎", ""));
//        System.out.println("第1球");
//        System.out.println(isWin(1106, "0,1,2,3,4", "1,3,5,7,9", "第一球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "5,6,7,8,9", "1,3,5,7,9", "第一球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "大", "1,3,5,7,9", "第一球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "小", "1,3,5,7,9", "第一球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "单", "1,3,5,7,9", "第一球单双", "总和大小", ""));
//        System.out.println(isWin(1106, "双", "1,3,5,7,9", "第一球单双", "总和大小", ""));
//        System.out.println("第2球");
//        System.out.println(isWin(1106, "0,1,2,3,4", "1,3,5,7,9", "第二球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "5,6,7,8,9", "1,3,5,7,9", "第二球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "大", "1,3,5,7,9", "第二球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "小", "1,3,5,7,9", "第二球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "单", "1,3,5,7,9", "第二球单双", "总和大小", ""));
//        System.out.println(isWin(1106, "双", "1,3,5,7,9", "第二球单双", "总和大小", ""));
//        System.out.println("第3球");
//        System.out.println(isWin(1106, "0,1,2,3,4", "1,3,5,7,9", "第三球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "5,6,7,8,9", "1,3,5,7,9", "第三球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "大", "1,3,5,7,9", "第三球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "小", "1,3,5,7,9", "第三球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "单", "1,3,5,7,9", "第三球单双", "总和大小", ""));
//        System.out.println(isWin(1106, "双", "1,3,5,7,9", "第三球单双", "总和大小", ""));
//        System.out.println("第4球");
//        System.out.println(isWin(1106, "0,1,2,3,4", "1,3,5,7,9", "第四球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "5,6,7,8,9", "1,3,5,7,9", "第四球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "大", "1,3,5,7,9", "第四球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "小", "1,3,5,7,9", "第四球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "单", "1,3,5,7,9", "第四球单双", "总和大小", ""));
//        System.out.println(isWin(1106, "双", "1,3,5,7,9", "第四球单双", "总和大小", ""));
//        System.out.println("第5球");
//        System.out.println(isWin(1106, "0,1,2,3,4", "1,3,5,7,9", "第五球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "5,6,7,8,9", "1,3,5,7,9", "第五球定位", "总和大小", ""));
//        System.out.println(isWin(1106, "大", "1,3,5,7,9", "第五球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "小", "1,3,5,7,9", "第五球大小", "总和大小", ""));
//        System.out.println(isWin(1106, "单", "1,3,5,7,9", "第五球单双", "总和大小", ""));
//        System.out.println(isWin(1106, "双", "1,3,5,7,9", "第五球单双", "总和大小", ""));
//
//
//    }
}
