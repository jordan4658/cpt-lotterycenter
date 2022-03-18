package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.text.ParseException;
import java.util.*;

/**
 * @author 阿布 彩播时时彩相关玩法
 */
public class LotteryCBsscUtil {

    /**
     * 获取分彩开奖期数（分间隔）
     *
     * @param length 返回字符长度不足以"0"补位
     * @param d      每期开奖间隔分钟数
     * @return 期号： yyyyMMdd+4位自增
     */
    public synchronized static String getMinPeriodOfDay(int length, double d) {
        Calendar ca = Calendar.getInstance();
        String reg = "%0" + String.valueOf(length) + "d";
        return DateUtils.getCurrentDateString("yyyyMMDD") + String.format(reg, (int) Math
                .ceil((ca.get(Calendar.HOUR_OF_DAY) * 60 + ca.get(Calendar.MINUTE) + (ca.get(Calendar.SECOND) + (ca.get(Calendar.MILLISECOND) + 1) / 1000.0) / 60.0) / d));
    }

    /**
     * 根据输入日期获取分彩开奖期数（分间隔）
     *
     * @param length 返回字符长度不足以"0"补位
     * @param d      每期开奖间隔分钟数
     * @param date   时间
     * @return
     */
    public synchronized static String getMinPeriodByTime(int length, double d, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        String reg = "%0" + String.valueOf(length) + "d";
        return DateUtils.getCurrentDateString("yyyyMMDD") + String.format(reg, (int) Math
                .ceil((ca.get(Calendar.HOUR_OF_DAY) * 60 + ca.get(Calendar.MINUTE) + (ca.get(Calendar.SECOND) + (ca.get(Calendar.MILLISECOND) + 1) / 1000.0) / 60.0) / d));
    }

    /**
     * 获取分彩开奖当期截止时间（秒）
     *
     * @param d 每期开奖间隔分钟数
     * @return 投注截止秒数
     */
    public synchronized static int getMinCountdown(double d) {
        Calendar ca = Calendar.getInstance();
        return (int) ((d - (ca.get(Calendar.HOUR_OF_DAY) * 60 + ca.get(Calendar.MINUTE)) % d) * 60 - ca.get(Calendar.SECOND));
    }

    /**
     * 获取上一期分彩开奖期数（分间隔）
     *
     * @param period 当期期号
     * @return 上一期期号
     */
    public synchronized static String getPreMinPeriod(String period, double d) throws ParseException {
        if (StringUtils.isEmpty(period) || period.length() <= 8) {
            return null;
        }
        Date date = DateUtils.parseDate(period.substring(0, 8), "yyyyMMdd");
        String no = period.substring(8, period.length());
        int total = (int) (24 * 60 / d);
        String reg = "%0" + String.valueOf(no.length()) + "d";
        if (Integer.parseInt(no) == 1) {
            return DateUtils.formatDate(DateUtils.beforeDays(date, 1), "yyyyMMdd") + String.format(reg, total);
        } else {
            return period.substring(0, 8) + String.format(reg, Integer.parseInt(no) - 1);
        }
    }

    /**
     * 获取秒彩开奖期号（秒间隔）
     *
     * @param length 返回字符长度不足以"0"补位
     * @param d      每期开奖间隔秒数
     * @return 期号： yyyyMMdd+6位自增
     */
    public synchronized static String getSecPeriodOfDay(int length, double d) {
        if (length < 6) {
            length = 6;
        }
        Calendar ca = Calendar.getInstance();
        String reg = "%0" + String.valueOf(length) + "d";
        return DateUtils.getCurrentDateString("yyyyMMDD") + String.format(reg, (int) Math
                .ceil((ca.get(Calendar.HOUR_OF_DAY) * 60 * 60 + ca.get(Calendar.MINUTE) * 60 + ca.get(Calendar.SECOND) + (ca.get(Calendar.MILLISECOND) + 1) / 1000.0) / d));
    }

    /**
     * 获取上一期秒彩开奖期号（秒间隔）
     *
     * @param period 当期期号
     * @return 上一期期号
     * @throws ParseException
     */
    public synchronized static String getPreSecPeriod(String period, double d) throws ParseException {
        if (StringUtils.isEmpty(period) || period.length() <= 8) {
            return null;
        }
        Date date = DateUtils.parseDate(period.substring(0, 8), "yyyyMMdd");
        String no = period.substring(8, period.length());
        int total = (int) (24 * 60 * 60 / d);
        String reg = "%0" + String.valueOf(no.length()) + "d";
        if (Integer.parseInt(no) == 1) {
            return DateUtils.formatDate(DateUtils.beforeDays(date, 1), "yyyyMMdd") + String.format(reg, total);
        } else {
            return period.substring(0, 8) + String.format(reg, Integer.parseInt(no) - 1);
        }
    }

    /**
     * @param luckynum 开奖号码
     * @return 大 小
     */
    public synchronized static String isBig(int luckynum) {
        return luckynum > 4 ? "大" : "小";
    }

    public synchronized static String isSumBig(int sum) {
        return sum > 22 ? "总大" : "总小";
    }

    /**
     * @param luckynum 开奖号码
     * @return 单 双
     */
    public synchronized static String isSingle(int luckynum) {
        return luckynum % 2 == 0 ? "双" : "单";
    }

    public synchronized static String isSumSingle(int sum) {
        return sum % 2 == 0 ? "总双" : "总单";
    }

    /**
     * 龙 开奖第一球（万位）的号码 大于 第五球（个位）的号码。如：3XXX2、7XXX6、9XXX8...开奖为龙，投注龙者视为中奖，其它视为不中奖。
     * <p>
     * 虎 开奖第一球（万位）的号码 小于 第五球（个位）的号码。如：1XXX2、3XXX6、4XXX8..开奖为虎，投注虎者视为中奖，其它视为不中奖。
     * <p>
     * 和 开奖第一球（万位）的号码 等于 第五球（个位）的号码。如：2XXX2、6XXX6、8XXX8...开奖为和，投注和者视为中奖，其它视为不中奖。
     *
     * @param luckynum1 第一位开奖号码
     * @param luckynum5 第五位开奖号码
     * @return 龙 虎 和
     */
    public synchronized static String isDragon(int luckynum1, int luckynum5) {
        if (luckynum1 == luckynum5) {
            return "和";
        }
        return luckynum1 > luckynum5 ? "龙" : "虎";
    }

    /**
     * @param luckynum 质数 合数
     * @return
     */
    public synchronized static String isPrimeNumber(int luckynum) {
        if ("1,2,3,5,7".contains(String.valueOf(luckynum))) {
            return "质";
        }
        return "合";
    }

    /**
     * @param luckynums 非重复
     * @return
     */
    public synchronized static String getNonrepeating(Integer... luckynums) {
        HashSet<String> set = new HashSet<String>();
        for (Integer num : luckynums) {
            set.add(String.valueOf(num));
        }
        return String.join(",", set);
    }

    /**
     * 组选三
     * <p>
     * <p>
     * 三个位置开奖号码与所下注号码中有两个相同且其中某个号码重复(如 112)，即为中奖；
     *
     * @param luckynum1
     * @param luckynum2
     * @param luckynum3
     * @return 为空即为组选三不成立
     */
    public synchronized static String getSinglePairs(Integer luckynum1, Integer luckynum2, Integer luckynum3) {
        if (luckynum1.intValue() == luckynum2.intValue() && luckynum2.intValue() != luckynum3.intValue()) {
            return luckynum1 + "," + luckynum3;
        } else if (luckynum1.intValue() == luckynum3.intValue() && luckynum1.intValue() != luckynum2.intValue()) {
            return luckynum1 + "," + luckynum2;
        } else if (luckynum2.intValue() == luckynum3.intValue() && luckynum1.intValue() != luckynum2.intValue()) {
            return luckynum1 + "," + luckynum2;
        }
        return "";
    }

    /**
     * 组选六
     * <p>
     * 三个位置开奖号码(三个互不相同)与所下注号码中有三个相同(如123)，即为中奖；
     *
     * @param luckynum1
     * @param luckynum2
     * @param luckynum3
     * @return 为空即组选六不成立
     */
    public synchronized static String getNrepeatingSix(Integer luckynum1, Integer luckynum2, Integer luckynum3) {
        if (luckynum1.intValue() != luckynum2.intValue() && luckynum2.intValue() != luckynum3.intValue() && luckynum1.intValue() != luckynum3.intValue()) {
            return luckynum1 + "," + luckynum2 + "," + luckynum3;
        }
        return "";
    }

    /**
     * 跨度
     * <p>
     * 三个位置开奖号码中的最大值减去最小值所得的数与下注的号码相同，即为中奖；
     *
     * @param luckynum1
     * @param luckynum2
     * @param luckynum3
     * @return
     */
    public synchronized static String getSpan(Integer... luckynums) {
        List<Integer> nums = Arrays.asList(luckynums);
        if (nums != null && nums.size() == 3) {
            nums.sort(new Comparator<Integer>() {
                public int compare(Integer i1, Integer i2) {
                    return i1.compareTo(i2);
                }
            });
            return String.valueOf(nums.get(nums.size() - 1) - nums.get(0));
        }
        return "";
    }

    /**
     * 斗牛：开奖号码不分顺序
     * <p>
     * 牛牛：根据开奖第一球 ~ 第五球开出的球号数字为基础，任意组合三个号码成0或10的倍数，取剩余两个号码之和为点数（大于10时减去10后的数字作为对奖基数，如：00026为牛8，02818为牛9，68628、23500皆为牛10俗称牛牛；
     * <p>
     * 没牛：26378、15286因任意三个号码都无法组合成0或10的倍数，称为没牛，注：当五个号码相同时，只有00000视为牛牛，其它11111，66666等皆视为没牛）
     * <p>
     * 大小：牛大(牛6,牛7,牛8,牛9,牛牛)，牛小(牛1,牛2,牛3,牛4,牛5)，若开出斗牛结果为没牛，则投注牛大牛小皆为不中奖。
     * <p>
     * 单双：牛单(牛1,牛3,牛5,牛7,牛9)，牛双(牛2,牛4,牛6,牛8,牛牛)，若开出斗牛结果为没牛，则投注牛单牛双皆为不中奖。
     *
     * @param luckynums 5位开奖号码
     * @return 没牛 牛牛 牛X 牛大 牛小 牛单 牛双
     */
    public synchronized static String isBull(Integer... luckynums) {
        List<Integer> nums = Arrays.asList(luckynums);
        if (nums != null && nums.size() == 5) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum == 0) {
                return "牛牛,牛大,牛双";
            }
            if (sum < 10) {
                return "无点";
            } else {
                for (int i = 0; i < nums.size(); i++) {
                    List<Integer> nums2 = new ArrayList<Integer>(nums);
                    nums2.remove(i);
                    for (int j = 0; j < nums2.size(); j++) {
                        List<Integer> nums3 = new ArrayList<Integer>(nums2);
                        nums3.remove(j);
                        for (int k = 0; k < nums3.size(); k++) {
                            if ((nums.get(i) + nums2.get(j) + nums3.get(k)) % 10 == 0) {
                                nums3.remove(k);
                                int sum2 = nums3.get(0) + nums3.get(1);
                                if (sum2 % 10 == 0) {
                                    return "牛牛,牛大,牛双";
                                }
                                return "牛" + sum2 + ",牛" + (sum2 > 5 ? "大" : "小") + ",牛" + (sum2 % 2 == 0 ? "双" : "单");
                            }
                        }
                    }
                }
            }
            return "无点";
        }
        return null;
    }

    /**
     * 德州扑克：
     * <p>
     * 豹子：当开出的五个号码都相同，如：22222、33333，99999等；
     * <p>
     * 四张：当五个开奖号码中有四个为一样，如：00001、77797等；
     * <p>
     * 葫芦：当五个开奖号码中三个相同（三条）及两个号码相同（一对），如：77997、45544；
     * <p>
     * 顺子：当五个号码是一串顺序的数字。如：09213、65743；
     * <p>
     * 三张：当五个号码中三个相同，且余下的两个号码完全不同，如：87477、65455等；
     * <p>
     * 两对：当五个号码，能组成两个对子，如：97789、01022等；
     * <p>
     * 一对：当五个开奖号码，能够组成一个对子，如：65877、01322等；
     * <p>
     * 杂牌：当五个号码全部都不一样，不能够组成任意对子或顺子，(不包含五离) 如：06587、98763；
     * <p>
     * 五离：当五个号码不能够组成对子，并且没有任何相邻的两个数。如：28064，19573等；
     *
     * @param luckynums 多组开奖号码
     * @return
     */
    public synchronized static String isDezhou(Integer... luckynums) {
        List<Integer> nums = Arrays.asList(luckynums);
        if (nums != null && nums.size() == 5) {
            nums.sort(new Comparator<Integer>() {
                public int compare(Integer i1, Integer i2) {
                    return i1.compareTo(i2);
                }
            });
            if (nums.get(0).equals(nums.get(4))) {
                return "豹子";
            } else if (nums.get(0).equals(nums.get(3)) || nums.get(1).equals(nums.get(4))) {
                return "四张";
            } else if ((nums.get(0).equals(nums.get(2)) && !nums.get(3).equals(nums.get(4))) || nums.get(1).equals(nums.get(3)) || !(nums.get(0).equals(nums.get(1)) && nums.get(2).equals(nums.get(4)))) {
                return "三张";
            } else if ((nums.get(0).equals(nums.get(2)) && nums.get(3).equals(nums.get(4))) || (nums.get(0).equals(nums.get(1)) && nums.get(2).equals(nums.get(4)))) {
                return "葫芦";
            } else if (isStraight(nums)) {
                return "顺子";
            } else if (isDoublePairs(nums)) {
                return "两对";
            } else if (isSinglePairs(nums)) {
                return "一对";
            } else if (Strings.join(nums.iterator(), ",".charAt(0)).equals("1,3,5,7,9") || Strings.join(nums.iterator(), ",".charAt(0)).equals("0,2,4,6,8")) {
                return "五离";
            } else {
                return "杂牌";
            }
        }
        return null;
    }

    /**
     * 三公
     * <p>
     * 左闲：即 开奖五个数字的前三位（万、千、百）之和的个位数。
     * <p>
     * 右闲：即 开奖五个数字的后三位（百、十、个）之和的个位数。
     * <p>
     * 和局：即当期左闲与右闲数值相同。
     * <p>
     * 规则一：当期左闲值大于右闲值，则当期为左闲，当右闲值大于左闲值则为右闲，当左闲 右闲值相同则本局为和。
     * <p>
     * 规则二：开奖结果左闲点数与右闲点数相同时，则押左闲或右闲均不计输赢，并且退还本金，且不计税费。同理当押注和局时开奖结果为左闲或右闲则都不中奖。
     * <p>
     * 左闲尾大小，左闲尾单双，左闲尾质合 即是 以左闲数值为依据：
     * <p>
     * 大小：0~4为小，5~9为大；
     * <p>
     * 单双： 1、3、5、7、9为单，0、2、4、6、8为双；
     * <p>
     * 质合： 1、2、3、5、7为质数，0、4、6、8、9为合数。
     *
     * @param luckynums
     * @return
     */
    public synchronized static String getSangong(Integer... luckynums) {
        String sum = String.valueOf(luckynums[0] + luckynums[1] + luckynums[2]);
        int left = Integer.parseInt(sum.substring(sum.length() - 1, sum.length()));
        sum = String.valueOf(luckynums[2] + luckynums[3] + luckynums[4]);
        int right = Integer.parseInt(sum.substring(sum.length() - 1, sum.length()));
        StringBuffer ret = new StringBuffer();
        if (left == right) {
            ret.append("和局");
        } else if (left > right) {
            ret.append("左闲");
        } else {
            ret.append("右闲");
        }
        ret.append(",").append("左闲尾" + isBig(left)).append(",").append("左闲尾" + isSingle(left)).append(",").append("左闲尾" + isPrimeNumber(left));
        ret.append(",").append("右闲尾" + isBig(right)).append(",").append("右闲尾" + isSingle(right)).append(",").append("右闲尾" + isPrimeNumber(right));
        return ret.toString();
    }

    /**
     * 百家乐
     * <p>
     * 庄是指以开奖五个号码为基础，选择前两个数（万千作为庄的前两张牌）。
     * <p>
     * 闲是指以开奖五个号码为基础，选择后两个数（十个作为闲的后两张牌）。
     * <p>
     * 如第一轮未分出胜负需要再按牌例发第二轮牌，第三张闲先发，最多每方3张牌，谁最接近9点即为胜方，而相同点数即为和局。
     * <p>
     * 如若投注庄闲，开奖结果为和局，那么不计输赢，并且退回本金。
     * <p>
     * 有一个天生赢家或者庄家闲家都大于等于6（庄闲都不补牌）
     * <p>
     * 个位与百位和数的个位数为第五张牌，万位和百位和数的个位数为第六张牌。
     * <p>
     * 例：开出号码为47098
     * <p>
     * 第一轮庄家手里拿4、7，共是4+7=11取个位是1点，小于6则需要补牌。闲家手里拿9、8，共是9+8=17取个位7点，大于6无需补牌。第五张牌为0+8=8取个位8，此时庄家补第五张牌，现庄家手里有4、7、8，共是4+7+8=19取个位是9点，庄家9点大于闲家7点，庄家胜。
     * *
     * 庄：以当前庄家与闲家点数来决定，庄家点数大于闲家，即为庄家胜，中奖；
     * <p>
     * 闲：以当前庄家与闲家点数来决定，庄家点数小于闲家，即为闲家胜，中奖；
     * <p>
     * 和局：以当前庄家与闲家点数来决定，庄家点数等于闲家，即为和局，中奖；
     * <p>
     * 庄对：庄家万位，仟位数字相同，即为对子，中奖；
     * <p>
     * 闲对：闲家拾位，个位数字相同，即为对子，中奖；
     * <p>
     * 庄大：以当前庄家点数来决定，庄家点数为5~9，即为庄大，中奖；
     * <p>
     * 庄小：以当前庄家点数来决定，庄家点数为0~4，即为庄小，中奖；
     * <p>
     * 庄单：以当前庄家点数来决定，庄家点数为1.3.5.7.9，即为庄单，中奖；
     * <p>
     * 庄双：以当前庄家点数来决定，庄家点数为0.2.4.6.8，即为庄双，中奖；
     * <p>
     * 庄质：以当前庄家点数来决定，庄家点数为1.2.3.5.7，即为庄质，中奖；
     * <p>
     * 庄合：以当前庄家点数来决定，庄家点数为0.4.6.8.9，即为庄合，中奖；
     * <p>
     * 闲大：以当前闲家点数来决定，闲家点数为5~9，即为闲大，中奖；
     * <p>
     * 闲小：以当前闲家点数来决定，闲家点数为0~4，即为闲小，中奖；
     * <p>
     * 闲单：以当前闲家点数来决定，闲家点数为1.3.5.7.9，即为闲单，中奖；
     * <p>
     * 闲双：以当前闲家点数来决定，闲家点数为0.2.4.6.8，即为闲双，中奖；
     * <p>
     * 闲质：以当前闲家点数来决定，闲家点数为1.2.3.5.7，即为闲质，中奖；
     * <p>
     * 闲合：以当前闲家点数来决定，闲家点数为0.4.6.8.9，即为闲合，中奖；
     *
     * @param luckynums
     * @return
     */
    public synchronized static String getBaccarat(Integer... luckynums) {
        StringBuffer ret = new StringBuffer();
        // 庄 banker
        String bankerstr = String.valueOf(luckynums[0] + luckynums[1]);
        int banker = Integer.parseInt(bankerstr.substring(bankerstr.length() - 1, bankerstr.length()));
        // 闲 player
        String playerstr = String.valueOf(luckynums[3] + luckynums[4]);
        int player = Integer.parseInt(playerstr.substring(playerstr.length() - 1, playerstr.length()));
        if (banker > 5 && player > 5) {
            ret.append(isBanker(banker, player));
        }
            // 第五张牌
        else if (banker > 5 && player < 6) {
            playerstr = String.valueOf(player + luckynums[2] + luckynums[4]);
            player = Integer.parseInt(playerstr.substring(playerstr.length() - 1, playerstr.length()));
            ret.append(isBanker(banker, player));
        } else if (banker < 6 && player > 5) {
            bankerstr = String.valueOf(banker + luckynums[2] + luckynums[4]);
            banker = Integer.parseInt(bankerstr.substring(bankerstr.length() - 1, bankerstr.length()));
            ret.append(isBanker(banker, player));
        } else {
            // 第六张牌
            playerstr = String.valueOf(player + luckynums[2] + luckynums[4]);
            player = Integer.parseInt(playerstr.substring(playerstr.length() - 1, playerstr.length()));
            bankerstr = String.valueOf(banker + luckynums[0] + luckynums[2]);
            banker = Integer.parseInt(bankerstr.substring(bankerstr.length() - 1, bankerstr.length()));
            ret.append(isBanker(banker, player));
        }
        if (luckynums[0].equals(luckynums[1])) {
            ret.append(",").append("庄对");
        }
        if (luckynums[3].equals(luckynums[4])) {
            ret.append(",").append("闲对");
        }
        ret.append(",").append("庄" + isBig(banker)).append(",").append("庄" + isSingle(banker)).append(",").append("庄" + isPrimeNumber(banker));
        ret.append(",").append("闲" + isBig(player)).append(",").append("闲" + isSingle(player)).append(",").append("闲" + isPrimeNumber(player));

        return ret.toString();
    }

    public synchronized static String isBanker(Integer banker, Integer player) {
        if (banker.equals(player)) {
            return "和局";
        } else if (banker > player) {
            return "庄";
        } else {
            return "闲";
        }
    }

    /**
     * 3个reduce1+1个reduce6 或者 4个reduce1即为顺子
     *
     * @param nums 小到大顺序list
     */
    public synchronized static boolean isStraight(List<Integer> nums) {
        // 相邻相减为1个数
        int reduce1 = 0;
        // 相邻相减为6个数
        int reduce6 = 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i + 1) - nums.get(i) == 1) {
                reduce1++;
            } else if (nums.get(i + 1) - nums.get(i) == 6) {
                reduce6++;
            }
        }
        return (reduce1 == 4 || (reduce1 == 3 && reduce6 == 1)) ? true : false;
    }

    /**
     * 1个reduce0+3个reduce1 即为一对
     *
     * @param nums
     * @return
     */
    public synchronized static boolean isSinglePairs(List<Integer> nums) {
        // 相邻相减为0个数
        int reduce0 = 0;
        // 相邻相减为1个数
        int reduce1 = 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i + 1) - nums.get(i) > 0) {
                reduce1++;
            } else if (nums.get(i + 1) - nums.get(i) == 0) {
                reduce0++;
            }
        }
        return (reduce1 == 3 && reduce0 == 1) ? true : false;
    }

    /**
     * 2个reduce0+2个reduce1 即为两对
     *
     * @param nums 小到大顺序list
     * @return
     */
    public synchronized static boolean isDoublePairs(List<Integer> nums) {
        // 相邻相减为0个数
        int reduce0 = 0;
        // 相邻相减为1个数
        int reduce1 = 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i + 1) - nums.get(i) > 0) {
                reduce1++;
            } else if (nums.get(i + 1) - nums.get(i) == 0) {
                reduce0++;
            }
        }
        return (reduce1 == 2 && reduce0 == 2) ? true : false;
    }

    public static void main(String[] arg) throws Exception {
//		System.out.println(getSangong(9, 9, 9, 5, 5));
//	    System.out.println(getBaccarat(7, 4, 7, 2, 8));
//		System.out.println(getSpan(0, 5, 9));
//		System.out.println(getNrepeatingSix(5, 2, 6));
//		System.out.println(getSinglePairs(6, 3, 6));
//		System.out.println(getNonrepeating("1","1","1","1","1","1","5","6","7","8","9","9"));
//		for (int i = 0; i < 10; i++) {
//			System.out.println(i + " = " + isPrimeNumber(i));
//		}
//		String period = getMinPeriodOfDay(4, 3.0);
//		System.out.println(period);
//		System.out.println(getPreSecPeriod(period, 1.0));
//		System.out.println(getPreMinPeriod(period, 3.0));
//		List<Integer> nums = new ArrayList<Integer>();
//		nums.add(9);
//		nums.add(0);
//		nums.add(1);
//		nums.sort(new Comparator<Integer>() {
//			public int compare(Integer i1, Integer i2) {
//				return i1.compareTo(i2);
//			}
//		});
//		System.out.println(nums.toString());
//		System.out.println(getMinCountdown(5));
//		System.out.println(isBull(2, 3, 5, 1, 9));
//		System.out.println(isSuoha(9, 8, 0, 1, 2));
    }
}
