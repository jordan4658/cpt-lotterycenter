package com.caipiao.live.common.util.lottery;


import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.util.StringUtils;

/**
 * @ClassName: AusactSgUtils
 * @Description: 德州PK10玩法处理
 * @author: HANS
 * @date: 2019年5月7日 下午3:30:38
 */
public class JspksSgUtils {

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

	/**
	 * @Title: getJssscBigOrSmall
	 * @Description: 德州PK10计算大小
	 * @author HANS
	 * @date 2019年5月13日上午11:40:20
	 */
	public static String getJspksBigOrSmall(String number, int type) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;

		if (type == 17 || type == 120 || type == 147 || type == 174 || type == 245) {
			// 冠亚和大小
			String sigleOne = idarr[0];
			String sigleTwo = idarr[1];
			int sigleOneInt = Integer.parseInt(sigleOne);
			int sigleTwoInt = Integer.parseInt(sigleTwo);
			int total = sigleOneInt + sigleTwoInt;

			if (total <= 11) {
				result = Constants.CROWN_BIGORSMALL_SMALL;
			} else {
				result = Constants.CROWN_BIGORSMALL_BIG;
			}
		}

		if (type == 18 || type == 121 || type == 148 || type == 175 || type == 246) {
			// 冠军大小
			String sigleOne = idarr[0];
			result = sigleNumberBigAndSmall(sigleOne);
		}

		if (type == 19 || type == 122 || type == 149 || type == 176 || type == 247) {
			// 亚军大小
			String sigleTwo = idarr[1];
			result = sigleNumberBigAndSmall(sigleTwo);
		}
		
		if (type == 20 || type == 123 || type == 150 || type == 177 || type == 248) {
			// 第三名大小
			String sigleThree = idarr[2];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 21 || type == 124 || type == 151 || type == 178 || type == 249) {
			// 第四名大小
			String sigleThree = idarr[3];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 22 || type == 125 || type == 152 || type == 179 || type == 250) {
			// 第五名大小
			String sigleThree = idarr[4];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 23 || type == 126 || type == 153 || type == 180 || type == 251) {
			// 第六名大小
			String sigleThree = idarr[5];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 24 || type == 127 || type == 154 || type == 181 || type == 252) {
			// 第七名大小
			String sigleThree = idarr[6];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 25 || type == 128 || type == 155 || type == 182 || type == 253) {
			// 第八名大小
			String sigleThree = idarr[7];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 26 || type == 129 || type == 156 || type == 183 || type == 254) {
			//  第九名大小
			String sigleThree = idarr[8];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		
		if (type == 27 || type == 130 || type == 157 || type == 184 || type == 255) {
			// 第十名大小
			String sigleThree = idarr[9];
			result = sigleNumberBigAndSmall(sigleThree);
		}
		return result;
	}
	
	/** 
	* @Title: getJspksSigleAndDouble 
	* @Description: 德州PK10计算单双
	* @author HANS
	* @date 2019年5月14日下午3:54:44
	*/ 
	public static String getJspksSigleAndDouble(String number, int type) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		
		if (type == 28 || type == 131 || type == 158 || type == 185 || type == 256) {
			// 冠亚和大小
			String sigleOne = idarr[0];
			String sigleTwo = idarr[1];
			int sigleOneInt = Integer.parseInt(sigleOne);
			int sigleTwoInt = Integer.parseInt(sigleTwo);
			int total = sigleOneInt + sigleTwoInt;
			if((total & 1) != 1){    
				//是偶数 
				result = Constants.CROWN_BIGORSMALL_EVEN_NUMBER;
			} else {
				//是奇数 
				result = Constants.CROWN_BIGORSMALL_ODD_NUMBER;
			}
		}
		
		if (type == 29 || type == 132 || type == 159 || type == 186 || type == 257) {
			//冠军单双 
			String sigleOne = idarr[0];
			result = sigleNumberSigleAndDouble(sigleOne);
		}
		
		if (type == 30 || type == 133 || type == 160 || type == 187 || type == 258) {
			//亚军单双 
			String sigleTwo = idarr[1];
			result = sigleNumberSigleAndDouble(sigleTwo);
		}
		
		if (type == 31 || type == 134 || type == 161 || type == 188 || type == 259) {
			//第三名单双
			String sigleThree = idarr[2];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 32 || type == 135 || type == 162 || type == 189 || type == 260) {
			//第四名单双
			String sigleThree = idarr[3];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 33 || type == 136 || type == 163 || type == 190 || type == 261) {
			//第五名单双
			String sigleThree = idarr[4];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 34 || type == 137 || type == 164 || type == 191 || type == 262) {
			//第六名单双
			String sigleThree = idarr[5];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 35 || type == 138 || type == 165 || type == 192 || type == 263) {
			//第七名单双
			String sigleThree = idarr[6];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 36 || type == 139 || type == 166 || type == 193 || type == 264) {
			//第八名单双
			String sigleThree = idarr[7];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 37 || type == 140 || type == 167 || type == 194 || type == 265) {
			// 第九名单双
			String sigleThree = idarr[8];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		
		if (type == 38 || type == 141 || type == 168 || type == 195 || type == 266) {
			//第十名单双
			String sigleThree = idarr[9];
			result = sigleNumberSigleAndDouble(sigleThree);
		}
		return result;
	}
	
	/** 
	* @Title: getJspksDragonAndtiger 
	* @Description: 德州PK10计算龙虎
	* @author HANS
	* @date 2019年5月14日下午4:37:26
	*/ 
	public static String getJspksDragonAndtiger(String number, int type) {
		if (StringUtils.isEmpty(number)) {
			return Constants.DEFAULT_NULL;
		}
		String[] idarr = number.split(",");
		// 定义返回值
		String result = Constants.DEFAULT_NULL;
		
		if (type == 39 || type == 142 || type == 169 || type == 196 || type == 267) {
			// 冠军龙虎
			result = sigleNumberDragonAndtiger(idarr[0],idarr[9]);
		}
		
		if (type == 40 || type == 143 || type == 170 || type == 197 || type == 268) {
			// 亚军龙虎
			result = sigleNumberDragonAndtiger(idarr[1],idarr[8]);
		}
		
		if (type == 41 || type == 144 || type == 171 || type == 198 || type == 269) {
			// 第三名龙虎
			result = sigleNumberDragonAndtiger(idarr[2],idarr[7]);
		}
		
		if (type == 42 || type == 145 || type == 172 || type == 199 || type == 270) {
			// 第四名龙虎
			result = sigleNumberDragonAndtiger(idarr[3],idarr[6]);
		}
		
		if (type == 43 || type == 146 || type == 173 || type == 200 || type == 271) {
			// 第五名龙虎
			result = sigleNumberDragonAndtiger(idarr[4],idarr[5]);
		}
		return result;
	}
	
	/** 
	* @Title: sigleNumberBigAndSmall 
	* @Description: 计算单个号码大小
	* @return String
	* @author HANS
	* @date 2019年5月14日下午3:32:33
	*/ 
	public static String sigleNumberBigAndSmall(String sigle) {
		if(sigle == null) {
			return Constants.DEFAULT_NULL;
		}
		// 定义结果
		String result = Constants.DEFAULT_NULL;
		// 单个号码Integer值
		int sigleInt = Integer.parseInt(sigle);

		if (sigleInt >= 1 && sigleInt <= 5) {
			result = Constants.BIGORSMALL_SMALL;
		} else if (sigleInt >= 6 && sigleInt <= 10) {
			result = Constants.BIGORSMALL_BIG;
		} else {
			result = Constants.DEFAULT_NULL;
		}
		return result;
	}
	
	
	/** 
	* @Title: sigleNumberSigleAndDouble 
	* @Description: 计算号码单双
	* @param total
	* @return String
	* @author HANS
	* @date 2019年5月14日下午4:17:22
	*/ 
	public static String sigleNumberSigleAndDouble(String sigle) {
		if(sigle == null) {
			return Constants.DEFAULT_NULL;
		}
		// 单个号码Integer值
		int sigleInt = Integer.parseInt(sigle);
		// 计算奇偶
		if((sigleInt & 1) != 1){    
			//是偶数 
			return Constants.BIGORSMALL_EVEN_NUMBER;
		} else {
			//是奇数 
			return Constants.BIGORSMALL_ODD_NUMBER;
		}
	}
	
	/** 
	* @Title: sigleNumberDragonAndtiger 
	* @Description: PK10计算号码龙虎 
	* @author HANS
	* @date 2019年5月14日下午4:45:35
	*/ 
	public static String sigleNumberDragonAndtiger(String sigle, String nextSigle) {
		if(sigle == null) {
			return Constants.DEFAULT_NULL;
		}
		
		if(nextSigle == null) {
			return Constants.DEFAULT_NULL;
		}
		// 当前号码
		int sigleInt = Integer.parseInt(sigle);
		// 下一个号码
		int nextSigleInt = Integer.parseInt(nextSigle);
		
		if(sigleInt > nextSigleInt) {
			return Constants.PLAYRESULT_DRAGON;
		} else {
			return Constants.PLAYRESULT_TIGER;
		}
	}
	

	/** 
	* @Title: interceptionPlayString 
	* @Description: 截取玩法名称
	* @author HANS
	* @date 2019年5月25日下午6:53:48
	*/ 
//	public static String interceptionPlayString(String sourcePlay) {
//		// 定义返回值
//		String result = Constants.DEFAULT_NULL;
//				
//		if(StringUtils.isEmpty(sourcePlay)) {
//			return result;
//		}
//		
//		// 结果包含“大”，只返回大
//		if(sourcePlay.contains(Constants.BIGORSMALL_BIG)) {
//			result = Constants.BIGORSMALL_BIG;
//		}
//		
//		// 结果包含“小”，只返回大
//		if(sourcePlay.contains(Constants.BIGORSMALL_SMALL)) {
//			result = Constants.BIGORSMALL_SMALL;
//		}
//		
//		// 结果包含“单”，只返回大
//		if(sourcePlay.contains(Constants.BIGORSMALL_ODD_NUMBER)) {
//			result = Constants.BIGORSMALL_ODD_NUMBER;
//		}
//		
//		// 结果包含“双”，只返回大
//		if(sourcePlay.contains(Constants.BIGORSMALL_EVEN_NUMBER)) {
//			result = Constants.BIGORSMALL_EVEN_NUMBER;
//		}
//		
//		// 结果包含“龙”，只返回大
//		if(sourcePlay.contains(Constants.PLAYRESULT_DRAGON)) {
//			result = Constants.PLAYRESULT_DRAGON;
//		}
//		
//		// 结果包含“虎”，只返回大
//		if(sourcePlay.contains(Constants.PLAYRESULT_TIGER)) {
//			result = Constants.PLAYRESULT_TIGER;
//		}
//		return result;
//	}
	
}
