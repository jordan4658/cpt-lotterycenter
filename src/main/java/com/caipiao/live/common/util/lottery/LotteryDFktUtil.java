package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 * 
 * @author 阿布 大发-快三相关玩法
 *
 */
public class LotteryDFktUtil {

	/**
	 * 获取快三开奖期数（分间隔）
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
	 * 根据输入日期获取快三开奖期数（分间隔）
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
	 * 获取快三开奖当期截止时间（秒）
	 * 
	 * @param d 每期开奖间隔分钟数
	 * @return 投注截止秒数
	 */
	public synchronized static int getMinCountdown(double d) {
		Calendar ca = Calendar.getInstance();
		return (int) ((d - (ca.get(Calendar.HOUR_OF_DAY) * 60 + ca.get(Calendar.MINUTE)) % d) * 60 - ca.get(Calendar.SECOND));
	}

	/**
	 * 获取上一期快三开奖期数（分间隔）
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
	 * 11~18为大
	 * 
	 * @param sum
	 * @return
	 */
	public synchronized static String isSumBig(int sum) {
		return sum > 10 ? "大" : "小";
	}

	/**
	 * 
	 * @param luckynum 开奖号码
	 * @return 单 双
	 */
	public synchronized static String isSingle(int luckynum) {
		return luckynum % 2 == 0 ? "双" : "单";
	}

	/**
	 * 龙 开奖第一球（万位）的号码 大于 第五球（个位）的号码。如：3XXX2、7XXX6、9XXX8...开奖为龙，投注龙者视为中奖，其它视为不中奖。
	 * 
	 * 虎 开奖第一球（万位）的号码 小于 第五球（个位）的号码。如：1XXX2、3XXX6、4XXX8..开奖为虎，投注虎者视为中奖，其它视为不中奖。
	 * 
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
	 * 
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
	 * 
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
	 * 三同号通选
	 * 
	 * 对所有相同的三个号码111/222/333/444/555/666进行投注,任意号码开除即为中奖
	 * 
	 * @param luckynums
	 * @return
	 */
	public synchronized static String getSamenums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(0).equals(nums.get(2))) {
			return "三同号通选";
		}
		return "";
	}

	/**
	 * 三同号单选
	 * 
	 * 对所有相同的三个号码111/222/333/444/555/666进行投注,所选号码开除即为中奖
	 * 
	 * @param luckynums
	 * @return
	 */
	public synchronized static String getSingleSamenums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(0).equals(nums.get(2))) {
			return nums.get(0) + "" + nums.get(0) + "" + nums.get(0);
		}
		return "";
	}

	/**
	 * 三不同号
	 * 
	 * 
	 * 开奖号码全部不相同,即为不同号;从1-6中任选3个或多个号码,所选号码与开奖号码的3个号码相同即为中奖.
	 * 
	 * @param luckynum1
	 * @param luckynum2
	 * @param luckynum3
	 * @return
	 */
	public synchronized static String getDefrentnums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (!nums.get(0).equals(nums.get(1))  && !nums.get(1).equals(nums.get(2))) {
			return nums.get(0) + "," + nums.get(1) + "," + nums.get(2);
		}
		return "";
	}

	/**
	 * 三连号通选
	 * 
	 * 对所有3个相连号码123/234/345/456进行投注,任意号码开出即中奖
	 * 
	 * @param luckynums
	 * @return 为空即组选六不成立
	 */
	public synchronized static String getSerialnums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(2) - nums.get(1) == 1 && nums.get(1) - nums.get(0) == 1) {
			return "三连号通选";
		}
		return "";
	}

	/**
	 * 二同号复选
	 * 
	 * 开奖号码有两个相同,即为二同号,从11-66中任选一个或多个号码,选号与奖号(包涵11-66,不限顺序)相同,即为中奖
	 * 
	 * 不包含豹子111-666
	 * 
	 * @param luckynums
	 * @return
	 */
	public synchronized static String getDoubleSamenums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(2).equals(nums.get(1))  &&  !nums.get(1).equals(nums.get(0))) {
			return nums.get(2) + "" + nums.get(1);
		} else if (nums.get(0).equals(nums.get(1))  && !nums.get(1).equals(nums.get(2))) {
			return nums.get(0) + "" + nums.get(1);
		}
		return "";
	}

	/**
	 * 二同号单选
	 * 
	 * 选择1对相同号码和1个不同号码投注,选号与奖号相同,即为中奖
	 * 
	 * @param luckynums
	 * @return
	 */
	public synchronized static String getDoubleSinglenums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(2).equals(nums.get(1)) && !nums.get(1).equals(nums.get(0))) {
			return nums.get(2) + "" + nums.get(1) + "," + nums.get(0);
		} else if (nums.get(0).equals(nums.get(1))  && !nums.get(1).equals(nums.get(2))) {
			return nums.get(0) + "" + nums.get(1) + "," + nums.get(2);
		}
		return "";
	}

	/**
	 * 二不同号
	 * 
	 * 从1-6中任选2个或多个号码,所选号码与开奖号码任意2个号码相同,即为中奖
	 * 
	 * @param luckynums
	 * @return
	 */
	public synchronized static String getDoublenums(Integer... luckynums) {
		List<Integer> nums = Arrays.asList(luckynums);
		if (nums != null && nums.size() == 3) {
			nums.sort(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}
		if (nums.get(2).equals(nums.get(1)) && nums.get(1).equals(nums.get(0))) {
			return "";
		} else if (nums.get(2).equals(nums.get(1)) && !nums.get(1).equals( nums.get(0))) {
			return nums.get(2) + "," + nums.get(0);
		} else if (nums.get(0).equals(nums.get(1)) && !nums.get(1).equals(nums.get(2))) {
			return nums.get(0) + "," + nums.get(2);
		} else {
			return nums.get(0) + "," + nums.get(1) + "," + nums.get(2);
		}
	}

//	public static void main(String[] arg) throws Exception {
//		System.out.println(getMinPeriodOfDay(4, 1.0));
//		System.out.println(getMinPeriodByTime(4, 1.0, new Date()));
//		System.out.println(getMinCountdown(1.0));
//		System.out.println(getPreMinPeriod(getMinPeriodOfDay(4, 1.0), 1.0));
//		System.out.println(getSamenums(6, 6, 6));
//		System.out.println(getSingleSamenums(1,1,1));
//		System.out.println(getDefrentnums(1, 1, 4));
//		System.out.println(getSerialnums(4, 5, 2));
//		System.out.println(getDoubleSamenums(1, 3, 1));
//		System.out.println(getDoubleSinglenums(1, 1, 1));
//		System.out.println(getDoublenums(1, 2, 3));
//	}
}
