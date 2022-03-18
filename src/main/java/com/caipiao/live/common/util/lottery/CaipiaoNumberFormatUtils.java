package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: CaipiaoNumberFormatUtils 
 * @Description: 彩票开奖号码前台显示格式调整
 * @author: admin
 * @date: 2019年4月11日 下午8:34:40  
 */
public class CaipiaoNumberFormatUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CaipiaoNumberFormatUtils.class);
	/** 
	* @Title: NumberFormat 
	* @Description: 按照格式修改 
	* @param number void
	* @author admin
	* @date 2019年4月11日下午8:35:53
	*/ 
	public static String NumberFormat(Integer wan,Integer qian,Integer bai,Integer shi,Integer ge) {
		// 定义返回
		StringBuffer formatNumberBuffer = new StringBuffer();
		try {
			// 转化成字符
			String wanString = memberNum(wan);
			String qianString = memberNum(qian);
			String baiString = memberNum(bai);
			String shiString = memberNum(shi);
			String geString = nextMemberNum(ge);
			
			// 合并
			formatNumberBuffer.append(wanString);
			formatNumberBuffer.append(qianString);
			formatNumberBuffer.append(baiString);
			formatNumberBuffer.append(shiString);
			formatNumberBuffer.append(geString);			
		} catch (Exception e) {
            logger.error("NumberFormat occur error.", e);
		}
		return formatNumberBuffer.toString();
	}
	
	/** 
	* @Title: memberNum 
	* @Description: 单个字符转化成字符
	* @param member
	* @return String
	* @author admin
	* @date 2019年4月11日下午8:53:49
	*/ 
	public static String memberNum(Integer member) {
		StringBuffer sigleBuffer = new StringBuffer();
		
		if(member < 10) {
			sigleBuffer.append(Constants.DEFAULT_INTEGER);
			sigleBuffer.append(member);
			sigleBuffer.append(Constants.CONNECTIONSYMBOL);
		} else {
			sigleBuffer.append(member);
			sigleBuffer.append(Constants.CONNECTIONSYMBOL);
		}
		return sigleBuffer.toString();	
	}
	
	/** 
	* @Title: memberNum 
	* @Description: 单个字符转化成字符
	* @param nextNum
	* @return String
	* @author admin
	* @date 2019年4月11日下午8:53:49
	*/ 
	public static String nextMemberNum(Integer nextNum) {
		StringBuffer sigleBuffer = new StringBuffer();

		if (nextNum < 10) {
			sigleBuffer.append(Constants.DEFAULT_INTEGER);
			sigleBuffer.append(nextNum);
		} else {
			sigleBuffer.append(nextNum);
		}
		return sigleBuffer.toString();
	}

}
