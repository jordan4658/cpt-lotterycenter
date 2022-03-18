package com.caipiao.task.server.util;


import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.tool.StringUtils;

/**
 * @ClassName: AusactSgUtils
 * @Description: 德州PK10玩法处理
 * @author: HANS
 * @date: 2019年5月7日 下午3:30:38
 */
public class OnelhcSgUtils {

	/**
	 * @Title: getNumberTotal
	 * @Description: 计算开奖号码总和
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
	
	/** 
	* @Title: getOnelhcBigOrSmall 
	* @Description: 计算大小
	* @author HANS
	* @date 2019年5月15日下午7:16:35
	*/ 
	public static String getOnelhcBigOrDouble(String number, int type) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		// 特码是最后一个值
		String tema = idarr[idarr.length - 1];
		int temaInt = Integer.parseInt(tema);
		
		if(type == 45 || type == 218 || type == 202) {
			if(temaInt >= 25) {
				result = Constants.BIGORSMALL_BIG;
			} else if(temaInt < 25) {
				result = Constants.BIGORSMALL_SMALL;
			} else if(temaInt == 49){
				result = Constants.DEFAULT_NULL;
			} else {
				result = Constants.DEFAULT_NULL;
			}
		} else if(type == 44 || type == 217 || type == 201) {
			if(temaInt == 49){
				result = Constants.DEFAULT_NULL;
			} else {
				if((temaInt & 1) != 1){   
					//是偶数 
					result = Constants.BIGORSMALL_EVEN_NUMBER;
				} else {
					//是奇数 
					result = Constants.BIGORSMALL_ODD_NUMBER;
				}
			}
		} else {
			result = Constants.DEFAULT_NULL;
		}
		return result;
	}

	/** 
	* @Title: getOnelhcTotalBigOrDouble 
	* @Description: 正码总单总双、大小 
	* @author HANS
	* @date 2019年5月15日下午8:53:05
	*/ 
	public static String getOnelhcTotalBigOrDouble(String number, int type) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		// 所以号码总和
		int total = getNumberTotal(number);
		// 判断
		
		if(type == 46 || type == 219 || type == 203) {
			if((total & 1) != 1){    
				//是偶数 
				result = Constants.ZONG_BIGORSMALL_EVEN_NUMBER;
			} else {
				//是奇数 
				result = Constants.ZONG_BIGORSMALL_ODD_NUMBER;
			}
		} else if(type == 47 || type == 220 || type == 204) {
            if(total >= 175) {
            	result = Constants.ZONG_BIGORSMALL_BIG;
			} else {
				result = Constants.ZONG_BIGORSMALL_SMALL;
			}
		}
		return result;
	}
	
	/** 
	* @Title: getZytBigOrSmall 
	* @Description: 正特 大小
	* @author HANS
	* @date 2019年5月15日下午9:09:27
	*/ 
	public static String getZytBigOrSmall(String number, int location) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		// 特码是最后一个值
		String tema = idarr[location];
		int temaInt = Integer.parseInt(tema);
		
		if(temaInt >= 25) {
			result = Constants.BIGORSMALL_BIG;
		} else if(temaInt < 25) {
			result = Constants.BIGORSMALL_SMALL;
		} else if(temaInt == 49){
			result = Constants.DEFAULT_NULL;
		} else {
			result = Constants.DEFAULT_NULL;
		}
		return result;
	}
	
	/** 
	* @Title: getZytSigleOrDouble 
	* @Description: 正特 单双
	* @author HANS
	* @date 2019年5月15日下午9:09:54
	*/ 
	public static String getZytSigleOrDouble(String number, int location) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		// 特码是最后一个值
		String tema = idarr[location];
		int temaInt = Integer.parseInt(tema);
		
		if(temaInt == 49){
			result = Constants.DEFAULT_NULL;
		} else {
			if((temaInt & 1) != 1){    
				//是偶数 
				result = Constants.BIGORSMALL_EVEN_NUMBER;
			} else {
				//是奇数 
				result = Constants.BIGORSMALL_ODD_NUMBER;
			}
		}
		return result;
	}

}
