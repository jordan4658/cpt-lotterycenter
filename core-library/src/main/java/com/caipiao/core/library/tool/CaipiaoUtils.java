package com.caipiao.core.library.tool;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.FourtyEightEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/** 
 * @ClassName: CaipiaoUtils 
 * @Description: 彩票相关计算
 * @author: admin
 * @date: 2019年4月21日 下午2:13:01  
 */
public class CaipiaoUtils {
	
	/** 
	* @Title: getAllIsuueNumber 
	* @Description: 组织返回前端开奖格式
	* @return String
	* @author admin
	* @date 2019年4月21日下午2:18:38
	*/ 
	public static String getAllIsuueNumber(Integer wan,Integer qian,Integer bai,Integer shi,Integer ge) {
		StringBuffer allNumber = new StringBuffer();
		String allNumberString = allNumber.append(wan).append(",").append(qian).append(",").append(bai).append(",").append(shi).append(",")
				.append(ge).toString();
		return allNumberString;
	}
	
	/** 
	* @Title: getAllIsuueSum 
	* @Description: 开奖号码合值
	* @return Integer
	* @author admin
	* @date 2019年4月21日下午2:22:37
	*/ 
	public static Integer getAllIsuueSum(Integer wan, Integer qian, Integer bai, Integer shi, Integer ge) {
		Integer sumInteger = Constants.DEFAULT_INTEGER;

		if (wan != null) {
			sumInteger += wan;
		}

		if (qian != null) {
			sumInteger += qian;
		}

		if (bai != null) {
			sumInteger += bai;
		}
		
		if (shi != null) {
			sumInteger += shi;
		}

		if (ge != null) {
			sumInteger += ge;
		}
		return sumInteger;
	}
	
	/** 
	* @Title: getNextIdealTime 
	* @Description: 获取下期开奖时间
	* @return String
	* @author admin
	* @date 2019年4月21日下午3:36:50
	*/ 
	public static Date nextIssueTime(Date lastTime) {
        Date dateTime;
        String date = DateUtils.formatDate(lastTime, "yyyy-MM-dd");
        String time = DateUtils.formatDate(lastTime, "HH:mm:ss");
        if ("02:00:00".equals(time)) {
            dateTime = DateUtils.parseDate(date + " 10:20:00", DateUtils.fullDatePattern);
        } else {
            dateTime = DateUtils.getMinuteAfter(lastTime, 20);
        }
        return dateTime;
    }
	
	
	/** 
	* @Title: mathNextIssueTimeForNow 
	* @Description: 计算当前时间的下期开奖时间,  (20分钟一次开奖)
	* @return String
	* @author admin
	* @date 2019年4月21日下午6:06:22
	*/ 
	public static String mathNextIssueTimeForNow() {
		Date thisTime = new Date();		
		String thisDateYearAndTime = DateUtils.formatDate(thisTime, DateUtils.fullDatePattern);
		String thisDateYear = DateUtils.formatDate(thisTime, DateUtils.datePattern);
		// 是否大于2点
		String startTimeString = thisDateYear + " 02:00:00";
		long startFlag = DateUtils.timeReduce(thisDateYearAndTime, startTimeString);
		// 是否小于10：20
		String endTimeString = thisDateYear + " 10:20:00";
		long endFlag = DateUtils.timeReduce(endTimeString, thisDateYearAndTime);
		
		// 02:00:00  到  10:20:00 之前的时间，下期时间都是 当日10:20:00
		if(startFlag > 0 && endFlag > 0) {
			String nextIssueTime = thisDateYear + " 10:20:00";
			return nextIssueTime;
		}
		// 取20分钟后时间
		//Date dateTime = DateUtils.getMinuteAfter(thisTime, 20);
		String thisDatetime = DateUtils.formatDate(thisTime, DateUtils.timePattern);
		String hourString = thisDatetime.substring(0, 2);//时
		String minString = thisDatetime.substring(3, 5);//分
		String secondString = thisDatetime.substring(6, 8);//秒
		// 按照数值类型比较
		Integer hour = Integer.valueOf(hourString);
		Integer minuter = Integer.valueOf(minString);
		Integer second = Integer.valueOf(secondString);
		
		if(second > 57) {
			minuter++;
		}
		
		if(minuter >= 0 && minuter < 20) {
			String nextIssueTime = thisDateYear + " " + hourString + ":20:00";
			return nextIssueTime;
		} 
		
		if(minuter >= 20 && minuter < 40) {
			String nextIssueTime = thisDateYear + " " + hourString + ":40:00";
			return nextIssueTime;
		}

		if(minuter >= 40 && minuter <= 59) {
			hour++;
			String nextIssueTime = thisDateYear + " " + hour + ":00:00";
			return nextIssueTime;
		}
		return null;
	}
	
	/** 
	* @Title: getNextIssueNumber 
	* @Description: 获取下期期号 ,  (20分钟一次开奖)
	* @return String
	* @author admin
	* @date 2019年4月21日下午3:28:16
	*/ 
	public static String getNextIssueNumber(String nextIssueTime) {
		Date thisDate = DateUtils.parseDate(nextIssueTime, DateUtils.fullDatePattern);
		String thisDateYear = DateUtils.formatDate(thisDate, DateUtils.DEFAULT_DATE_FOMAT);
		// 获取时分秒
		String hourString = nextIssueTime.substring(0, 2);//时
		String minString = nextIssueTime.substring(3, 5);//分
		StringBuffer issueKeyBuffer = new StringBuffer();
		issueKeyBuffer.append(hourString).append(minString);
		// 查找出今日顺序号
		String issueId = FourtyEightEnum.getIssueId(issueKeyBuffer.toString());
		// 返回下期期号
		StringBuffer issueNumberBuffer = new StringBuffer();
		issueNumberBuffer.append(thisDateYear).append(issueId);
		return issueNumberBuffer.toString();
	}

	/**
	 * @Title: getNocommaNumber
	 * @Description: 去掉开奖号码中的逗号
	 * @author HANS
	 * @date 2019年7月20日上午11:22:35
	 */
	public static String getNocommaNumber(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String newNumber = number.replace(",", "");
		return newNumber;
	}

	/**
	 * @Title: getNumberTotal
	 * @Description: 号码单个值相加合值
	 * @author HANS
	 * @date 2019年7月20日上午11:33:33
	 */
	public static Integer getNumberTotal(String number) {
		Integer total = Constants.DEFAULT_INTEGER;
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_INTEGER;
		}
		String[] sigleString = number.split(",");

		if(null == sigleString || sigleString.length < 1) {
			return Constants.DEFAULT_INTEGER;
		}

		for (String sigleNumber : sigleString) {
			int idint = Integer.parseInt(sigleNumber);
			total += idint;
		}
		return total;
	}


	
}
