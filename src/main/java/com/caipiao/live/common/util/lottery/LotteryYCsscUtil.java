package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 * @author 阿布 易彩时时彩相关玩法
 */
public class LotteryYCsscUtil {

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
        return sum > 22 ? "总和大" : "总和小";
    }

    /**
     * @param luckynum 开奖号码
     * @return 单 双
     */
    public synchronized static String isSingle(int luckynum) {
        return luckynum % 2 == 0 ? "双" : "单";
    }

    public synchronized static String isSumSingle(int sum) {
        return sum % 2 == 0 ? "总和双" : "总和单";
    }

    /**
     * 豹子 如中奖号码为：222XX、666XX、888XX...开奖号码的万位千位百位数字相同，则投注前三豹子者视为中奖，其它视为不中奖。
     * <p>
     * 顺子 开奖号码都相连，不分顺序（数字9、0、1相连）。如中奖号码为：123XX、901XX、321XX、798XX...开奖号码的万位千位百位数字相连，则投注前三顺子者视为中奖，其它视为不中奖。
     * <p>
     * 对子 开奖号码任意两位数字相同（不包括豹子）。如中奖号码为：001XX，288XX、696XX...开奖号码的万位千位百位有两位数字相同，则投注前三对子者视为中奖，其它视为不中奖。如果开奖号码为前三豹子，则前三对子视为不中奖。
     * <p>
     * 半顺 开奖号码的万位千位百位任意两位数字相连，不分顺序（不包括顺子、对子）。如中奖号码为：125XX、540XX、390XX、160XX...开奖号码的万位千位百位有两位数字相连，则投注前三半顺者视为中奖，其它视为不中奖。如果开奖号码为前三顺子、前三对子，则前三半顺视为不中奖。如开奖号码为：123XX、901XX、556XX、233XX...视为不中奖。
     * <p>
     * 杂六 不包括豹子、对子、顺子、半顺的所有开奖号码。如开奖号码为：157XX、268XX...开奖号码位数之间无关联性，则投注前三杂六者视为中奖，其它视为不中奖。
     *
     * @param luckynums 多组开奖号码
     * @return 豹子 顺子 对子 半顺 杂六
     */
    public synchronized static String isPreSeries(Integer... luckynums) {
        List<Integer> nums = Arrays.asList(luckynums);
        if (nums != null) {
            nums.sort(new Comparator<Integer>() {
                public int compare(Integer i1, Integer i2) {
                    return i1.compareTo(i2);
                }
            });
            if (nums.get(0).equals(nums.get(2))) {
                return "豹子";
            } else if ((nums.get(2) - nums.get(1) == 8 || nums.get(2) - nums.get(1) == 1) && nums.get(1) - nums.get(0) == 1) {
                return "顺子";
            } else if (nums.get(2).equals(nums.get(1)) || nums.get(1).equals(nums.get(0))) {
                return "对子";
            } else if (nums.get(2) - nums.get(1) == 8 || nums.get(2) - nums.get(1) == 1 || nums.get(1) - nums.get(0) == 1) {
                return "半顺";
            } else {
                return "杂六";
            }
        }
        return null;
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
                return "没牛";
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
            return "没牛";
        }
        return null;
    }

    /**
     * 梭哈：开奖号码不分顺序
     * <p>
     * 五条：开奖的五个号码全部相同，例如：22222、66666、88888 投注梭哈：五条 中奖，其它不中奖。
     * <p>
     * 四条：开奖的五个号码中有四个号码相同，例如：22221、66663、88885 投注梭哈：四条 中奖，其它不中奖。
     * <p>
     * 葫芦：开奖的五个号码中有三个号码相同(三条)另外两个号码也相同(一对)，例如：22211、66633 投注梭哈：葫芦 中奖，其它不中奖。
     * <p>
     * 顺子：开奖的五个号码从小到大排列为顺序(号码9、0、1相连)，例如：23456、89012、90123 投注梭哈：顺子 中奖，其它不中奖。
     * <p>
     * 三条：开奖的五个号码中有三个号码相同另外两个不相同，例如：22231、66623、88895 投注梭哈：三条 中奖，其它不中奖。
     * <p>
     * 两对：开奖的五个号码中有两组号码相同，例如：22166、66355、82668 投注梭哈：两对 中奖，其它不中奖。
     * <p>
     * 一对：开奖的五个号码中只有一组号码相同，例如：22168、66315、82968 投注梭哈：一对 中奖，其它不中奖。
     * <p>
     * 散号：开奖号码不是五条、四条、葫芦、三条、顺子、两对、一对的其它所有开奖号码，例如：23186、13579、21968 投注梭哈：散号 中奖，其它不中奖。
     *
     * @param luckynums 5位开奖号码
     * @return
     */
    public synchronized static String isSuoha(Integer... luckynums) {
        List<Integer> nums = Arrays.asList(luckynums);
        if (nums != null && nums.size() == 5) {
            nums.sort(new Comparator<Integer>() {
                public int compare(Integer i1, Integer i2) {
                    return i1.compareTo(i2);
                }
            });
            if (nums.get(0).equals(nums.get(4))) {
                return "五条";
            } else if (nums.get(0).equals(nums.get(3)) || nums.get(1).equals(nums.get(4))) {
                return "四条";
            } else if ((nums.get(0).equals(nums.get(2)) && !nums.get(3).equals(nums.get(4))) || nums.get(1).equals(nums.get(3)) || (!nums.get(0).equals(nums.get(1)) && nums.get(2).equals(nums.get(4))))
                return "三条";
            else if ((nums.get(0).equals(nums.get(2)) && nums.get(3).equals(nums.get(4))) || (nums.get(0).equals(nums.get(1)) && nums.get(2).equals(nums.get(4)))) {
                return "葫芦";
            } else if (isStraight(nums)) {
                return "顺子";
            } else if (isDoublePairs(nums)) {
                return "两对";
            } else if (isSinglePairs(nums)) {
                return "一对";
            } else {
                return "散号";
            }
        }
        return null;
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

//	public static void main(String[] arg) throws Exception {
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
//	}
}
