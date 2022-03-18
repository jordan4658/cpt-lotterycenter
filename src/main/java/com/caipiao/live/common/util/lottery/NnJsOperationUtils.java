package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.enums.NiuWinnerEnum;
import com.caipiao.live.common.util.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class NnJsOperationUtils {

	// 判断输赢
	// 德州牛牛比大小
	// 无牛《牛一《牛二《牛三《牛四《牛五《牛六《牛七《牛八《牛九《牛牛
	// 对应结果数字0-20(牛牛设置为20)
	public static boolean winOrNot(String betNum, String sg) {

		String[] sgArray = sg.split(",");
		Integer[] sgIntArray = new Integer[10];
		for (int i = 0; i < sgArray.length; i++) {
			sgIntArray[i] = Integer.valueOf(sgArray[i]);
		}
		int zhuang = getResult(new int[] { sgIntArray[0], sgIntArray[1], sgIntArray[2], sgIntArray[3], sgIntArray[4] });
		int xian1 = getResult(new int[] { sgIntArray[1], sgIntArray[2], sgIntArray[3], sgIntArray[4], sgIntArray[5] });
		int xian2 = getResult(new int[] { sgIntArray[2], sgIntArray[3], sgIntArray[4], sgIntArray[5], sgIntArray[6] });
		int xian3 = getResult(new int[] { sgIntArray[3], sgIntArray[4], sgIntArray[5], sgIntArray[6], sgIntArray[7] });
		int xian4 = getResult(new int[] { sgIntArray[4], sgIntArray[5], sgIntArray[6], sgIntArray[7], sgIntArray[8] });
		int xian5 = getResult(new int[] { sgIntArray[5], sgIntArray[6], sgIntArray[7], sgIntArray[8], sgIntArray[9] });
		if (betNum.contains(NiuWinnerEnum.IDLERONE.getEnName())) {
			return winByBetNum(zhuang, xian1, sgIntArray[0], sgIntArray[1]);
		} else if (betNum.contains(NiuWinnerEnum.IDLERTWO.getEnName())) {
			return winByBetNum(zhuang, xian2, sgIntArray[0], sgIntArray[2]);
		} else if (betNum.contains(NiuWinnerEnum.IDLERTHREE.getEnName())) {
			return winByBetNum(zhuang, xian3, sgIntArray[0], sgIntArray[3]);
		} else if (betNum.contains(NiuWinnerEnum.IDLERFOUR.getEnName())) {
			return winByBetNum(zhuang, xian4, sgIntArray[0], sgIntArray[4]);
		} else if (betNum.contains(NiuWinnerEnum.IDLERFIVE.getEnName())) {
			return winByBetNum(zhuang, xian5, sgIntArray[0], sgIntArray[5]);
		}
		return false;
	}
	
	/** 
	* @Title: getNiuWinner 
	* @Description: 通过开奖记录，算出德州牛牛结果 
	* @param number
	* @return String
	* @author admin
	* @date 2019年4月11日上午11:36:51
	*/ 
	public static String getNiuWinner(String number) {
		// 定义返回结果字符
		StringBuffer winnerBuffer = new StringBuffer();
		// 收集闲家赢得信息
		List<String> winnerList = new ArrayList<String>();
		boolean idlerOne = winOrNot(NiuWinnerEnum.IDLERONE.getEnName(), number);
		boolean idlerTwo = winOrNot(NiuWinnerEnum.IDLERTWO.getEnName(), number);
		boolean idlerThree = winOrNot(NiuWinnerEnum.IDLERTHREE.getEnName(), number);
		boolean idlerFour = winOrNot(NiuWinnerEnum.IDLERFOUR.getEnName(), number);
		boolean idlerFive = winOrNot(NiuWinnerEnum.IDLERFIVE.getEnName(), number);
		
		if(idlerOne) {
			winnerList.add(NiuWinnerEnum.IDLERONE.getCnName());
		}
		
		if(idlerTwo) {
			winnerList.add(NiuWinnerEnum.IDLERTWO.getCnName());
		}
		
		if(idlerThree) {
			winnerList.add(NiuWinnerEnum.IDLERTHREE.getCnName());
		}
		
		if(idlerFour) {
			winnerList.add(NiuWinnerEnum.IDLERFOUR.getCnName());
		}
		
		if(idlerFive) {
			winnerList.add(NiuWinnerEnum.IDLERFIVE.getCnName());
		}
		
		// 庄家赢
		if(CollectionUtils.isEmpty(winnerList)) {
			winnerBuffer.append(NiuWinnerEnum.BANKER.getCnName());
			return winnerBuffer.toString();
		}
		
		for(int index = 0; index < winnerList.size() ; index++) {
			String winderMember = winnerList.get(index);
			
			if(StringUtils.isNotEmpty(winderMember)) {
				winnerBuffer.append(winderMember);
				
				if(index < winnerList.size() - 1) {
					winnerBuffer.append(",");
				}
			}	
		}
		return winnerBuffer.toString();	
	}

	private static boolean winByBetNum(int zhuang, int xian, int zhuangStart, int xianStart) {
		if (xian > zhuang) {
			return true;
		} else if (xian == zhuang) {
			if (xian <= 6) {
				return false;
			} else {
				return xianStart > zhuangStart ? true : false;
			}
		} else {
			return false;
		}
	}

	// 012,013,014,023,024,034,123,124,134,234
	// 返回对应的牛几
	private static int getResult(int[] data) {
		int[][] array = { { data[0], data[1], data[2], data[3], data[4] },
				{ data[0], data[1], data[3], data[2], data[4] }, { data[0], data[1], data[4], data[2], data[3] },
				{ data[0], data[2], data[3], data[1], data[4] }, { data[0], data[2], data[4], data[1], data[3] },
				{ data[0], data[3], data[4], data[1], data[2] }, { data[1], data[2], data[3], data[0], data[4] },
				{ data[1], data[2], data[4], data[0], data[3] }, { data[1], data[3], data[4], data[0], data[2] },
				{ data[2], data[3], data[4], data[0], data[1] } };
		for (int i = 0; i < array.length; i++) {
			if ((array[i][0] + array[i][1] + array[i][2]) % 10 == 0) {
				int xulie = array[i][3] + array[i][4];
				if (xulie == 10) {
					return 10; // 牛牛
				} else {
					return xulie % 10;
				}
			} else {
				continue;
			}
		}
		return 0;
	}

}
