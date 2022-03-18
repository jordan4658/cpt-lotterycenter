package com.caipiao.task.server.util;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.tool.StringUtils;

/** 
 * @ClassName: AusactSgUtils 
 * @Description: 澳洲系列玩法处理
 * @author: HANS
 * @date: 2019年5月7日 下午3:30:38  
 */
public class AusactSgUtils {

	/** 
	* @Title: getBigOrSmall 
	* @Description: 通过开奖号码计算大小
	* @param number
	* @return String
	* @author HANS
	* @date 2019年5月7日下午3:36:48
	*/ 
	public static String getBigOrSmall(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 计算号码总和
		int total = getNumberTotal(number);
	            
		// 计算大小
		if(total > Constants.DEFAULT_BIGORSMALL_MEDIAN) {
			return Constants.BIGORSMALL_BIG;
		} else if(total == Constants.DEFAULT_BIGORSMALL_MEDIAN) {
			return Constants.BIGORSMALL_SAME;
		} else {
			return Constants.BIGORSMALL_SMALL;
		}
	}
	
	/** 
	* @Title: getPceegBigOrSmall 
	* @Description: PC蛋蛋计算混合大小 
	* @author HANS
	* @date 2019年5月12日下午8:14:32
	*/ 
	public static String getPceegBigOrSmall(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 计算号码总和
		int total = getNumberTotal(number);
		
		// 计算大小
		if(total >= Constants.DEFAULT_INTEGER && total <= 13) {
			return Constants.BIGORSMALL_SMALL;
		} else if(total > 13 && total <= 27) {
			return Constants.BIGORSMALL_BIG;
		} else {
			return Constants.DEFAULT_NULL;
		}
	}
	
	/** 
	* @Title: getJssscBigOrSmall 
	* @Description: 德州时时彩计算两面大小  
	* @author HANS
	* @date 2019年5月13日上午11:40:20
	*/ 
	public static String getJssscBigOrSmall(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 计算号码总和
		int total = getNumberTotal(number);
		
		// 计算大小
		if(total >= Constants.DEFAULT_INTEGER && total <= 22) {
			return Constants.TOTAL_BIGORSMALL_SMALL;
		} else if(total >= 23) {
			return Constants.TOTAL_BIGORSMALL_BIG;
		} else {
			return Constants.DEFAULT_NULL;
		}
	}
	
	/** 
	* @Title: getJssscSingleNumber 
	* @Description: 单个球的大小 
	* @author HANS
	* @date 2019年5月13日上午11:50:41
	*/ 
	public static String getJssscSingleNumber(Integer number) {
		if(number == null) {
			return Constants.DEFAULT_NULL;
		}
		// 计算大小
		if(number >= Constants.DEFAULT_INTEGER && number <= 4) {
			return Constants.BIGORSMALL_SMALL;
		} else if(number >= 5) {
			return Constants.BIGORSMALL_BIG;
		} else {
			return Constants.DEFAULT_NULL;
		}
	}
	
	/** 
	* @Title: getSingleAndDouble 
	* @Description: 通过开奖号码计算单双 
	* @param number
	* @return String
	* @author HANS
	* @date 2019年5月7日下午3:37:15
	*/ 
	public static String getSingleAndDouble(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 计算号码总和
		int total = getNumberTotal(number);
		// 计算奇偶
		if((total & 1) != 1){    
			//是偶数 
			return Constants.TOTAL_BIGORSMALL_EVEN_NUMBER;
		} else {
			//是奇数 
			return Constants.TOTAL_BIGORSMALL_ODD_NUMBER;
		}
	}

	/** 
	* @Title: getOneSingleAndDouble 
	* @Description: 单个球的计算单双 
	* @author HANS
	* @date 2019年5月13日上午11:59:12
	*/ 
	public static String getOneSingleAndDouble(Integer number) {
		if(number == null) {
			return Constants.DEFAULT_NULL;
		}
		// 计算奇偶
		if((number & 1) != 1){    
			//是偶数 
			return Constants.BIGORSMALL_EVEN_NUMBER;
		} else {
			//是奇数 
			return Constants.BIGORSMALL_ODD_NUMBER;
		}
	}

	/** 
	* @Title: getFiveElements 
	* @Description: 通过开奖号码计算五行
	* @param number
	* @return String
	* @author HANS
	* @date 2019年5月7日下午3:37:33
	*/ 
	public static String getFiveElements(String number) {
		if(StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 计算号码总和
		int total = getNumberTotal(number);
		
		// 计算五行
		if(total >= Constants.BIGORSMALL_GOLD_START && total <= Constants.BIGORSMALL_GOLD_END) {
			return Constants.BIGORSMALL_GOLD_TYPE;
		} else if(total >= Constants.BIGORSMALL_WOOD_START && total <= Constants.BIGORSMALL_WOOD_END) {
			return Constants.BIGORSMALL_WOOD_TYPE;
		} else if(total >= Constants.BIGORSMALL_WATER_START && total <= Constants.BIGORSMALL_WATER_END) {
			return Constants.BIGORSMALL_WATER_TYPE;
		} else if(total >= Constants.BIGORSMALL_FIRE_START && total <= Constants.BIGORSMALL_FIRE_END) {
			return Constants.BIGORSMALL_FIRE_TYPE;
		} else if(total >= Constants.BIGORSMALL_SOIL_START && total <= Constants.BIGORSMALL_SOIL_END) {
			return Constants.BIGORSMALL_SOIL_TYPE;
		} else {
			return Constants.DEFAULT_NULL;
		}
	}
	
	/** 
	* @Title: getNumberTotal 
	* @Description: 计算开奖号码总和
	* @param number
	* @return Integer
	* @author HANS
	* @date 2019年5月7日下午3:54:51
	*/ 
	public static int getNumberTotal(String number) {
		 String[] idarr = number.split(",");
		 // 结果数据
		 int total = Constants.DEFAULT_INTEGER;
		 for (String sigle : idarr) {
			 sigle = sigle.trim();
	         int sigleInt = Integer.parseInt(sigle);	         
	         total += sigleInt;
		 }
		 return total;
	}
	
}
