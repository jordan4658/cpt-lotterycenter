package com.caipiao.core.library.tool;

import com.caipiao.core.library.constant.Constants;

/** 
 * @ClassName: FanTanCalculationUtils 
 * @Description: 番摊计算工具类
 * @author: Hans
 * @date: 2019年4月10日 下午3:35:42  
 */
public class FanTanCalculationUtils {
	
	/** 
	* @Title: ftjspksSaleResult 
	* @Description: 极速PK10番摊计算结果  前三位和值 对4取模 
	* @param numberStr
	* @return String
	* @author admin
	* @date 2019年4月10日下午5:21:55
	*/ 
	public static String ftjspksSaleResult(String lotteryNumber) {
		String resultMsg = Constants.DEFAULT_NULL;
		
		if(StringUtils.isEmpty(lotteryNumber)) {
			return resultMsg;
		}
		String[] lotteryStrings = lotteryNumber.split(",");
		
        if(lotteryStrings.length < 4) {
        	return resultMsg;
        }
		// 取出中奖号码前三位
        String firstNum = lotteryStrings[0];
        String secondNum = lotteryStrings[1];
        String thirdNum = lotteryStrings[2];
        // 求前三位数值和
        Integer topSum = theTopThreeSum(firstNum,secondNum,thirdNum);
        // 对合值4取模
        Integer modValueInteger = Math.floorMod(topSum, 4);
        //如果为0 则为4 
        if (modValueInteger==0) {
        	modValueInteger = 4;
		}
		return modValueInteger.toString();
	}
	
	/** 
	* @Title: ftjssscSaleResult 
	* @Description: 极速时时彩番摊 全部合值  对4取模 
	* @param numberStr
	* @return String
	* @author admin
	* @date 2019年4月10日下午7:59:02
	*/ 
	public static String ftjssscSaleResult(String numberStr) {
		String resultMsg = Constants.DEFAULT_NULL;
		Integer allMemberInteger = Constants.DEFAULT_INTEGER;
		Integer modValueInteger = Constants.DEFAULT_INTEGER;

		try {
			if (StringUtils.isEmpty(numberStr)) {
				return resultMsg;
			}
			String[] numbers = numberStr.split(",");

			if (numbers.length < 1) {
				return resultMsg;
			}
			for (String member : numbers) {
				Integer memInteger = Integer.valueOf(member);
				allMemberInteger += memInteger;
			}
			// 对合值4取模
			modValueInteger = Math.floorMod(allMemberInteger, 4);
			 //如果为0 则为4 
			if (modValueInteger==0) {
	        	modValueInteger = 4;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return modValueInteger.toString();
	}
	
	
	/** 
	* @Title: theTopThreeSum 
	* @Description: 求和
	* @param firstNum
	* @param secondNum
	* @param thirdNum
	* @return Integer
	* @author admin
	* @date 2019年4月10日下午6:45:59
	*/ 
	public static Integer theTopThreeSum(String firstNum, String secondNum, String thirdNum) {
		Integer topSum = Constants.DEFAULT_INTEGER;
		try {
			// 转换为Integer
			Integer firstInteger = Integer.valueOf(firstNum);
			Integer secondInteger = Integer.valueOf(secondNum);
			Integer thirdInteger = Integer.valueOf(thirdNum);
			// 求和
			topSum = firstInteger + secondInteger + thirdInteger;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topSum;
	}
	
	
	

}
