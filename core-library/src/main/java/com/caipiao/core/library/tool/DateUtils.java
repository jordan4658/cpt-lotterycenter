package com.caipiao.core.library.tool;

import com.caipiao.core.library.enums.CaipiaoSumCountEnum;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
	/**
	 * 星期一
	 */
	public static final int MONDAY = 1;
	/**
	 * 星期二
	 */
	public static final int TUESDAY= 2;
	/**
	 * 星期三
	 */
	public static final int WEDNESDAY= 3;
	/**
	 * 星期四
	 */
	public static final int THURSDAY= 4;
	/**
	 * 星期五
	 */
	public static final int FRIDAY= 5;
	/**
	 * 星期六
	 */
	public static final int SATURDAY= 6;
	/**
	 * 星期天
	 */
	public static final int SUNDAY= 7;

	//日期格式模板
	public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";
	public static final String datePattern = "yyyy-MM-dd";
	public static final String timePattern = "HH:mm:ss";
	public static final String fullDatePattern = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMEOVER = "23:59:00";
	public static final String TIMEOVERHOUR = "23:59";
	public static final String HOURANDMINUTES = "HH:mm";
	public static final String TIMEZERO = "00:00:00";
	public static final String ZERO_TIME = " 00:00:00";
	public static final String XYFT_OPEN_TIME = "04:04:00";
	public static final String XYFT_START_TIME = "13:09:00";
	public static final String XYFT_MID_TIME = " 13:09:00";
	
	/**
	 * 获取下一期官方开奖时间
	 *
	 * @param dateTime 当前期官方开奖时间
	 * @return
	 */
	public static Date nextIssueTime(Date dateTime) {
		return DateUtils.getMinuteAfter(dateTime, 1);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 *            时间
	 * @param pattern
	 *            格式化字符串
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
	 * 将时间对象的日期部分格化化
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return formatDate(date, datePattern);
	}
	
	/**
	 * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 *
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE, *
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减。
	 *
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
	/**
	 * 给时间加上或减去指定毫秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 将时间对象的日期、时间部分格化化
	 * 
	 * @param date
	 * @return
	 */
	public static String getFullString(Date date) {
		return formatDate(date, fullDatePattern);
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
		return formatDate(date, timePattern);
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
	 * @param patterns
	 *            输入字符串的格式
	 * @param str
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 */
	public static Date parseDate(String str, String[] patterns) {
		for (String pattern : patterns) {
			try {
				SimpleDateFormat df = new SimpleDateFormat(pattern);
				return df.parse(str);
			} catch (Exception pe) {
			}
		}
		return null;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param pattern
	 *            输入字符串的格式
	 * @param str
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 */
	public static Date parseDate(String str, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(str);
		} catch (Exception pe) {
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
		return parseDate(str, datePattern);
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

	public static final String TIME_FORMAT = "HH:mm:ss:SS";
	public static final String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_SHORT_DATE_FORMAT_ZH = "yyyy年M月d日";
	public static final String DEFAULT_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SS";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_TIMESTAMP = "yyyyMMddHHmmss";
	public static final String JAVA_MIN_SHORT_DATE_STR = "1970-01-01";
	public static final String JAVA_MIN_LONG_DATE_STR = "1970-01-01 00:00:00:00";
	public static final String DEFAULT_PERIOD_FORMAT = "{0}天{1}小时{2}分钟";
	public static final String JAVA_MAX_SHORT_DATE_STR = "9999-12-31";
	public static final String DEFAULT_DATE_FOMAT = "yyyyMMdd";

	public static Date addDate(String datepart, int number, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (datepart.equals("yy"))
			cal.add(1, number);
		else if (datepart.equals("MM"))
			cal.add(2, number);
		else if (datepart.equals("dd"))
			cal.add(5, number);
		else if (datepart.equals("HH"))
			cal.add(11, number);
		else if (datepart.equals("mm"))
			cal.add(12, number);
		else if (datepart.equals("ss"))
			cal.add(13, number);
		else {
			throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值：" + datepart);
		}

		return cal.getTime();
	}

	public static boolean compareNow(String time1) {
		return compareTime(time1, currentStr(), "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean compareTime(String time1, String time2) {
		return compareTime(time1, time2, "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean compareTime(String time1, String time2, String dateFormat) {
		SimpleDateFormat t1 = new SimpleDateFormat(dateFormat);
		SimpleDateFormat t2 = new SimpleDateFormat(dateFormat);
		try {
			Date d1 = t1.parse(time1);
			Date d2 = t2.parse(time2);
			return d1.before(d2);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Date convert(String date, String format) {
		if ((date == null) || (date.equals("")))
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.convert():" + e.getMessage());
		}
	}

	public static String convert(Date date, String format) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date convert(String date) {
		int len = date.length();
		return convert(date, (len < 11) ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss");
	}

	public static Date convert(long time) {
		return new Date(time);
	}

	public static Date convert(Long time) {
		return new Date(time.longValue());
	}

	public static String convert(Date date) {
		return convert(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date toDate(Object o) {
		if (null == o)
			return null;

		if (o instanceof Date)
			return ((Date) o);

		if (o instanceof String)
			return convert((String) o);

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
		String result = MessageFormat.format("{0}天{1}小时{2}分钟", new Object[] { Long.valueOf(period / dayUnit),
				Long.valueOf(period % dayUnit / hourUnit), Long.valueOf(period % hourUnit / minUnit) });

		return result;
	}

	public static double dateDiff(String datepart, Date startdate, Date enddate) {
		if ((datepart == null) || ("".equals(datepart))) {
			String info = "DateUtil.dateDiff()方法非法参数值：" + datepart;
			throw new IllegalArgumentException(info);
		}

		double days = (enddate.getTime() - startdate.getTime()) / 86400000.0D;

		if (datepart.equals("yy")) {
			days /= 365.0D;
		} else if (datepart.equals("MM")) {
			days /= 30.0D;
		} else {
			if (datepart.equals("dd"))
				return days;

			String info = "DateUtil.dateDiff()方法非法参数值：" + datepart;
			throw new IllegalArgumentException(info);
		}
		return days;
	}
	
	/**
	 * 计算时间差
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long timeReduce(String time1,String time2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			Date t1=df.parse(time1);
			Date t2=df.parse(time2);
			return (t1.getTime()-t2.getTime())/1000l;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String currentStr() {
		return currentStr("yyyy-MM-dd HH:mm:ss");
	}

	public static String currentStr(String dateFormat) {
		return convert(new Date(), dateFormat);
	}

	public static long getTimeMillis(String dateStr) {
		return convert(dateStr, "yyyy-MM-dd HH:mm:ss").getTime();
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
		Long hour=time/3600;
		Long temp=time%3600;
		Long minute=temp/60;
		Long second=temp%60;
		return (hour>0?hour+"小时，":"")+(minute>0?minute+"分，":"")+(second+"秒");
	}
	
	public static int getDateInterval(String day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.convert(day));
		int d=calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(getLastMonthDay(DateUtils.convert(day)));
		int dd=calendar.get(Calendar.DAY_OF_MONTH);
		return dd-d;
	}
	
	public static int getDayOfMonth(String day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.convert(day));
		int d=calendar.get(Calendar.DAY_OF_MONTH);
		return d;
	}
	
	public static int getLocalMothDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getLastMonthDay(new Date()));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取时间后多少分钟的时间
	 * @param date 制定的时间
	 * @param count 后几分钟
	 * @return
	 */
	public static Date getMinuteAfter(Date date, Integer count){
		long time = date.getTime();
		long diff = count*60*1000;
		long nowTime = time+diff;
		return new Date(nowTime);
	}

	/**
	 * 获取时间后多少秒钟的时间
	 * @param date 制定的时间
	 * @param count 后几秒钟
	 * @return
	 */
	public static Date getSecondAfter(Date date, Integer count){
		long time = date.getTime();
		long diff = count*1000;
		long nowTime = time+diff;
		return new Date(nowTime);
	}

	/**
	 * 获取指定时间前后多少天的时间
	 * @param date 指定的时间
	 * @param count 前后多少天
	 * @return
	 */
	public static Date getDayAfter(Date date, Long count){
		long time = date.getTime();
		long diff = count*24*60*60*1000;
		long nowTime = time+diff;
		return new Date(nowTime);
	}

	/**
	 * 获取当天开始时间
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
	 * @param date 日期
	 * @return
	 */
	public static Date getDayBegin(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Objects.requireNonNull(DateUtils.parseDate(date, "yyyy-MM-dd")));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当天开始时间
	 * @param date 日期
	 * @return
	 */
	public static Date getDayEnd(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Objects.requireNonNull(DateUtils.parseDate(date, "yyyy-MM-dd")));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 给指定时间设置时分秒
	 * @param date 日期
	 * @param hour 时
	 * @param minute 分
	 * @param second 秒
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
	 * 计算两个日期相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);

		// 不同年
		if(year1 != year2) {
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}
			return timeDistance + (day2-day1) ;
		} else { // 同年
			return day2-day1;
		}
	}

	/**
	 * 按时间判断是星期几
	 * @param pTime 日期
	 * @return
	 * @throws Exception
	 */
	public static int dayForWeek(String pTime) throws Exception{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek=0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
		/**
		 * 获取本周一的日期
		 * @return
		 */
		public static Date getMondayOfThisWeek() {
			Calendar c = Calendar.getInstance();
			int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (day_of_week == 0)
			day_of_week = 7;
			c.add(Calendar.DATE, -day_of_week + 1);
			return c.getTime();
		}

		/**
		 * 获取本周末的日期
		 * @return
		 */
		public static Date getSundayOfThisWeek() {
			Calendar c = Calendar.getInstance();
			int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (day_of_week == 0)
			day_of_week = 7;
			c.add(Calendar.DATE, -day_of_week + 7);
			return c.getTime();
		}
		/**
		 * 获取上个月的第一天
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
		 * @return
		 */
		public static Date getLastOfLastMonth() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1); 
			calendar.add(Calendar.DATE, -1);
			return calendar.getTime();
		}
		
		/**
		 * 计算当前时间是周几,周日返回 7
		 * @return
	     */
		public static int getCurrentWeek() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if(w == 0) w = 7;
			return w;
		}

		/**
		 * 验证字符是否为有效时间
		 * @param str
		 * @return
	     */
		public static boolean isValidDateTime(String str) {
			if(StringUtils.isBlank(str)) return false;
			boolean convertSuccess = true;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
				format.setLenient(false);
				format.parse(str);
			} catch (ParseException e) {
				convertSuccess=false;
			}
			return convertSuccess;
		}
		
		/**
		 * 添加一年
		 * @param date
		 * @return
		 */
		public static Date addOneYears(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.YEAR,1);
			Date returnDate=cal.getTime();
			return returnDate;
		}
		
		/**
		 * 根据日期获取当年总天数
		 * @param date
		 * @return
		 */
		public static int getYearsDays(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getActualMaximum(Calendar.DAY_OF_YEAR );
		}
	
		public static void main(String[] args) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dd = new Date();
			System.out.println("kk:"+dd.getTime());
			
			long aa = 1553951520l * 1000l;
			
			Date bb = new Date(aa);
			
			System.out.println("ff:" + sdf.format(bb));


			Calendar startCal = Calendar.getInstance();
			startCal.setTime(DateUtils.parseDate("2019-04-22 00:05:00",DateUtils.fullDatePattern));
			startCal.set(Calendar.HOUR_OF_DAY,0);
			startCal.set(Calendar.MINUTE,0);
			startCal.set(Calendar.SECOND,0);

			long jiange = DateUtils.timeReduce("2019-04-22 00:05:00",DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
			System.out.println(jiange);
			int openCount = (int)(jiange/60) + 1;
			int noOpenCount = CaipiaoSumCountEnum.JSPKS.getSumCount() - openCount;
			System.out.println(noOpenCount);
		}
	
}
