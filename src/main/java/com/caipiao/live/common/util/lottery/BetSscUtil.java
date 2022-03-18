package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryPlay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/6 11:08
 */
public class BetSscUtil {

    public static Integer isWinBySizeSumGroup(String betNumber, String number) {
        String[] num = number.split(",");
        Integer sum = Integer.valueOf(num[0]) + Integer.valueOf(num[1]) + Integer.valueOf(num[2]) + Integer.valueOf(num[3]) + Integer.valueOf(num[4]);
        String[] strs = betNumber.split(",");
        Integer count = 0;
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i].trim();
            if (StringUtils.isBlank(str)) {
                continue;
            }
            if (str.contains(getBigSmallSum(sum)) && str.contains(getSingleDouble(sum))) {
                count++;
            }
        }
        return count;
    }

    public static Integer isWinBySizeSum(String betNumber, String number) {
        String[] num = number.split(",");
        Integer sum = Integer.valueOf(num[0]) + Integer.valueOf(num[1]) + Integer.valueOf(num[2]) + Integer.valueOf(num[3]) + Integer.valueOf(num[4]);
        String[] strs = betNumber.split(",");
        Integer count = 0;
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i].trim();
            if (StringUtils.isBlank(str)) {
                continue;
            }
            if (str.contains(getBigSmallSum(sum))) {
                count++;
            }
            if (str.contains(getSingleDouble(sum))) {
                count++;
            }
        }
        return count;
    }

    public static String getBigSmallSum(Integer num) {
        return num >= 25 ? "大" : "小";
    }

    public static Integer isWinBySize(String betNumber, String number, LotteryPlay play) {
        String[] sgs = number.split(",");

        String section = play.getSection();
        String[] sections = section.split(",");
        int start = Integer.parseInt(sections[0]);
        int end = Integer.parseInt(sections[1]);

        List<String> betNumberList = new ArrayList<>(Arrays.asList(sgs).subList(start - 1, end));

        String[] strs = betNumber.split(",");

        Integer count = 1;

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            int n = 0;
            String str = strs[i].trim();
            if (StringUtils.isBlank(str)) {
                continue;
            }
            String num = betNumberList.get(i);
            if (str.contains(getBigSmall(Integer.valueOf(num)))) {
                n++;
            }
            if (str.contains(getSingleDouble(Integer.valueOf(num)))) {
                n++;
            }
            list.add(n);
        }

        for (Integer num : list) {
            count *= num;
        }
        return count;
    }

    public static String getBigSmall(Integer num) {
        return num >= 5 ? "大" : "小";
    }

    public static String getSingleDouble(Integer num) {
        return num % 2 == 0 ? "双" : "单";
    }

    public static Integer isWinByLocation(String betNumber, String number) {
        betNumber = betNumber.replace(",", " , ");
        String[] strs = betNumber.split(",");
        String[] num = number.split(",");
        Integer count = 0;
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i].trim();
            switch (i) {
                case 0:
                    if (str.contains(num[0])) {
                        count++;
                    }
                    break;

                case 1:
                    if (str.contains(num[1])) {
                        count++;
                    }
                    break;

                case 2:
                    if (str.contains(num[2])) {
                        count++;
                    }
                    break;

                case 3:
                    if (str.contains(num[3])) {
                        count++;
                    }
                    break;

                case 4:
                    if (str.contains(num[4])) {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    public static Boolean isWinByGroup3(String betNumber, String number, LotteryPlay play) {
        Map<String, String> strMap = countDouble(number, play);
        if (CollectionUtils.isEmpty(strMap)) {
            return false;
        }
        String two = strMap.get("2") == null ? "-1" : strMap.get("2");
        String one = strMap.get("1") == null ? "-1" : strMap.get("1");
        return betNumber.contains(two) && betNumber.contains(one);
    }

    public static Boolean isWinByGroup2(String betNumber, String number, LotteryPlay play) {
        String[] sgs = number.split(",");

        String section = play.getSection();
        String[] sections = section.split(",");
        int start = Integer.parseInt(sections[0]);
        int end = Integer.parseInt(sections[1]);

        List<String> betNumberList = new ArrayList<>(Arrays.asList(sgs).subList(start - 1, end));

        String[] split = betNumber.split(",");

        boolean bool = false;

        for (String sp : split) {
            Integer count = 0;
            for (String num : betNumberList) {
                if (sp.contains(num)) {
                    count++;
                }
            }
            if (count.equals(betNumberList.size())) {
                bool = true;
                break;
            }
        }

        return bool;
    }

    /**
     * 判断是否中奖【五星组选120/60/30/20/10/5】、【四星组选24/12/6/4】、【三星组选6】、【二星组选复式】
     *
     * @param betNumber 投注号码
     * @param number    开奖号码
     * @return
     */
    public static Boolean isWinByGroup(String betNumber, String number, LotteryPlay play) {
        Map<String, String> strMap = countDouble(number, play);
        if (CollectionUtils.isEmpty(strMap)) {
            return false;
        }
        boolean bool = true;
        // 根据重号个数拆分投注号码
        String[] bets = betNumber.split(",");
        // 遍历重号投注结果
        for (String bet : bets) {
            // 拆分具体投注信息
            String index;
            String betNum;

            String[] str = bet.split(":");
            if (bet.contains(":")) {
                index = str[0];
                betNum = str[1];
            } else { // 用于解决三星组选6出现的问题
                index = "1";
                betNum = str[0];
            }

            // 根据重号数获取开奖中重号号码
            String s = strMap.get(index);
            if (StringUtils.isEmpty(s)) {
                bool = false;
                break;
            }
            s = s.trim();
            // 开奖重号号码
            char[] results = s.toCharArray();
            // 获取玩法id
            Integer playId = play.getId();
            // 【五星组选120】（单号5个）
            if (playId.equals(30)) {
                bool = isExist(betNum, results, 5);
                break;

                // 【五星组选60】（二重号1个，单号3个）
            } else if (playId.equals(31)) {
                if ("2".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 3);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【五星组选30】（二重号2个，单号1个）
            } else if (playId.equals(32)) {
                if ("2".equals(index)) {
                    bool = isExist(betNum, results, 2);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【五星组选20】（三重号1个，单号2个）
            } else if (playId.equals(33)) {
                if ("3".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 2);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【五星组选10】（三重号1个，二重号1个）
            } else if (playId.equals(34)) {
                if ("3".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("2".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【五星组选5】（四重号1个，单号1个）
            } else if (playId.equals(35)) {
                if ("4".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【前四/后四组选24】（单号4个）
            } else if (playId.equals(43) || playId.equals(52)) {
                if ("1".equals(index)) {
                    bool = isExist(betNum, results, 4);
                } else {
                    bool = false;
                }
                break;

                // 【前四/后四组选12】（二重号1个，单号2个）
            } else if (playId.equals(44) || playId.equals(53)) {
                if ("2".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 2);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【前四/后四组选6】（二重号2个）
            } else if (playId.equals(45) || playId.equals(54)) {
                if ("2".equals(index)) {
                    bool = isExist(betNum, results, 2);
                } else {
                    bool = false;
                }
                break;

                // 【前四/后四组选4】（三重号1个，单号1个）
            } else if (playId.equals(46) || playId.equals(55)) {
                if ("3".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else if ("1".equals(index)) {
                    bool = isExist(betNum, results, 1);
                    if (bool) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    bool = false;
                }
                break;

                // 【前三/后三组选6】（单号3个）
            } else if (playId.equals(61) || playId.equals(68) || playId.equals(75)) {
                if ("1".equals(index)) {
                    bool = isExist(betNum, results, 3);
                } else {
                    bool = false;
                }
                break;

                // 【前二/后二组选复式】（单号2个）
            } else if (playId.equals(85) || playId.equals(87)) {
                if ("1".equals(index)) {
                    bool = isExist(betNum, results, 2);
                } else {
                    bool = false;
                }
                break;
            }
        }
        return bool;
    }

    public static Boolean isExist(String betNum, char[] results, Integer count) {
        if (results.length != count) {
            return false;
        }

        int sum = 0;
        for (char c : results) {
            if (betNum.contains(String.valueOf(c))) {
                sum++;
            }
        }
        return sum == results.length;
    }

    public static Map<String, String> countDouble(String number, LotteryPlay play) {
        String[] sgs = number.split(",");

        String section = play.getSection();
        String[] sections = section.split(",");
        int start = Integer.parseInt(sections[0]);
        int end = Integer.parseInt(sections[1]);

        List<String> betNumber = new ArrayList<>(Arrays.asList(sgs).subList(start - 1, end));
        Map<String, String> map = new HashMap<>();
        getNumCount(betNumber, map);
        return map;
    }

    /**
     * 获取开奖号码重复的次数
     *
     * @param list 集合
     * @param map  重复map key：重复的次数； value：重复的数字
     */
    public static void getNumCount(List<String> list, Map<String, String> map) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String num = list.get(0);
        int count = 0;
        List<String> newList = new ArrayList<>();
        for (String bet : list) {
            if (num.equals(bet)) {
                count++;
            } else {
                newList.add(bet);
            }
        }
        String s = map.get(Integer.toString(count));
        s = s == null ? "" : s;
        if (!s.contains(num)) {
            s += num;
        }
        map.put(Integer.toString(count), s);
        getNumCount(newList, map);
    }

    /**
     * 判断是否中奖【直选单式】
     *
     * @param betNumber 投注号码
     * @param number    开奖号码
     * @param play      玩法
     * @return
     */
    public static Boolean isWinByDS(String betNumber, String number, LotteryPlay play) {
        String section = play.getSection();
        String[] sections = section.split(",");
        Integer start = Integer.parseInt(sections[0]);
        Integer end = Integer.parseInt(sections[1]);
        String[] bets = betNumber.split(",");
        String[] num = number.split(",");
        Integer tag = 0;
        for (int i = start - 1, j = 0; i < end; i++, j++) {
            switch (i) {
                case 0:
                    if (bets[j].equals(num[0])) {
                        tag++;
                    }
                    break;
                case 1:
                    if (bets[j].equals(num[1])) {
                        tag++;
                    }
                    break;
                case 2:
                    if (bets[j].equals(num[2])) {
                        tag++;
                    }
                    break;
                case 3:
                    if (bets[j].equals(num[3])) {
                        tag++;
                    }
                    break;
                default:
                    if (bets[j].equals(num[4])) {
                        tag++;
                    }
                    break;
            }
        }
        return tag.equals(end - start + 1);
    }

    /**
     * 判断是否中奖【直选复式】
     *
     * @param betNumber 投注号码
     * @param number    开奖号码
     * @param play      玩法
     * @return
     */
    public static Boolean isWinByDD(String betNumber, String number, LotteryPlay play) {
        String section = play.getSection();
        String[] sections = section.split(",");
        Integer start = Integer.parseInt(sections[0]);
        Integer end = Integer.parseInt(sections[1]);
        String[] bets = betNumber.split(",");
        String[] num = number.split(",");
        Integer tag = 0;
        for (int i = start - 1, j = 0; i < end; i++, j++) {
            switch (i) {
                case 0:
                    if (bets[j].contains(num[0])) {
                        tag++;
                    }
                    break;
                case 1:
                    if (bets[j].contains(num[1])) {
                        tag++;
                    }
                    break;
                case 2:
                    if (bets[j].contains(num[2])) {
                        tag++;
                    }
                    break;
                case 3:
                    if (bets[j].contains(num[3])) {
                        tag++;
                    }
                    break;
                default:
                    if (bets[j].contains(num[4])) {
                        tag++;
                    }
                    break;
            }
        }
        return tag.equals(end - start + 1);
    }

    /**
     * 判断定位大小单双是否中奖
     *
     * @param betNumber 投注号码
     * @param number    开奖号码
     * @return
     */
    public static Boolean isWinBySizeDs(String betNumber, String number) {
        String str = betNumber.trim().substring(0, 2);
        String[] numStr = number.split(",");
        Integer num;
        switch (str) {
            case "万位":
                num = Integer.valueOf(numStr[0]);
                break;
            case "千位":
                num = Integer.valueOf(numStr[1]);
                break;
            case "百位":
                num = Integer.valueOf(numStr[2]);
                break;
            case "十位":
                num = Integer.valueOf(numStr[3]);
                break;
            default:
                num = Integer.valueOf(numStr[4]);
                break;
        }

        String bigOrSmall = num > 4 ? "大" : "小";
        String singleOrDouble = num % 2 == 0 ? "双" : "单";
        String s = betNumber.substring(2);
        return s.equals(bigOrSmall) || s.equals(singleOrDouble);
    }

    /**
     * 判断两面是否中奖
     *
     * @param betNumber 投注号码
     * @param number    开奖号码
     * @return
     */
    public static Boolean isWinBylm(String betNumber, String number) {

        if (betNumber.contains("@")) {
            String[] betarr = betNumber.split("@");
            String qian = betarr[0];

            String[] numStr = number.split(",");
            Integer num;

            qian = qian.replace("第1球", "第一球");
            qian = qian.replace("第2球", "第二球");
            qian = qian.replace("第3球", "第三球");
            qian = qian.replace("第4球", "第四球");
            qian = qian.replace("第5球", "第五球");
            switch (qian) {
                case "第一球":
                    num = Integer.valueOf(numStr[0]);
                    break;
                case "第二球":
                    num = Integer.valueOf(numStr[1]);
                    break;
                case "第三球":
                    num = Integer.valueOf(numStr[2]);
                    break;
                case "第四球":
                    num = Integer.valueOf(numStr[3]);
                    break;
                case "第五球":
                    num = Integer.valueOf(numStr[4]);
                    break;
                default:
                    num = Integer.valueOf(numStr[0]) + Integer.valueOf(numStr[1]) + Integer.valueOf(numStr[2]) + Integer.valueOf(numStr[3]) + Integer.valueOf(numStr[4]);
                    String bigOrSmall = num > 22 ? "总和大" : "总和小";
                    String singleOrDouble = num % 2 == 0 ? "总和双" : "总和单";

                    String longhuhe = Integer.valueOf(numStr[0]) > Integer.valueOf(numStr[4]) ? "龙" : Integer.valueOf(numStr[0]) .equals(Integer.valueOf(numStr[4]))  ? "和" : "虎";
                    String s = betarr[1];
                    if ("大".equals(s)) {
                        s = "总和大";
                    } else if ("小".equals(s)) {
                        s = "总和小";
                    } else if ("单".equals(s)) {
                        s = "总和单";
                    } else if ("双".equals(s)) {
                        s = "总和双";
                    }
                    return s.equals(bigOrSmall) || s.equals(singleOrDouble) || s.equals(longhuhe);

            }

            String bigOrSmall = num > 4 ? "大" : "小";
            String singleOrDouble = num % 2 == 0 ? "双" : "单";
            String s = betarr[1];
            return s.equals(bigOrSmall) || s.equals(singleOrDouble);
        } else {
            return false;
        }
    }

    public static Boolean isWinByDN(String betNumber, String number) {

        if (betNumber.contains("@")) {
            String[] betarr = betNumber.split("@");
            String[] numStr = number.split(",");
            int[] num = new int[5];
            for (int i = 0; i < numStr.length; i++) {
                num[i] = Integer.parseInt(numStr[i]);
            }
            int sum = 0;
            for (int i = 0; i < num.length; i++) {
                for (int j = 0; j < num.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    for (int k = 0; k < num.length; k++) {
                        if (i == k || j == k) {
                            continue;
                        }
                        sum = num[i] + num[j] + num[k];
                        int count = 0;
                        String isniu = "";
                        if (sum == 0 || sum % 10 == 0) {
                            for (int x : num) {
                                count += x;
                            }
                            int niu = (count - sum) % 10;
                            switch (niu) {
                                case 0:
                                    isniu = "牛牛";
                                    break;
                                case 1:
                                    isniu = "牛一";
                                    break;
                                case 2:
                                    isniu = "牛二";
                                    break;
                                case 3:
                                    isniu = "牛三";
                                    break;
                                case 4:
                                    isniu = "牛四";
                                    break;
                                case 5:
                                    isniu = "牛五";
                                    break;
                                case 6:
                                    isniu = "牛六";
                                    break;
                                case 7:
                                    isniu = "牛七";
                                    break;
                                case 8:
                                    isniu = "牛八";
                                    break;
                                case 9:
                                    isniu = "牛九";
                                    break;
                                default:
                                    break;
                            }
                            return isniu.equals(betarr[1]);
                        }
                    }
                }

            }
            return "无牛".equals(betarr[1]);
        }
        return null;
    }

    public static Boolean isWinBy15(String betNumber, String number, Integer playId) {
        if (betNumber.contains("@")) {
            String[] betarr = betNumber.split("@");
            String qian = betarr[0];
            String stringBetNum = betarr[1];
            String[] numStr = number.split(",");
            Integer num;
            if ("大".equals(stringBetNum) || "小".equals(stringBetNum) ||  "单".equals(stringBetNum) ||  "双".equals(stringBetNum)) {
                switch (String.valueOf(playId).substring(4, 6)) {
                    case "05":
                        num = Integer.valueOf(numStr[0]);
                        break;
                    case "06":
                        num = Integer.valueOf(numStr[1]);
                        break;
                    case "07":
                        num = Integer.valueOf(numStr[2]);
                        break;
                    case "08":
                        num = Integer.valueOf(numStr[3]);
                        break;
                    case "09":
                        num = Integer.valueOf(numStr[4]);
                        break;
                    default:
                        return false;
                }
                if (("大".equals(stringBetNum) && num > 4)
                        || ("小".equals(stringBetNum) && num <= 4)
                        || ("单".equals(stringBetNum) && num % 2 != 0)
                        || ("双".equals(stringBetNum) && num % 2 == 0)) {
                    return true;
                }
                return false;

            } else {
                int betnum = Integer.parseInt(betarr[1]);
                qian = qian.replace("第1球", "第一球");
                qian = qian.replace("第2球", "第二球");
                qian = qian.replace("第3球", "第三球");
                qian = qian.replace("第4球", "第四球");
                qian = qian.replace("第5球", "第五球");
                switch (qian) {
                    case "第一球":
                        num = Integer.valueOf(numStr[0]);
                        break;
                    case "第二球":
                        num = Integer.valueOf(numStr[1]);
                        break;
                    case "第三球":
                        num = Integer.valueOf(numStr[2]);
                        break;
                    case "第四球":
                        num = Integer.valueOf(numStr[3]);
                        break;
                    case "第五球":
                        num = Integer.valueOf(numStr[4]);
                        break;
                    default:
                        return false;

                }

                return betnum == (num);
            }

        } else {
            return false;
        }
    }

    public static Boolean isWinByqzh(String betNumber, String number) {
        if (betNumber.contains("@")) {
            String[] betarr = betNumber.split("@");
            String qian = betarr[0];
            String hou = betarr[1];
            String[] numStr = number.split(",");
            int[] num = new int[3];
            switch (qian) {
                case "前三":
                    num[0] = Integer.parseInt(numStr[0]);
                    num[1] = Integer.parseInt(numStr[1]);
                    num[2] = Integer.parseInt(numStr[2]);
                    break;
                case "中三":
                    num[0] = Integer.parseInt(numStr[1]);
                    num[1] = Integer.parseInt(numStr[2]);
                    num[2] = Integer.parseInt(numStr[3]);
                    break;
                case "后三":
                    num[0] = Integer.parseInt(numStr[2]);
                    num[1] = Integer.parseInt(numStr[3]);
                    num[2] = Integer.parseInt(numStr[4]);
                    break;
            }
            String s = "";
            int[] numold = Arrays.copyOf(num, 3);
            Arrays.sort(num);
            int[] ary1 = {0, 8, 9};   //8,9,0  排序后 0,8,9

            int[] ary2 = {0, 1, 9};   //9,0,1  排序后 0,1,9
            if (num[0] == (num[1]) && num[1] == (num[2])) {
                s = "豹子";

            } else if ((num[2] - num[1] == num[1] - num[0] && num[1] - num[0] == 1) || Arrays.equals(num, ary1) || Arrays.equals(num, ary2)) {
                s = "顺子";
            } else if (num[2] == num[1] || num[1] == num[0]) {
                s = "对子";
            } else if (num[2] - num[1] == 1 || num[1] - num[0] == 1 || (num[2] == 9 && num[0] == 0)) {
                s = "半顺";
            } else {
                s = "杂六";
            }
            return s.equals(hou);
        }
        return false;
    }

}
