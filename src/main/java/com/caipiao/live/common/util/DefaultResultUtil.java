package com.caipiao.live.common.util;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.enums.AppMianParamEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认空结果返回工具类
 * 
 * @author Hans
 * @create 2019-03-27 20:07
 * 
 * */
public class DefaultResultUtil {
	
	/**
	 * 定义空结果
	 * 
	 * */
	public static Map<String, Object> getNullResult(){
		// 定义返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.HE.getParamEnName(), Constants.DEFAULT_NULL);
		return result;
	}
	
	public static Map<String, Object> getNullPkResult(){
		// 定义返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), Constants.DEFAULT_NULL);
		return result;
	}
	
	public static Map<String, Object> getNullAlgorithmResult(){
		// 定义返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), Constants.DEFAULT_NULL);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), Constants.DEFAULT_NULL);
		return result;
	}
	
	/**
	 * 定义长龙空结果
	 * 
	 * */
	public static Map<String, Object> getNullDragonsResult(String caiPiaoType,String playTyep, String dragonType){
		// 定义返回结果
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put(AppMianParamEnum.TYPE.getParamEnName(), caiPiaoType);
//		result.put(AppMianParamEnum.DRAGONType.getParamEnName(), Constants.DEFAULT_NULL);
//		result.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), Constants.DEFAULT_INTEGER);
//		result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), Constants.DEFAULT_NULL);
//		result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), Constants.DEFAULT_NULL);
//		return result;
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(AppMianParamEnum.TYPE.getParamEnName(), caiPiaoType);
		result.put(AppMianParamEnum.TYPEID.getParamEnName(), "2201");
		result.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), playTyep);
		result.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonType);
		result.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), 8);
		result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), "20190512288");
		result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(DateUtils.getTestLong()) / 1000L);
		
		List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
		if("大小".equalsIgnoreCase(playTyep)) {
			Map<String, Object> playMap1 = new HashMap<String, Object>();
			playMap1.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), 59585);
			playMap1.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), "大");
			Map<String, Object> playMap2 = new HashMap<String, Object>();
			playMap2.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), 59587);
			playMap2.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), "小");
			playMapList.add(playMap1);
			playMapList.add(playMap2);
		} else {
			Map<String, Object> playMap1 = new HashMap<String, Object>();
			playMap1.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), 59588);
			playMap1.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), "单");
			Map<String, Object> playMap2 = new HashMap<String, Object>();
			playMap2.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), 59589);
			playMap2.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), "双");
			playMapList.add(playMap1);
			playMapList.add(playMap2);
		}
		result.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
		return result;
	}

}
