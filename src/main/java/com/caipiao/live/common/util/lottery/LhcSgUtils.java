package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.enums.CalculationEnum;
import com.caipiao.live.common.util.CalendarUtil;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.StringUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LhcSgUtils {

	private static final Logger logger = LoggerFactory.getLogger(LhcSgUtils.class);
	/** 尾数 */
	public static final List<Integer> WEISHU = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	/** 红波 */
	public static final List<Integer> RED = Lists.newArrayList(1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40,
			45, 46);
	/** 蓝波 */
	public static final List<Integer> BLUE = Lists.newArrayList(3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47,
			48);
	/** 绿波 */
	public static final List<Integer> GREEN = Lists.newArrayList(5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43,
			44, 49);
	/** 所有球 */
	public static final List<Integer> BALLS = new ArrayList<>();
	/** 五行 */
	public static final List<String> WUXING = Lists.newArrayList("木", "水", "金", "火", "木", "土", "金", "火", "水", "土", "金",
			"木", "水", "土", "火", "木", "水", "金", "火", "木", "土", "金", "火", "水", "土", "金", "木", "水", "土", "火", "木");
	/** 生肖排序 */
	public static final ArrayList<String> SHENGXIAO = Lists.newArrayList("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴",
			"鸡", "狗", "猪");
	/** 家禽生肖 */
	public static final ArrayList<String> JIAQIN = Lists.newArrayList("牛", "马", "羊", "鸡", "狗", "猪");
	/** 野兽生肖 */
	public static final ArrayList<String> YESHOU = Lists.newArrayList("鼠", "虎", "兔", "龙", "蛇", "猴");

	/**
	 * 计算大小
	 * 
	 * @param number
	 * @return
	 */
	public static String getBigOrSmall(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		// 正码大小
		if (CalculationEnum.LHCSEVEN.getCode() == type) {
			String[] sgArr = number.split(",");
			int total = 0;
			for (String num : sgArr) {
				total += Integer.valueOf(num);
			}
			if (total >= 175) {
				return Constants.BIGORSMALL_BIG;
			} else {
				return Constants.BIGORSMALL_SMALL;
			}
		} else {
			if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
				return Constants.BIGORSMALL_SAME;
			}
			int numberSum = Integer.parseInt(numberSize);
			// 计算大小
			if (numberSum > Constants.LHC_BIGORSMALL_MEDIAN) {
				return Constants.BIGORSMALL_BIG;
			} else {
				return Constants.BIGORSMALL_SMALL;
			}
		}
	}

	/**
	 * 计算家禽或野兽
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String getPoultryOrBeast(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
			return Constants.BIGORSMALL_SAME;
		}
		int numberSum = Integer.parseInt(numberSize);
		Date date = new Date();
		// 转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
		String shengXiao = getShengXiao(numberSum, dateFormat.format(date));
		if (JIAQIN.contains(shengXiao)) {
			return Constants.BIGORSMALL_POULTRY;
		} else {
			return Constants.BIGORSMALL_BEAST;
		}
	}
	
	/**
	 * 计算生肖
	 * @param number
	 * @param type
	 * @return
	 */
	public static String attribute(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		int numberSum = Integer.parseInt(numberSize);
		Date date = new Date();
		// 转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
		String shengXiao = getShengXiao(numberSum, dateFormat.format(date));
		return shengXiao;
	}
	

	/**
	 * 计算合单双
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String joinSingleOrDouble(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		// 正码单双
		if (CalculationEnum.LHCSEVEN.getCode() == type) {
			String[] sgArr = number.split(",");
			int total = 0;
			for (String num : sgArr) {
				total += Integer.valueOf(num);
			}
			if (total % 2 == 1) {
				return Constants.BIGORSMALL_ODD_NUMBER;
			} else {
				return Constants.BIGORSMALL_EVEN_NUMBER;
			}
		} else {
			int numberSum = Integer.parseInt(numberSize);
			int shi = numberSum / 10;
			int ge = numberSum % 10;
			int he = shi + ge;
			if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
				return Constants.BIGORSMALL_SAME;
			}
			if (he % 2 == 1) {
				return Constants.BIGORSMALL_ODD_NUMBER;
			} else {
				return Constants.BIGORSMALL_EVEN_NUMBER;
			}
		}
	}

	/**
	 * 计算总尾单双
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String totalSingleOrDouble(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		int numberSum = Integer.parseInt(numberSize);
		int shi = numberSum / 10;
		int ge = numberSum % 10;
		int he = shi + ge;
		int sum = he % 10;
		if (sum % 2 == 1) {
			return Constants.BIGORSMALL_ODD_NUMBER;
		} else {
			return Constants.BIGORSMALL_EVEN_NUMBER;
		}
	}

	/**
	 * 计算单双
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String singleOrDouble(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
			return Constants.BIGORSMALL_SAME;
		}
		int numberSum = Integer.parseInt(numberSize);
		if (numberSum % 2 == 1) {
			return Constants.BIGORSMALL_ODD_NUMBER;
		} else {
			return Constants.BIGORSMALL_EVEN_NUMBER;
		}
	}

	/**
	 * 尾大小
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String tailBigOrSmall(String number, Integer type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		// 正码尾大小
		if (CalculationEnum.LHCSEVEN.getCode() == type) {
			String[] sgArr = number.split(",");
			int total = 0;
			for (String num : sgArr) {
				total += Integer.valueOf(num);
			}
			int wei = total % 10;
			if (wei >= 5) {
				return Constants.BIGORSMALL_BIG;
			} else {
				return Constants.BIGORSMALL_SMALL;
			}
		} else {
			if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
				return Constants.BIGORSMALL_SAME;
			}
			int numberSum = Integer.parseInt(numberSize);
			int ge = numberSum % 10;
			if (ge > 4) {
				return Constants.BIGORSMALL_BIG;
			} else {
				return Constants.BIGORSMALL_SMALL;
			}
		}
	}

	/**
	 * 计算龙虎
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String dragonOrTiger(String number) {
		if (StringUtils.isBlank(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] sgArr = number.split(",");
		if (Integer.valueOf(sgArr[0]) > Integer.valueOf(sgArr[6])) {
			return Constants.PLAYRESULT_DRAGON;
		} else {
			return Constants.PLAYRESULT_TIGER;
		}
	}

	/**
	 * 波色计算
	 * 
	 * @param number
	 * @return
	 */
	public static String getNumBose(String number,int type) {
		String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		if(type== CalculationEnum.LHCSIX.getCode() &&numberSize.equals(Constants.BIGORSMALL_FORTY_NINE)) {
			return Constants.BIGORSMALL_SAME;
		}
		int numberSum = Integer.parseInt(numberSize);
		if (RED.contains(numberSum)) {
			return Constants.BIGORSMALL_RED;
		} else if (BLUE.contains(numberSum)) {
			return Constants.BIGORSMALL_BLUE;
		} else {
			return Constants.BIGORSMALL_GREEN;
		}
	}
	
	
	  /**
     * 获取对应号码的五行
     *
     * @param number     号码
     * @param type 日期 yyyy-MM-dd
     * @return
     */
    public static String getNumWuXing(String number,int type) {
    	String numberSize = commonNumber(number, type);
		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
        //获取该日期的农历年份
		Date date = new Date();
		int num = Integer.parseInt(numberSize);
//        LunarCalendar calendar = new LunarCalendar(date);
//        int year = calendar.getYear();
		Integer year = null;
		try{
			year = CalendarUtil.solarToLunar(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYYMMDD));
		}catch (Exception e){
			logger.error("获取日历出错",e);
		}
        int index = (60 + ((year / 2) % 30) - (num / 2)) % 30;
        if (year % 2 == 1 && num % 2 == 0) {
            index += 1;
        }
        return WUXING.get(index);
    }

	/**
	 * 根据公历日期获取号码对应的生肖
	 *
	 * @param num     号码
	 * @param dateStr 日期yyyy-MM-dd
	 * @return
	 */
	public static String getShengXiao(int num, String dateStr) {
		// 获取该日期的年份生肖
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
			// 获取该日期的年份生肖
			Date date = DateUtils.dateStringToDate(dateStr);
//			LunarCalendar calendar = new LunarCalendar(date);
			Integer year = CalendarUtil.solarToLunar(dateStr.replace("-", ""));
//			if (calendar.getYear() != year) {
//				logger.error("获取农历年份不同:{}，{}", year, calendar.getYear());
//			}
//			if (calendar.getYear() != 2019) {
//				logger.error("获取农历年份不对:{},{}", year, calendar.getYear());
//			}
			String animalsYear = SHENGXIAO.get((year - 4) % 12);
			return animalsYear;
		} catch (Exception e) {
			logger.error("获取生肖出错", e);
			return Constants.DEFAULT_NULL;
		}
	}

	/**
	 * 公共获取号码
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public static String commonNumber(String number, Integer type) {

		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String numberSize = null;
		String[] balls = number.split(",");
		if (CalculationEnum.LHCZERO.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCZERO.getCode()];
		}
		if (CalculationEnum.LHCONE.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCONE.getCode()];
		}
		if (CalculationEnum.LHCTWO.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCTWO.getCode()];
		}
		if (CalculationEnum.LHCTHREE.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCTHREE.getCode()];
		}
		if (CalculationEnum.LHCFOUR.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCFOUR.getCode()];
		}
		if (CalculationEnum.LHCFIVE.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCFIVE.getCode()];
		}
		if (CalculationEnum.LHCSIX.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCSIX.getCode()];
		}
		if (CalculationEnum.LHCSEVEN.getCode() == type) {
			numberSize = balls[CalculationEnum.LHCSIX.getCode()];
		}

		if (StringUtils.isBlank(numberSize)) {
			return Constants.DEFAULT_NULL;
		}
		return numberSize;
	}

}
