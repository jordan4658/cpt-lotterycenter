package com.caipiao.task.server.util;

import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class AusPksUtil {
	/**
	 * 计算pks号码
	 * 
	 * @return
	 */
	public static LinkedHashSet<Integer> partOneMatch(String sg) {
		// 获取开奖号码
		LinkedHashSet<Integer> numbers = new LinkedHashSet<Integer>();
		String[] nums=	sg.split(",");
		// 获取每一个开奖号码，截取尾数，判断是否重复，非重复添加至List数组
		for (String num:nums) {
			int num1=Integer.parseInt(num)%10;
			numbers.add(num1);
			if(numbers.size()==10){
				return numbers;
			}
		}
		//计算方式:取澳洲ACT开奖号码的个位和十位相加的和值，再取该和值的尾数作为澳洲F1的开奖号码
		for (String num:nums) {
			int num2=(Integer.parseInt(num)%10+Integer.parseInt(num)/10)%10;
			numbers.add(num2);
			if(numbers.size()==10){
				return numbers;
			}
		}
//计算方式:取澳洲ACT开奖号码的第一位和二十位(第二位和第十九位...第十位和第十一位以此类推)相加的和值，再取该和值的尾数作为澳洲F1的开奖号码
		for (int i = 0; i <10 ; i++) {
			int num3=(Integer.parseInt(nums[i])+Integer.parseInt(nums[19-i]))%10;
			numbers.add(num3);
			if(numbers.size()==10){
				return numbers;
			}
		}
//计算方式:取澳洲ACT开奖号码的第一位和二位(第二位和第三位...第十九位和第二十位以此类推)相加的和值，再取该和值的尾数作为澳洲F1的开奖号码
		for (int i = 0; i <20 ; i++) {
			int num4 = 0;
			if(i == 19){
				num4=(Integer.parseInt(nums[i])+Integer.parseInt(nums[0]))%10;
			}else{
				num4=(Integer.parseInt(nums[i])+Integer.parseInt(nums[i+1]))%10;
			}
			numbers.add(num4);
			if(numbers.size()==10){
				return numbers;
			}
		}
		//计算方式:取澳洲ACT开奖号码的第一位和十一位(第二位和第十二位...第十位和第二十位以此类推)相加的和值，再取该和值的尾数作为澳洲F1的开奖号码
		for (int i = 0; i <10 ; i++) {
			int num5=(Integer.parseInt(nums[i])+Integer.parseInt(nums[i+10]))%10;
			numbers.add(num5);
			if(numbers.size()==10){
				return numbers;
			}
		}

		if(numbers.size() == 9){
			int noti = 0;
			for(int i=0;i<10;i++){
				if(!numbers.contains(i)){
					noti = i;
					break;
				}
			}
			numbers.add(noti);
		}

		return numbers;
	}

	public static String getAusPksbyAct(String sg){
		LinkedHashSet<Integer> numbers=AusPksUtil.partOneMatch(sg);
		String pkssg="";
		for (int num:numbers) {
			pkssg+=num+",";
		}
		pkssg=	pkssg.substring(0,pkssg.length()-1);
		return pkssg;
	}

	public static String getAusSscbyAct(String sg){
		String[] nums=	sg.split(",");
		// 取澳洲ACT的开奖号码尾数作为澳洲时时彩的开奖号码，开出5个时时彩号码(废弃)
		// 1到4   5到8   9到12   13到16   17到20  分别和值取尾数
		StringBuilder sscsg = new StringBuilder();
		int number1 = (Integer.parseInt(nums[0]) + Integer.parseInt(nums[1]) + Integer.parseInt(nums[2]) + Integer.parseInt(nums[3]))%10;
		int number2 = (Integer.parseInt(nums[4]) + Integer.parseInt(nums[5]) + Integer.parseInt(nums[6]) + Integer.parseInt(nums[7]))%10;
		int number3 = (Integer.parseInt(nums[8]) + Integer.parseInt(nums[9]) + Integer.parseInt(nums[10]) + Integer.parseInt(nums[11]))%10;
		int number4 = (Integer.parseInt(nums[12]) + Integer.parseInt(nums[13]) + Integer.parseInt(nums[14]) + Integer.parseInt(nums[15]))%10;
		int number5 = (Integer.parseInt(nums[16]) + Integer.parseInt(nums[17]) + Integer.parseInt(nums[18]) + Integer.parseInt(nums[19]))%10;
		sscsg.append(number1).append(",").append(number2).append(",").append(number3).append(",").append(number4).append(",").append(number5);
		return  sscsg.toString();
	}



	public static List<LotterySgModel> getAusSscListbyAct(List<LotterySgModel> actResults) {
		List<LotterySgModel> list=new ArrayList<>();
		for (LotterySgModel sgmodel: actResults) {
			LotterySgModel model=new LotterySgModel();
			BeanUtils.copyProperties(sgmodel, model);
			model.setSg(getAusPksbyAct(sgmodel.getSg()));
			list.add(model);
		}
		return  list;
	}

	public static List<LotterySgModel> getAusPksListbyAct(List<LotterySgModel> actResults) {

		List<LotterySgModel> list=new ArrayList<>();
		for (LotterySgModel sgmodel: actResults) {
			LotterySgModel model=new LotterySgModel();
			BeanUtils.copyProperties(sgmodel, model);
			model.setSg(getAusSscbyAct(sgmodel.getSg()));
			list.add(model);
		}
		return  list;
	}
}
