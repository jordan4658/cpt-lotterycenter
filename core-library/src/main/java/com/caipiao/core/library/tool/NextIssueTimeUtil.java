package com.caipiao.core.library.tool;

import java.util.Calendar;
import java.util.Date;

public class NextIssueTimeUtil {

    /**
     * 获取PC蛋蛋下一期开奖时间戳（秒值）
     * @return
     */
    public static Long getNextIssueTimePCDD() {
        Calendar calendar = Calendar.getInstance();
        // 获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 23 && minute >= 55) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 5);
        } else if (hour < 9) {
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 5);
        } else {
            int diff = 5 - (minute % 5);
            calendar.add(Calendar.MINUTE, diff);
        }

        // 将 “秒” 和 “毫秒” 设置为 0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 获取PC蛋蛋下一期的期号
     * @return
     */
    public static int nextIssuePcegg(String time, String issue) {
        long sgTime = TimeHelper.str2time(time, false);
        long nextIssueTime = getNextIssueTimePCDD();
        long count = (nextIssueTime - sgTime) / 60;
        int nextIssue = Integer.valueOf(issue) + 1;
        if (count > 5) {
            String date = TimeHelper.date("yyyy-MM-dd");
            String oneIssueTime = date + " 09:05:00"; // 获取今天第一期开奖时间
            long oneIssue = TimeHelper.str2time(oneIssueTime, true);
            long nowTime = System.currentTimeMillis();
            if (nowTime >= oneIssue) {
                nextIssue++;
            }
        }
        return nextIssue;
    }

    /**
     * 获取新疆时时彩下一期的开奖时间的毫秒值
     * @return
     */
    public static long nextIssueTimeXjssc() {
        long result;
        Calendar calendar = Calendar.getInstance();
        //获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);
        //获取秒
        int second = calendar.get(Calendar.SECOND);
        if (hour >= 10 || hour < 2) {
            // 十分钟一期
            int mm = 20 - (minute % 20);
            calendar.add(Calendar.MINUTE, mm);

        } else {
            //下一期在早上十点十分
            calendar.set(Calendar.HOUR, 10);
            calendar.set(Calendar.MINUTE, 20);
        }
        calendar.set(Calendar.SECOND, 0);
        result = calendar.getTimeInMillis();
        return result;
    }

    /**
     * 获取新疆时时彩下一期的期号
     * @return
     */
    public static String nextIssueXjssc(String time, String issue) {
        long sgTime = TimeHelper.str2time(time, true);
        long nextIssueTime = nextIssueTimeXjssc();
        String date = TimeHelper.date("yyyy-MM-dd HH:mm:ss", nextIssueTime);
        String timeStr = date.substring(11);
        if ("10:10:00".equals(timeStr)) {
            String today = TimeHelper.date("yyyyMMdd");
            return today + "01";
        } else if ("10:20:00".equals(timeStr)) {
            String today = TimeHelper.date("yyyyMMdd");
            return today + "02";
        }
        long count = (nextIssueTime - sgTime) / 1000 / 60;
        long nextIssue = Long.valueOf(issue) + 1;
        if (count > 10) {
            nextIssue++;
        }
        return nextIssue + "";
    }
    
    
    
    /**
     * 根据上期期号获取下期期号
     *
     * @param issue 上期期号
     * @return
     */
    public  static String getNextIssueXjssc(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("48".equals(num)||"96".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "01";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }
    
    /**
     * 根据上期期号获取下期期号
     *
     * @param issue 上期期号
     * @return
     */
    public static String getNextIssueCqssc(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("059".equals(num)||"120".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }
    
    public static String getNextIssueJgssc(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("059".equals(num)||"120".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }    
    
    public static String getNextIssueTjssc(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("42".equals(num)||"120".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }
    
    

    /**
     * 获取腾讯分分彩下一期的开奖时间的毫秒值
     * @return
     */
    public static long nextIssueTimeTxffc() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取腾讯分分彩下一期的期号
     * @return
     */
    public static String nextIssueTxffc() {
        long nextIssueTime = nextIssueTimeTxffc();
        String date = TimeHelper.date("yyyy-MM-dd HH:mm:ss", nextIssueTime);
        Calendar calendar = Calendar.getInstance();
        //获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);
        String nextIssue;
        if (hour == 23 && minute == 59) {
            String yyyyMMdd = TimeHelper.date("yyyyMMdd", System.currentTimeMillis() + 60 * 1000L);
            nextIssue = yyyyMMdd + "-0000";
        } else {
            String yyyyMMdd = TimeHelper.date("yyyyMMdd");
            int issue = hour * 60 + minute + 1;
            if (issue < 10) {
                nextIssue = yyyyMMdd + "-000" + issue;
            } else if (issue < 100) {
                nextIssue = yyyyMMdd + "-00" + issue;
            } else if (issue < 1000) {
                nextIssue = yyyyMMdd + "-0" + issue;
            } else {
                nextIssue = yyyyMMdd + "-" + issue;
            }
        }
        return nextIssue;
    }

    /**
     * 获取幸运飞艇下一期的开奖时间的毫秒值
     * @return
     */
    public static long nextIssueTimeXyft() {
        String today = TimeHelper.date("yyyy-MM-dd");
        String startDate = today + " 13:09:30";
        String endDate = today + " 04:04:30";
        long firstIssue = TimeHelper.str2time(startDate, true); //当天第一期开奖时间的毫秒值
        long endIssue = TimeHelper.str2time(endDate, true); //上一天最后一期开奖时间的毫秒值
        long now = System.currentTimeMillis();
        if (now >= endIssue && now < firstIssue) {
            return firstIssue;
        } else if (now >= firstIssue) {
            long issue = (now - firstIssue) / (5 * 60 * 1000);
            return firstIssue + ((5 * 60 * 1000) * (issue + 1));
        } else {
            long issue = (endIssue - now) / (5 * 60 * 1000);
            return endIssue - ((5 * 60 * 1000) * (issue));
        }

    }

    /**
     * 获取幸运飞艇下一期的期号
     * @return
     */
    public static String nextIssueXyft() {
        String today = TimeHelper.date("yyyy-MM-dd");
        String startDate = today + " 13:09:30";
        String endDate = today + " 04:04:30";
        long firstIssue = TimeHelper.str2time(startDate, true); //当天第一期开奖时间的毫秒值
        long endIssue = TimeHelper.str2time(endDate, true); //上一天最后一期开奖时间的毫秒值
        long now = System.currentTimeMillis();
        String todayStr = TimeHelper.date("yyyyMMdd");
        if (now >= endIssue && now < firstIssue) {
            return todayStr + "001";
        } else if (now >= firstIssue) {
            long issue = (now - firstIssue) / (5 * 60 * 1000) + 2;
            if (issue < 10) {
                return todayStr + "00" + issue;
            } else if (issue < 100) {
                return todayStr + "0" + issue;
            } else {
                return todayStr + issue;
            }
        } else {
            // 获取昨天的日期字符串
            long yesterday = endIssue - (5 * 60 * 60 * 1000);
            String yesterdayStr = TimeHelper.date("yyyyMMdd", yesterday);
            long issue = 180 - (endIssue - now) / (5 * 60 * 1000);
            return yesterdayStr + issue;
        }
    }

    /**
     * 获取重庆时时彩下一期的开奖时间的毫秒值
     * @return
     */
    public static long nextIssueTimeCqssc2() {
        long result;
        Calendar calendar = Calendar.getInstance();
        //获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);

        if (hour >= 23) {
            // 五分钟一期
            int mm = 5 - (minute % 5);
            calendar.add(Calendar.MINUTE, mm);
        } else if (hour >= 10) {
            // 十分钟一期
            int mm = 10 - (minute % 10);
            calendar.add(Calendar.MINUTE, mm);
        } else if (hour >= 2) {
            // 下一期在早上十点
            calendar.set(Calendar.HOUR, 10);
            calendar.set(Calendar.MINUTE, 0);
        } else {
            if (hour == 1 && minute >= 55) {
                // 下一期在早上十点
                calendar.set(Calendar.HOUR, 10);
                calendar.set(Calendar.MINUTE, 0);
            } else {
                // 五分钟一期
                int mm = 5 - (minute % 5);
                calendar.add(Calendar.MINUTE, mm);
            }

        }
        calendar.set(Calendar.SECOND, 0);
        result = calendar.getTimeInMillis();
        return result;
    }
    
    public static long nextIssueTimeCqssc(){
        String today = TimeHelper.date("yyyy-MM-dd");

        
        long time0 = TimeHelper.str2time(today + " 00:30:00", true); // 当天0点整时刻（毫秒值 ）
        long time3 = TimeHelper.str2time(today + " 03:10:00", true); // 当天1点55分时刻（毫秒值 ）
        long time7 = TimeHelper.str2time(today + " 07:30:00", true); // 当天10点整时刻（毫秒值 ）
        long time23 = TimeHelper.str2time(today + " 23:50:00", true); // 当天22点整时刻（毫秒值 ）
        
        
        long calculation_begin = TimeHelper.str2time(today + " 00:00:00", true); // 当天0点整时刻（毫秒值 ）
        
        long calculation_end = TimeHelper.str2time(today + " 00:00:00", true); // 当天0点整时刻（毫秒值 ）
        calculation_end+=(24 * 60 * 60 * 1000);
        
        
        

        // 获取当前时刻毫秒值
        long now = System.currentTimeMillis();

        // 期号的日期部分
        String issueStr = TimeHelper.date("yyyyMMdd");

        // 通过时间计算出每期期号
        long issueNum;
        if (now < time0) {
       	 	return time0;
        }else if (now >= time0 && now < time3) {
            return  time3;
        } else if (now >= time3 && now < time7) {
            return time7;
        } else if (now >= time7 && now < time23) {
        	return time23;
        } else 
//        	if (now >= time23)
        	{//如果是23:50:00之后要多加半小时
       	 	return (calculation_end+(30 * 60 * 1000)-now);
        }
        
        
        
//        if (now < time0) {
//       	 	return (time0-now);
//        }else if (now >= time0 && now < time3) {
//            return  (time3-now);
//        } else if (now >= time3 && now < time7) {
//            return (time7-now);
//        } else if (now >= time7 && now < time23) {
//        	return (time23-now);
//        } else 
////        	if (now >= time23)
//        	{//如果是23:50:00之后要多加半小时
//       	 	return (calculation_end+(30 * 60 * 1000)-now);
//        }
    }

    /**
     * 获取重庆时时彩下一期的期号
     * @return
     */
    public static String nextIssueCqssc() {
        String today = TimeHelper.date("yyyy-MM-dd");

        // 获取相应的时间点毫秒值
//        long time0 = TimeHelper.str2time(today + " 00:00:00", true); // 当天0点整时刻（毫秒值 ）
//        long time1 = TimeHelper.str2time(today + " 01:55:00", true); // 当天1点55分时刻（毫秒值 ）
//        long time10 = TimeHelper.str2time(today + " 10:00:00", true); // 当天10点整时刻（毫秒值 ）
//        long time22 = TimeHelper.str2time(today + " 22:00:00", true); // 当天22点整时刻（毫秒值 ）
        
        
        
        long time0 = TimeHelper.str2time(today + " 00:30:00", true); // 当天0点整时刻（毫秒值 ）
        long time3 = TimeHelper.str2time(today + " 03:10:00", true); // 当天1点55分时刻（毫秒值 ）
        long time7 = TimeHelper.str2time(today + " 07:30:00", true); // 当天10点整时刻（毫秒值 ）
        long time23 = TimeHelper.str2time(today + " 23:50:00", true); // 当天22点整时刻（毫秒值 ）

        // 获取当前时刻毫秒值
        long now = System.currentTimeMillis();

        // 期号的日期部分
        String issueStr = TimeHelper.date("yyyyMMdd");

        // 通过时间计算出每期期号
        long issueNum;
        if (now >= time0 && now < time3) {
            issueNum = (now - time0) / (20 * 60 * 1000) + 1;
            return issueNum < 10 ? issueStr + "00" + issueNum : issueStr + "0" + issueNum;
        } else if (now >= time3 && now < time7) {
            return issueStr + "9";
        } else if (now >= time7 && now < time23) {
            issueNum = (now - time7) / (20 * 60 * 1000) + 10;
            return issueStr + "0" + issueNum;
        } else {//第二天的第一期
        	 String prefix = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), 1L), "yyyyMMdd");
        	 return prefix + "001";
        }
    }

    /**
     * 获取下一期开奖时间戳（秒值）
     * @param id 彩种ID
     * @return
     */
    public static Long getNextIssueTime(Integer id) {
        Long millisTime = 0L;
        switch (id) {
            // 重庆时时彩 下期开奖时间
            case 1 :
                millisTime = nextIssueTimeCqssc()/1000;
                break;

            // 新疆时时彩 下期开奖时间
            case 2 :
                millisTime = nextIssueTimeXjssc()/1000;
                break;

            // 腾讯分分彩 下期开奖时间
            case 3 :
                millisTime = nextIssueTimeTxffc()/1000;
                break;

            // TODO 香港六合彩 下期开奖时间
            case 4 :
                break;

            // PC蛋蛋 下期开奖时间
            case 5 :
                millisTime = getNextIssueTimePCDD();
                break;

            // 北京PK10 下期开奖时间
            case 6 :
                millisTime = BjpksUtils.nextIssueTime()/1000;
                break;

            // 幸运飞艇 下期开奖时间
            default :
                millisTime = nextIssueTimeXyft()/1000;
                break;
        }
        return millisTime;
    }

    public static void main(String[] args) {
        System.out.println(nextIssueCqssc());
        System.out.println(TimeHelper.date(nextIssueTimeCqssc()));
    }


    /**
     * 获取指定时间的下一个整10分钟时刻
     * @param date 指定时间
     * @return
     */
    public static Long getNextOneTimeByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        // 将 “秒” 和 “毫秒” 设置为 0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间的下一个整5分钟时刻
     * @param date 指定时间
     * @return
     */
    public static Long getNextFiveTimeByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 23 && minute >= 55) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
        } else {
            int diff = 5 - (minute % 5);
            calendar.add(Calendar.MINUTE, diff);
        }

        // 将 “秒” 和 “毫秒” 设置为 0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间的下一个整10分钟时刻
     * @param date 指定时间
     * @return
     */
    public static Long getNextTenTimeByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 获取分
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 23 && minute >= 50) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
        } else {
            int diff = 10 - (minute % 10);
            calendar.add(Calendar.MINUTE, diff);
        }

        // 将 “秒” 和 “毫秒” 设置为 0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

	
	    public  static String getNextIssueTenssc(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("144".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }
	    
	    public  static String getNextIssueFivessc(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("288".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }
	    
	    public  static String getNextIssueJsssc(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("1440".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "0001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }
	    
	    public  static String getNextIssueTenpks(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("144".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }
	    
	    public  static String getNextIssueFivepks(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("288".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }
	    
	    public  static String getNextIssueJspks(String issue) {
	        // 生成下一期期号
	        String nextIssue;
	        // 截取后三位
	        String num = issue.substring(8);
	        // 判断是否已达最大值
	        if ("1440".equals(num)||"96".equals(num)) {
	            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
	            nextIssue = prefix + "0001";
	        } else {
	            long next = Long.parseLong(issue) + 1;
	            nextIssue = Long.toString(next);
	        }
	        return nextIssue;
	    }

		
	
}
