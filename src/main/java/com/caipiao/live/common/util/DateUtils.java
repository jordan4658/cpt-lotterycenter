package com.caipiao.live.common.util;

import com.caipiao.live.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


/**
 * @author abu
 * @ClassName: DateUtils
 * @date 2013-6-24 上午8:57:16
 * @Description: 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    //星期一
    public static final int MONDAY = 1;
    //星期二
    public static final int TUESDAY = 2;
    //星期三
    public static final int WEDNESDAY = 3;
    // 星期四
    public static final int THURSDAY = 4;
    // 星期五
    public static final int FRIDAY = 5;
    // 星期六
    public static final int SATURDAY = 6;
    // 星期天
    public static final int SUNDAY = 7;

    // 日期格式模板
    public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HHMMSSSSS = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String FORMAT_YYYY_MM_DD_HHMM00 = "yyyy-MM-dd HH:mm:00";
    public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYYMMDD_HHMMSS_SSS = "yyyyMMdd-HHmmssSSS";
    public static final String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String DEFAULT_TIMESTAMP = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YEAR = "yyyy";

    public static final String FORMAT_DATE_YYYYMMDD = "yyyymmdd";
    public static final String FORMAT_DATE_HHMMSS = "HHmmss";

    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String HOURANDMINUTES = "HH:mm";
    public static final String TIME_FORMAT = "HH:mm:ss:SS";
    public static final String DEFAULT_SHORT_DATE_FORMAT_ZH = "yyyy年M月d日";
    public static final String DEFAULT_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SS";
    public static final String JAVA_MIN_SHORT_DATE_STR = "1970-01-01";
    public static final String JAVA_MIN_LONG_DATE_STR = "1970-01-01 00:00:00:00";
    public static final String DEFAULT_PERIOD_FORMAT = "{0}天{1}小时{2}分钟";
    public static final String JAVA_MAX_SHORT_DATE_STR = "9999-12-31";
    public static final String TIMEZERO = "00:00:00";
    public static final String TIMEZERO_BLANK = " 00:00:00";
    public static final String XYFT_OPEN_TIME = "04:04:00";
    public static final String XYFT_START_TIME = "13:09:00";
    public static final String XYFT_MID_TIME = " 13:09:00";
    public static final String TIMEOVER = "23:59:59";
    public static final String TIMEOVER_BLANK = " 23:59:59";
    public static final String TIMEOVER_BLANK_MINUTE = " 23:59:00";
    public static final String TIMEOVERHOUR = "23:59";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");

    /**
     * @param date   日期
     * @param years  年数
     * @param months 月数
     * @param days   天数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数，月数，天数之后的日期
     */
    public static Date afterDate(Date date, int years, int months, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        cal.add(Calendar.MONTH, months);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * @param date    日期
     * @param years   年数
     * @param months  月数
     * @param days    天数
     * @param hours   小时数
     * @param minutes 分钟数
     * @param seconds 秒数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数，月数，天数，小时数，分钟数，秒数之后的日期
     */
    public static Date afterDate(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        cal.add(Calendar.MONTH, months);
        cal.add(Calendar.DAY_OF_MONTH, days);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minutes);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     * @param hours 小时数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定 小时数 之后的日期
     */
    public static Date afterHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * @param date 日期
     * @param days 天数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定天数之后的日期
     */
    public static Date afterDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

//	/**
//	 * 将<code>datePattern<code>为格式的字符串解析为日期对象
//	 *
//	 * @param str
//	 * @return
//	 */
//	public static Date dateStringToDate(String str) {
//		try {
//			return parseDate(str, FORMAT_YYYY_MM_DD);
//		} catch (Exception e) {
//			logger.error("dateStringToDate occur error for str:{}, error:{}", str, e);
//			return null;
//		}
//	}

    /**
     * @param date
     * @param weeks
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 返回指定周数之后的日期
     */
    public static Date afterWeeks(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, weeks);
        return cal.getTime();
    }

    /**
     * @param date   日期
     * @param months 月数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定月数之后的日期
     */
    public static Date afterMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * @param date  日期
     * @param years 年数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数之后的日期
     */
    public static Date afterYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * @param date   日期
     * @param years  年数
     * @param months 月数
     * @param days   天数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数，月数，天数之前的日期
     */
    public static Date beforeDate(Date date, int years, int months, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -years);
        cal.add(Calendar.MONTH, -months);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        return cal.getTime();
    }

//	public static long getTimeMillis(String dateStr) {
//
//		if (StringUtils.isEmpty(dateStr)) {
//			return Constants.DEFAULT_LONG;
//		}
//		return convert(dateStr, FORMAT_YYYY_MM_DD_HHMMSS).getTime();
//
//	}

//	public static Date convert(String date, String format) {
//		if ((date == null) || (date.equals("")))
//			return null;
//
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		try {
//			return sdf.parse(date);
//		} catch (Exception e) {
//			logger.error("convert occur error for date:{}, format:{}, error:{}", date, format, e);
//			throw new RuntimeException("DateUtil.convert():" + e.getMessage());
//		}
//	}

    /**
     * @param date    日期
     * @param years   年数
     * @param months  月数
     * @param days    天数
     * @param hours   小时数
     * @param minutes 分钟数
     * @param seconds 秒数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数，月数，天数，小时数，分钟数，秒数之前的日期
     */
    public static Date beforeDate(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -years);
        cal.add(Calendar.MONTH, -months);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        cal.add(Calendar.HOUR_OF_DAY, -hours);
        cal.add(Calendar.MINUTE, -minutes);
        cal.add(Calendar.SECOND, -seconds);
        return cal.getTime();
    }

    /**
     * @param date 日期
     * @param days 天数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定天数之前的日期
     */
    public static Date beforeDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        return cal.getTime();
    }

    /**
     * @param date   日期
     * @param months 月数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定月数之前的日期
     */
    public static Date beforeMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -months);
        return cal.getTime();
    }

    /**
     * @param date
     * @param weeks
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 返回指定周数之前的日期
     */
    public static Date beforeWeeks(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -weeks);
        return cal.getTime();
    }

    /**
     * @param date  日期
     * @param years 年数
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回指定年数之前的日期
     */
    public static Date beforeYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -years);
        return cal.getTime();
    }

//	/**
//	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
//	 */
//	public static String formatDate(Date date, String pattern) {
//		String formatDate = null;
//		if (pattern != null) {
//			formatDate = DateFormatUtils.format(date, pattern);
//		} else {
//			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
//		}
//		return formatDate;
//	}

//	/**
//	 * 把字符串转换成指定格式的日期
//	 *
//	 * @param dateStr
//	 * @param pattern
//	 * @return
//	 * @throws ParseException
//	 */
//	public static Date parseDate(String dateStr, String pattern) throws ParseException {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//		Date date = simpleDateFormat.parse(dateStr);
//		return date;
//	}

    /**
     * 取当前时间字符串
     * <p>
     * 时间字符串格式为：年(4位)-月份(2位)-日期(2位) 小时(2位):分钟(2位):秒(2位)
     *
     * @return 时间字符串
     */
    public static String getCurrentDateString() {
        return getCurrentDateString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按格式取当前时间字符串
     *
     * @param formatString 格式字符串
     * @return
     */
    public static String getCurrentDateString(String formatString) {
        Date currentDate = new Date();
        return formatDate(currentDate, formatString);
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 取得指定日期的23点59分59秒
     *
     * @param dateStr 日期 年月
     * @return : 格式化为当天的最后一秒
     * @throws ParseException
     * @author : chenlong
     * @version : 1.00
     * @create time : 2013-3-18
     * @description : 取得指定日期的最后一秒
     */
    public static Date getDayLastSecond(String dateStr) throws ParseException {
        // 当日期字符串不为空或者""时，转换为Date类型
        if (dateStr != null || !"".equals(dateStr)) {
            Date date = parseDate("yyyy-MM-dd", dateStr);
            // 实例化Calendar类型
            Calendar cal = Calendar.getInstance();
            // 设置年月
            cal.setTime(date);
            // 设置时间为23时
            cal.set(Calendar.HOUR_OF_DAY, 23);
            // 设置时间为59分
            cal.set(Calendar.MINUTE, 59);
            // 设置时间为59秒
            cal.set(Calendar.SECOND, 59);
            // 设置时间为999毫秒
            cal.set(Calendar.MILLISECOND, 999);
            return cal.getTime();
        } else {
            return null;
        }
    }

    /**
     * 获取传入日期一天的结束时间 yyyy-MM-dd 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getDayLastSecond(Date date) {
        // 实例化Calendar类型
        Calendar cal = Calendar.getInstance();
        // 设置年月
        cal.setTime(date);
        // 设置时间为23时
        cal.set(Calendar.HOUR_OF_DAY, 23);
        // 设置时间为59分
        cal.set(Calendar.MINUTE, 59);
        // 设置时间为59秒
        cal.set(Calendar.SECOND, 59);
        // 设置时间为999毫秒
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 取得指定年月的第一天
     *
     * @param yearMonthStr 年月
     * @return : firstDay 第一天
     * @throws ParseException
     * @author : youyd
     * @version : 1.00
     * @create time : 2013-2-25 下午12:43:16
     * @description : 取得指定年月的第一天
     */
    public static Date getFirstDay(String yearMonthStr) throws ParseException {
        // 当日期字符串不为空或者""时，转换为Date类型
        if (yearMonthStr != null || !"".equals(yearMonthStr)) {
            Date yearMonth = parseDate(yearMonthStr, "yyyy-MM");
            // 实例化Calendar类型
            Calendar cal = Calendar.getInstance();
            // 设置年月
            cal.setTime(yearMonth);
            // 设置日期为该月第一天
            cal.set(Calendar.DATE, 1);
            // 返回指定年月的第一天
            return cal.getTime();
        } else {
            return null;
        }

    }

    /**
     * @param yearMonthStr
     * @return
     * @throws ParseException
     * @author abu
     * <p>
     * Description:<br>
     * 取得指定年月的第一天
     */
    public static Date getFirstDay(Date yearMonthStr) throws ParseException {
        // 当日期字符串不为空或者""时，转换为Date类型
        if (yearMonthStr != null) {
            Date yearMonth = parseDate(formatDate(yearMonthStr, "yyyy-MM"), "yyyy-MM");
            // 实例化Calendar类型
            Calendar cal = Calendar.getInstance();
            // 设置年月
            cal.setTime(yearMonth);
            // 设置日期为该月第一天
            cal.set(Calendar.DATE, 1);
            // 返回指定年月的第一天
            return cal.getTime();
        } else {
            return null;
        }

    }

    /**
     * 取得指定年月的最后一天
     *
     * @param yearMonthStr 年月
     * @return : lastDay 最后一天
     * @throws ParseException
     * @author : youyd
     * @version : 1.00
     * @create time : 2013-2-25 下午12:43:16
     * @description : 取得指定年月的最后一天
     */
    public static Date getLastDay(String yearMonthStr) throws ParseException {
        // 当日期字符串不为空或者""时，转换为Date类型
        if (yearMonthStr != null || !"".equals(yearMonthStr)) {
            Date yearMonth = parseDate(yearMonthStr, "yyyy-MM");
            // 实例化Calendar类型
            Calendar cal = Calendar.getInstance();
            // 设置年月
            cal.setTime(yearMonth);
            // 设置月份为下一月份
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            // 设置日期为下一月份第一天
            cal.set(Calendar.DATE, 1);
            // 设置时间为23时
            cal.set(Calendar.HOUR_OF_DAY, 23);
            // 设置时间为59分
            cal.set(Calendar.MINUTE, 59);
            // 设置时间为59秒
            cal.set(Calendar.SECOND, 59);
            // 设置时间为999毫秒
            cal.set(Calendar.MILLISECOND, 999);
            // 回滚一天 即上月份的最后一天
            cal.roll(Calendar.DATE, -1);
            // 返回指定年月的最后一天
            return cal.getTime();
        } else {
            return null;
        }

    }

    /**
     * @param yearMonthStr
     * @return
     * @throws ParseException
     * @author abu
     * <p>
     * Description:<br>
     * 取得指定年月的最后一天
     */
    public static Date getLastDay(Date yearMonthStr) throws ParseException {
        // 当日期字符串不为空或者""时，转换为Date类型
        if (yearMonthStr != null || !"".equals(yearMonthStr)) {
            Date yearMonth = parseDate(formatDate(yearMonthStr, "yyyy-MM"), "yyyy-MM");
            // 实例化Calendar类型
            Calendar cal = Calendar.getInstance();
            // 设置年月
            cal.setTime(yearMonth);
            // 设置月份为下一月份
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            // 设置日期为下一月份第一天
            cal.set(Calendar.DATE, 1);
            // 设置时间为23时
            cal.set(Calendar.HOUR_OF_DAY, 23);
            // 设置时间为59分
            cal.set(Calendar.MINUTE, 59);
            // 设置时间为59秒
            cal.set(Calendar.SECOND, 59);
            // 设置时间为999毫秒
            cal.set(Calendar.MILLISECOND, 999);
            // 回滚一天 即上月份的最后一天
            cal.roll(Calendar.DATE, -1);
            // 返回指定年月的最后一天
            return cal.getTime();
        } else {
            return null;
        }

    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * @param date1
     * @param date2
     * @return 返回两个日期间的月数
     */
    public static Integer getMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1)) {
                return 0;
            }
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH)) {
                flag = 1;
            }

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            } else {
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 获取传入日期的开始时间 yyyy-MM-dd 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getDayStartSecond(Date date) {
        // 实例化Calendar类型
        Calendar cal = Calendar.getInstance();
        // 设置年月
        cal.setTime(date);
        // 设置时间为0时
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // 设置时间为0分
        cal.set(Calendar.MINUTE, 0);
        // 设置时间为0秒
        cal.set(Calendar.SECOND, 0);
        // 设置时间为0毫秒
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * @param date1 日期1
     * @param date2 日期2
     * @return
     * @about version ：1.00
     * @auther : abu
     * @Description ：返回两个日期相差天数的绝对值
     */
    public static Integer intervalDay(Date date1, Date date2) {
        return Math.abs((int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)));
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 返回两个日期相差周数的绝对值
     */
    public static Integer intervalWeek(Date date1, Date date2) {
        return (int) Math.ceil(Math.abs((int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24))) / 7);
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @description 计算量日期中的月数差
     */
    public static Integer intervalMonth(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        int month12 = Math.abs(year1 - year2) * 12;
        if (year1 - year2 > 0) {
            month1 += month12;
        } else if (year2 - year1 > 0) {
            month2 += month12;
        }
        return Math.abs(month2 - month1);
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        return parseDate(str.toString(), parsePatterns);
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * @param date
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取当前时间所在年的周数
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @param year
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取当前时间所在年的最大周数
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /**
     * @param year
     * @param week
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取某年的第几周的开始日期
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * @param year
     * @param week
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取某年的第几周的结束日期
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * @param date
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取当前时间所在周的开始日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * @param date
     * @return
     * @author abu
     * <p>
     * Description:<br>
     * 获取当前时间所在周的结束日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * @param birthDay
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 由出生日期获得年龄
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 将世界标准时间转换为本地时间
     *
     * @param gmtDate
     * @return
     */
    public static Date convertGMT2Local(Date gmtDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(gmtDate);
        int zoneOffset = c.get(Calendar.ZONE_OFFSET);
        int dstOffset = c.get(Calendar.DST_OFFSET);
        c.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
        return c.getTime();
    }

    /**
     * 将世界标准时间转换为目标时区的本地时间
     *
     * @param gmtDate
     * @param id
     * @return
     */
    public static Date convertGMTToLocal(Date gmtDate, String id) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(gmtDate);
        calendar.setTimeZone(TimeZone.getTimeZone(id));
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, dstOffset + zoneOffset);
        return calendar.getTime();
    }

    /**
     * 将本地时间转换为世界标准时间
     *
     * @param date
     * @return
     */
    public static Date convertToGMT(Date date) {
        // Local Time Zone Calendar Instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(dstOffset + zoneOffset));
        return calendar.getTime();
    }

    /**
     * 获取下一期官方开奖时间
     *
     * @param dateTime 当前期官方开奖时间
     * @return
     */
    public static Date nextIssueTime(Date dateTime) {
        return getMinuteAfter(dateTime, 1);
    }

    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
            return df.format(date);
        }
        return "";
    }

    /**
     * 格式化时间
     *
     * @param date    时间
     * @param pattern 格式化字符串
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return "";
    }

    /**
     * @Title: getTodayStart
     * @Description: 获取当天的开始时间
     * @date 2019年7月18日下午2:39:39
     */
    public static Date getTodayStart() {
        String todayTime = formatDate(new Date(), FORMAT_YYYY_MM_DD);
        String todayStartTime = todayTime + " 00:00:00";
        Date todayStartData = parseDate(todayStartTime);
        return todayStartData;
    }

    /**
     * 将时间对象的日期部分格化化
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        return formatDate(date, FORMAT_YYYY_MM_DD);
    }

    /**
     * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
     *
     * @param date   要加减前的时间，如果不传，则为当前日期
     * @param field  时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE, *
     *               Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
     * @param amount 按指定时间域加减的时间数量，正数为加，负数为减。
     * @return 变动后的时间
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    /**
     * 给时间加上或减去指定毫秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMilliSeconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    public static Date addMilliSeconds(String date, int amount) {
        return addMilliSeconds(date, amount, null);
    }

    public static Date addMilliSeconds(String date, int amount, String pattern) {
        return addMilliSeconds(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addSeconds(String date, int amount) {
        return addSeconds(date, amount, null);
    }

    public static Date addSeconds(String date, int amount, String pattern) {
        return addSeconds(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addMinutes(String date, int amount) {
        return addMinutes(date, amount, null);
    }

    public static Date addMinutes(String date, int amount, String pattern) {
        return addMinutes(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addHours(String date, int amount) {
        return addHours(date, amount, null);
    }

    public static Date addHours(String date, int amount, String pattern) {
        return addHours(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addDays(String date, int amount) {
        return addDays(date, amount, null);
    }

    public static Date addDays(String date, int amount, String pattern) {
        return addDays(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addWeeks(String date, int amount) {
        return addWeeks(date, amount, null);
    }

    public static Date addWeeks(String date, int amount, String pattern) {
        return addWeeks(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addMonths(String date, int amount) {
        return addMonths(date, amount, null);
    }

    public static Date addMonths(String date, int amount, String pattern) {
        return addMonths(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addYears(String date, int amount) {
        return addYears(date, amount, null);
    }

    public static Date addYears(String date, int amount, String pattern) {
        return addYears(parseDate(date, getDefaultFormatPattern(date, pattern)), amount);
    }

    /**
     * 添加一年
     *
     * @param date
     * @return
     */
    public static Date addOneYears(Date date) {
        return addYears(date, 1);
    }

    /**
     * 将时间对象的日期、时间部分格化化
     *
     * @param date
     * @return
     */
    public static String getFullString(Date date) {
        return formatDate(date, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 将时间对象的日期、时间部分格化化 秒为0
     *
     * @param date
     * @return
     */
    public static String getFullStringZeroSecond(Date date) {
        return formatDate(date, FORMAT_YYYY_MM_DD_HHMM00);
    }

    public static String getFullsString(Date date) {
        return formatDate(date, DEFAULT_TIMESTAMP);
    }

    /**
     * 将时间对象的时间部分格化化
     *
     * @param date
     * @return
     */
    public static String getTimeString(Date date) {
        return formatDate(date, TIME_PATTERN);
    }

    /**
     * 将时间对象的时间部分格化化
     *
     * @param date
     * @return
     */
    public static String getTimeString(Date date, String pattern) {
        return formatDate(date, pattern);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param patterns 输入字符串的格式
     * @param str      一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     */
    public static Date parseDate(String str, String[] patterns) {
        for (String pattern : patterns) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(pattern);
                return df.parse(str);
            } catch (Exception e) {
                logger.error("parseDate occur error for str:{}, error:{}", str, e);
            }
        }
        return null;
    }

    public static Date parseDate(String str) {
        return parseDate(str, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param pattern 输入字符串的格式
     * @param str     一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     */
    public static Date parseDate(String str, String pattern) {
        if (null == str || "".equals(str.trim()) || "0".equals(str)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(str);
        } catch (Exception e) {
            logger.error("parseDate occur error for str:{}, error:{}", str, e);
            return null;
        }
    }

    /**
     * 将<code>datePattern<code>为格式的字符串解析为日期对象
     *
     * @param str
     * @return
     */
    public static Date dateStringToDate(String str) {
        try {
            return parseDate(str, FORMAT_YYYY_MM_DD);
        } catch (Exception e) {
            logger.error("dateStringToDate occur error for str:{}, error:{}", str, e);
            return null;
        }
    }

    /**
     * 将<code>datePattern<code>为格式的字符串解析为日期对象
     *
     * @param str
     * @return
     */
    public static Date dateStringToDate(String str, String pattern) {
        return parseDate(str, pattern);
    }

    /**
     * add by fa ,2008.12.11 将日期对象转换为“yyyyMMdd”String
     *
     * @param date
     * @return String
     */
    public static String dateToShortString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    public static Date addDate(String datepart, int number, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if ("yy".equals(datepart)) {
            cal.add(Calendar.YEAR, number);
        } else if ("MM".equals(datepart)) {
            cal.add(Calendar.MONTH, number);
        } else if ("dd".equals(datepart)) {
            cal.add(Calendar.DAY_OF_MONTH, number);
        } else if ("HH".equals(datepart)) {
            cal.add(Calendar.HOUR_OF_DAY, number);
        } else if ("mm".equals(datepart)) {
            cal.add(Calendar.MINUTE, number);
        } else if ("ss".equals(datepart)) {
            cal.add(Calendar.SECOND, number);
        } else {
            throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值：" + datepart);
        }

        return cal.getTime();
    }

    public static boolean compareNow(String time1) {
        return compareTime(time1, currentStr(), FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static boolean compareTime(String time1, String time2) {
        return compareTime(time1, time2, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static boolean compareTime(String time1, String time2, String dateFormat) {
        SimpleDateFormat t1 = new SimpleDateFormat(dateFormat);
        SimpleDateFormat t2 = new SimpleDateFormat(dateFormat);
        try {
            Date d1 = t1.parse(time1);
            Date d2 = t2.parse(time2);
            return d1.before(d2);
        } catch (Exception e) {
            logger.error("compareTime occur error for time1:{}, time2:{}, error:{}", time1, time2, e);
            throw new RuntimeException(e);
        }
    }

    public static Date convert(String date, String format) {
        if ((date == null) || ("".equals(date))) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            logger.error("convert occur error for date:{}, format:{}, error:{}", date, format, e);
            throw new RuntimeException("DateUtil.convert():" + e.getMessage());
        }
    }

    public static String convert(Date date, String format) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date convert(String date) {
        return convert(date, getDefaultFormatPattern(date));
    }

    public static String getDefaultFormatPattern(String dateString) {
        return getDefaultFormatPattern(dateString, null);
    }

    public static String getDefaultFormatPattern(String dateString, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = dateString.length() < 11 ? FORMAT_YYYY_MM_DD : FORMAT_YYYY_MM_DD_HHMMSS;
        }
        return pattern;
    }

    public static Date convert(long time) {
        return new Date(time);
    }

    public static Date convert(Long time) {
        return new Date(time.longValue());
    }

    public static String convert(Date date) {
        return convert(date, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static Date toDate(Object o) {
        if (null == o) {
            return null;
        }

        if (o instanceof Date) {
            return ((Date) o);
        }

        if (o instanceof String) {
            return convert((String) o);
        }

        if (o instanceof Long) {
            Long t = (Long) o;
            return new Date(t.longValue());
        }
        throw new RuntimeException("invalid time object:" + o);
    }

    public static String format(long period) {
        long dayUnit = 86400L;
        long hourUnit = 3600L;
        long minUnit = 60L;
        String result = MessageFormat.format(DEFAULT_PERIOD_FORMAT, new Object[]{Long.valueOf(period / dayUnit),
                Long.valueOf(period % dayUnit / hourUnit), Long.valueOf(period % hourUnit / minUnit)});
        return result;
    }

    public static double dateDiff(String datepart, Date startdate, Date enddate) {
        if ((datepart == null) || ("".equals(datepart))) {
            String info = "DateUtil.dateDiff()方法非法参数值：" + datepart;
            throw new IllegalArgumentException(info);
        }

        double days = (enddate.getTime() - startdate.getTime()) / 86400000.0D;

        if ("yy".equals(datepart)) {
            days /= 365.0D;
        } else if ("MM".equals(datepart)) {
            days /= 30.0D;
        } else {
            if ("dd".equals(datepart)) {
                return days;
            }

            String info = "DateUtil.dateDiff()方法非法参数值：" + datepart;
            throw new IllegalArgumentException(info);
        }
        return days;
    }

    /**
     * 计算时间差
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long timeReduce(String time1, String time2) {
        DateFormat df = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        try {
            Date t1 = df.parse(time1);
            Date t2 = df.parse(time2);
            return (t1.getTime() - t2.getTime()) / 1000l;
        } catch (ParseException e) {
            logger.error("parseDate occur error for time1:{}, time2:{} error:{}", time1, time2, e);
        }
        return 0;
    }

    public static String currentStr() {
        return currentStr(FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static String currentTimeStr() {
        return currentStr(FORMAT_YYYY_MM_DD_HHMMSSSSS);
    }

    public static String currentStr(String dateFormat) {
        return convert(new Date(), dateFormat);
    }

    public static long getTimeMillis(String dateStr) {

        if (StringUtils.isEmpty(dateStr)) {
            return Constants.DEFAULT_LONG;
        }
        return convert(dateStr, FORMAT_YYYY_MM_DD_HHMMSS).getTime();
    }

    public static Date getFirstMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        return calendar.getTime();
    }

    public static Date getFirstDayNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(2, 1);
        return calendar.getTime();
    }

    public static Date getFirstDayCurMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getFirstDayCurYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(6, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getFirstDayCurYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 1, 1);
        calendar.set(6, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getLastMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar.getTime();
    }

    public static String addDate(String datepart, int number, String dateStr) {
        Date dt = convert(dateStr);
        Date date = addDate(datepart, number, dt);
        return convert(date);
    }

    public static String formartTime(Long time) {
        Long hour = time / 3600;
        Long temp = time % 3600;
        Long minute = temp / 60;
        Long second = temp % 60;
        return (hour > 0 ? hour + "小时，" : "") + (minute > 0 ? minute + "分，" : "") + (second + "秒");
    }

    public static int getDateInterval(String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convert(day));
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(getLastMonthDay(convert(day)));
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return dd - d;
    }

    public static int getDayOfMonth(String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convert(day));
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        return d;
    }

    public static int getLocalMothDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getLastMonthDay(new Date()));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间后多少分钟的时间
     *
     * @param date  制定的时间
     * @param count 后几分钟
     * @return
     */
    public static Date getMinuteAfter(Date date, Integer count) {
        long time = date.getTime();
        long diff = count * 60 * 1000;
        long nowTime = time + diff;
        return new Date(nowTime);
    }

    /**
     * 获取时间后多少秒钟的时间
     *
     * @param date  制定的时间
     * @param count 后几秒钟
     * @return
     */
    public static Date getSecondAfter(Date date, Integer count) {
        long time = date.getTime();
        long diff = count * 1000;
        long nowTime = time + diff;
        return new Date(nowTime);
    }

    /**
     * 获取指定时间前后多少天的时间
     *
     * @param date  指定的时间
     * @param count 前后多少天
     * @return
     */
    public static Date getDayAfter(Date date, Long count) {
        long time = date.getTime();
        long diff = count * 24 * 60 * 60 * 1000;
        long nowTime = time + diff;
        return new Date(nowTime);
    }

    /**
     * 获取当天开始时间
     *
     * @param date 日期
     * @return
     */
    public static Date getDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天开始时间
     *
     * @param date 日期
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当天开始时间
     *
     * @param date 日期
     * @return
     */
    public static Date getDayBegin(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(parseDate(date, FORMAT_YYYYMMDD)));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    public static String getDayStart(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(parseDate(date, FORMAT_YYYYMMDD)));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return formatDate(calendar.getTime());
    }

    public static String getEndDay(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(parseDate(date, FORMAT_YYYYMMDD)));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取当天开始时间
     *
     * @param date 日期
     * @return
     */
    public static Date getDayEnd(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(parseDate(date, FORMAT_YYYYMMDD)));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 给指定时间设置时分秒
     *
     * @param date        日期
     * @param hour        时
     * @param minute      分
     * @param second      秒
     * @param millisecond 毫秒
     * @return
     */
    public static Date setTime(Date date, Integer hour, Integer minute, Integer second, Integer millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 相差多少秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long diffSeconds(Date date1, Date date2) {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long seconds1 = time1 / 1000;
        long seconds2 = time2 / 1000;
        return seconds1 - seconds2;
    }

    /**
     * 计算两个时间相差多少分钟
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long diffMinutes(Date date1, Date date2) {
        long diffSeconds = diffSeconds(date1, date2);
        return diffSeconds / 60;
    }

    /**
     * 计算两个时间相差多少小时
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long diffHours(Date date1, Date date2) {
        long diffMinutes = diffMinutes(date1, date2);
        return diffMinutes / 60;
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        // 不同年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { // 同年
            return day2 - day1;
        }
    }

    /**
     * 按时间判断是星期几
     *
     * @param pTime 日期
     * @return
     * @throws Exception
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDD);
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 获取本周一的日期
     *
     * @return
     */
    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 获取本周末的日期
     *
     * @return
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     * 获取上个月的第一天
     *
     * @return
     */
    public static Date getFirstOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月的最后一天
     *
     * @return
     */
    public static Date getLastOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取当月的第一天
     *
     * @return
     */
    public static Date getFirstOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当月的最后一天
     *
     * @return
     */
    public static Date getLastOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 计算当前时间是周几,周日返回 7
     *
     * @return
     */
    public static int getCurrentWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) {
            w = 7;
        }
        return w;
    }

    /**
     * 验证字符是否为有效时间
     *
     * @param str
     * @return
     */
    public static boolean isValidDateTime(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 根据日期获取当年总天数
     *
     * @param date
     * @return
     */
    public static int getYearsDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static String getTestLong() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 30);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        String dateStr = sdf.format(now.getTimeInMillis());
        return dateStr;
    }

    /**
     * @Title: pastWeek
     * @Description: 一周前时间
     * @author HANS
     * @date 2019年5月22日下午2:40:14
     */
    public static String pastWeek() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        Calendar calendar = Calendar.getInstance();
        // 过去七天
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date date = calendar.getTime();
        String day = format.format(date);
        return day;
    }

    /**
     * @Title: pastWeek
     * @Description: 半个月前时间
     * @author HANS
     * @date 2019年5月22日下午2:40:14
     */
    public static String pastHalfMonth() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        Calendar calendar = Calendar.getInstance();
        // 过去半个月
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -15);
        Date date = calendar.getTime();
        String day = format.format(date);
        return day;
    }

    /**
     * @Title: pastWeek
     * @Description: 半个月前时间
     * @author HANS
     * @date 2019年5月22日下午2:40:14
     */
    public static String pastHalfMonth(Date thisTime) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        Calendar calendar = Calendar.getInstance();
        // 过去半个月
        calendar.setTime(thisTime);
        calendar.add(Calendar.DATE, -15);
        Date date = calendar.getTime();
        String day = format.format(date);
        return day;
    }

    /**
     * @Title: pastMonth
     * @Description: 获取三月前时间
     * @author HANS
     * @date 2019年5月22日下午2:43:17
     */
    public static Date pastMonth() {
        Calendar calendar = Calendar.getInstance();
        // 过去一月
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -6);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * @Title: partYearDay
     * @Description: 获取3个月前日期
     * @author HANS
     * @date 2019年10月4日下午2:57:28
     */
    public static Date partYearDay(Date threeMonthAgo) {
        try {
            SimpleDateFormat dfe = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
            String currDay = dfe.format(threeMonthAgo);
            Date onlyDay = parseDate(currDay, FORMAT_YYYY_MM_DD);
            return onlyDay;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Title: pastMonth
     * @Description: 获取明天日期
     * @author HANS
     * @date 2019年5月22日下午2:43:17
     */
    public static Date tomorrow() {
        Calendar calendar = Calendar.getInstance();
        // 明天
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * @Title: partYearDay
     * @Description: 获取明天日期
     * @author HANS
     * @date 2019年10月4日下午2:57:28
     */
    public static Date tomorrowDay(Date tomorrow) {
        try {
            SimpleDateFormat dfe = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
            String currDay = dfe.format(tomorrow);
            Date onlyDay = parseDate(currDay, FORMAT_YYYY_MM_DD);
            return onlyDay;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Title: pastMonth
     * @Description: 获取指定时间的天数时间
     * @author HANS
     * @date 2019年5月22日下午2:43:17
     */
    public static Date getNumDayTime(Integer dayInt) {
        Calendar calendar = Calendar.getInstance();
        // 过去一月
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, dayInt);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * @Title: pastMonth
     * @Description: 获取一周前时间
     * @author HANS
     * @date 2019年5月22日下午2:43:17
     */
    public static Date pastOneHourAgo() {
        Calendar calendar = Calendar.getInstance();
        // 1小时前
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -3);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * @Title: pastYear
     * @Description: 获取一年前时间
     * @author HANS
     * @date 2019年5月22日下午2:43:51
     */
    public static String pastYear() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        Calendar calendar = Calendar.getInstance();
        // 过去一年
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        Date date = calendar.getTime();
        String year = format.format(date);
        return year;
    }

    public static String getCurrentDate() {
        String formatPattern_Short = FORMAT_YYYYMMDDHHMMSS;
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
        return format.format(new Date());
    }

    public static String getSeqString() {
        SimpleDateFormat fm = new SimpleDateFormat(DEFAULT_TIMESTAMP); // "yyyyMMdd G
        return fm.format(new Date());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date str2date(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            logger.error("str2date occur error.", e);
        }
        return date;
    }

    public static Date str2date(String str) {
        return str2date(str, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 获取当前时间，格式为 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTimeStr(String format) {
        format = org.apache.commons.lang3.StringUtils.isBlank(format) ? FORMAT_YYYY_MM_DD_HHMMSS : format;
        Date now = new Date();
        return date2Str(now, format);
    }

    public static String date2Str() {
        return date2Str(FORMAT_YYYY_MM_DD);
    }

    public static String date2Str(String format) {
        return date2Str(new Date(), format);
    }

    public static String date2Str(Date date) {
        return date2Str(date, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static String todayStartTimeString() {
        return date2Str() + " " + TIMEZERO;
    }

    public static String todayEndTimeString() {
        return date2Str() + " " + TIMEOVER;
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date todayStartTime() {
        return parseDate(date2Str() + " " + TIMEZERO, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Date todayEndTime() {
        return parseDate(date2Str() + " " + TIMEOVER, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 时间转换成 Date 类型
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if ((format == null) || "".equals(format)) {
            format = FORMAT_YYYY_MM_DD_HHMMSS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    /**
     * 获取批量付款预约时间
     *
     * @return
     */
    public static String getRevTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        String dateString = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS).format(cal.getTime());
        logger.info(dateString);
        return dateString;
    }

    /**
     * 时间比较
     *
     * @param date1
     * @param date2
     * @return DATE1>DATE2返回1，DATE1<DATE2返回-1,等于返回0
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("compareDate occur error for date1:{}, date2:{}, format:{}, error:{}", date1, date2, format,
                    e);
        }
        return 0;
    }

    /**
     * 把给定的时间减掉给定的分钟数
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date minusDateByMinute(Date date, int minute) {
        Date newDate = new Date(date.getTime() - (minute * 60 * 1000));
        return newDate;
    }

    /**
     * 2天前凌晨0点
     *
     * @return
     */
    public static String twoDayAgoBeginStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime());
    }

    /**
     * 当天凌晨0点
     *
     * @return
     */
    public static String dayBeginStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取前一天凌晨
     *
     * @return
     */
    public static String dayBeforeStratStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime());
    }


    /**
     * 当天凌晨0点 （美东）
     *
     * @return
     */
    public static String timeBeginStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, -12);
        return formatDate(calendar.getTime());

    }


    /**
     * 获取前一天 23:59:59
     *
     * @return
     */
    public static String timeBeforeEndStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return formatDate(calendar.getTime());
    }


    /**
     * 当天23:59:59
     *
     * @return
     */
    public static String dayEndStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return formatDate(calendar.getTime());
    }

    /**
     * 当天23:59:59 （美东）
     *
     * @return
     */
    public static String timeEndStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.HOUR_OF_DAY, -12);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取昨天date
     *
     * @return
     */
    public static LocalDate getYesterday() {
        return LocalDate.now().plusDays(-1);
    }

    /**
     * 10位时间戳 转 时间
     *
     * @param timeStamp
     * @return
     */
    public static Date timeMillisAndDate(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        String d = format.format(timeStamp * 1000);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            logger.error("timeMillisAndDate occur error for timeStamp:{},  error:{}", timeStamp, e);
        }
        return date;
    }

    /**
     * 取某月第一天
     */
    public static LocalDate firstDayOfThisMonth(LocalDate today) {
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 取某月第N天
     */
    public static LocalDate dayOfThisMonth(LocalDate today, int n) {
        return today.withDayOfMonth(n);
    }

    /**
     * 取某月最后一天
     */
    public static LocalDate lastDayOfThisMonth(LocalDate today) {
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 计算当前时间距离24点还有多少毫秒
     *
     * @return
     */
    public static long timeDifference() {
        long currentTimeMillis = System.currentTimeMillis();
        String endStr = dayEndStr();
        LocalDateTime endDate = LocalDateTime.parse(endStr, timeFormatter);
        long endTimeMillis = endDate.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return endTimeMillis - currentTimeMillis;
    }

    /*2019/12/05
     * localdate,转 yyyy-mm-dd字符串
     * */
    public static String getLocalDateToYyyyMmDd(LocalDate Date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = formatter.format(Date);
        return dateStr;
    }

    /*2019/12/05
     * localdatetime,转 yyyy-mm-dd HH:mm:ss字符串
     * */
    public static String getLocalDateTimeToYyyyMmDdHhMmSs(LocalDateTime Date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = Date.format(formatter);
        return format;
    }

    /*2019/12/05
     * localdatetime,转 Date 类型
     * */
    public static Date getLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /*2019/12/05
     * localdate类型,转 Date 类型
     * */
    public static Date getLocalDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /* 2019/12/17
     * LocalDateTime 类型的当天开始时间
     * */
    public static LocalDateTime getLocalDateTimeStartTime() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return todayStart;
    }


    /**
     * @Title: getCptTodayStart
     * @Description: 获取当天的开始时间
     * @date 12:00:00
     */
    public static Date getCptTodayStart() {
        String todayTime = formatDate(new Date(), FORMAT_YYYY_MM_DD);
        String todayStartTime = todayTime + " 00:00:00";
        Date todayStartData = parseDate(todayStartTime);
        return todayStartData;
    }


    /**
     * @Title: getCptTodayEnd
     * @Description: 获取当天的结束时间
     * @date 20:00:00
     */
    public static Date getCptTodayEnd() {
        String todayTime = formatDate(new Date(), FORMAT_YYYY_MM_DD);
        String todayStartTime = todayTime + " 23:59:59";
        Date todayStartData = parseDate(todayStartTime);
        return todayStartData;
    }


    /**
     * @Title: getCptTodayStart
     * @Description: 获取当天的开始时间
     * @date 00:00:00
     */
    public static Date getHkTodayStart() {
        String todayTime = formatDate(new Date(), FORMAT_YYYY_MM_DD);
        String todayStartTime = todayTime + " 00:00:00";
        Date todayStartData = parseDate(todayStartTime);
        return todayStartData;
    }


    /**
     * @Title: getCptTodayEnd
     * @Description: 获取当天的结束时间
     * @date 23:59:59
     */
    public static Date getHkTodayEnd() {
        String todayTime = formatDate(new Date(), FORMAT_YYYY_MM_DD);
        String todayStartTime = todayTime + " 23:59:59";
        Date todayStartData = parseDate(todayStartTime);
        return todayStartData;
    }

    /**
     * 获取前一周凌晨
     *
     * @return
     */
    public static String weekBeforeStratStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取前一月凌晨
     *
     * @return
     */
    public static String monthBeforeStratStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime());
    }


    //日期比较操作相关方法
    public static boolean isEquals(String dateStr1, String dateStr2, String format) {
        return isEquals(parseDate(dateStr1, format), parseDate(dateStr2, format));
    }

    public static boolean isEquals(Date date1, Date date2) {
        checkCompareDate(date1, date2);
        return date1.equals(date2);
    }

    public static boolean isGreatThan(String dateStr1, String dateStr2, String format) {
        return isGreatThan(parseDate(dateStr1, format), parseDate(dateStr2, format));
    }

    public static boolean isGreatThan(Date date1, Date date2) {
        checkCompareDate(date1, date2);
        return date1.after(date2);
    }

    public static boolean isGreatThanOrEquals(String dateStr1, String dateStr2, String format) {
        return isGreatThanOrEquals(parseDate(dateStr1, format), parseDate(dateStr2, format));
    }

    public static boolean isGreatThanOrEquals(Date date1, Date date2) {
        checkCompareDate(date1, date2);
        return date1.after(date2) || date1.equals(date2);
    }

    public static boolean isLessThan(String dateStr1, String dateStr2, String format) {
        return isLessThan(parseDate(dateStr1, format), parseDate(dateStr2, format));
    }

    public static boolean isLessThan(Date date1, Date date2) {
        checkCompareDate(date1, date2);
        return date1.before(date2);
    }

    public static boolean isLessThanOrEquals(String dateStr1, String dateStr2, String format) {
        return isLessThanOrEquals(parseDate(dateStr1, format), parseDate(dateStr2, format));
    }

    public static boolean isLessThanOrEquals(Date date1, Date date2) {
        checkCompareDate(date1, date2);
        return date1.before(date2) || date1.equals(date2);
    }

    private static void checkCompareDate(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new NullPointerException("参数为空");
        }
    }

    public static String dateString(Date date) {
        String str = formatDate(date, FORMAT_YYYY_MM_DD_HHMMSS);
        return str.split(" ")[0];
    }

    public static String timeString(Date date) {
        String str = formatDate(date, FORMAT_YYYY_MM_DD_HHMMSS);
        return str.split(" ")[1];
    }

    public static String dateTimeString(Date date) {
        return formatDate(date, FORMAT_YYYY_MM_DD_HHMMSS);
    }

    public static void main(String[] args) {
        String gmtDateString = "2020-03-25 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date gmtDate = sdf2.parse(gmtDateString);
            Date localDate = convertGMT2Local(gmtDate);
            System.out.println(sdf2.format(localDate));
//	            for (String id : TimeZone.getAvailableIDs()) {
//	                System.out.println(id);
//	            }
            localDate = convertGMTToLocal(gmtDate, "Asia/Hong_Kong");
            System.out.println(sdf2.format(localDate));
            gmtDate = convertToGMT(localDate);
            System.out.println(sdf.format(gmtDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个时间所在月份的最大天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //获取当月第一天 最后一天
    public static String getFirstDayString() {
        Date firstDayCurMonth = getFirstOfThisMonth();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(firstDayCurMonth);
        return format1;

    }

    //获取当月第一天 最后一天
    public static String getLastDayString() {
        Date lastMonthDay = getLastOfThisMonth();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(lastMonthDay);
        return format1;

    }

    public static String getEndOfDayString(String str) {
        return str + TIMEOVER_BLANK;
    }

    public static String getStartOfDayString(String str) {
        return str + TIMEZERO_BLANK;
    }
}
