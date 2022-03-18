package com.caipiao.app.service;

import java.util.Map;

public interface JssscLotterySgService {
	
	/** 
	* @Title: getJssscNewestSgInfo 
	* @Description: 获取德州时时彩开奖 
	* @author HANS
	* @date 2019年5月15日下午2:00:17
	*/ 
	Map<String, Object> getJssscNewestSgInfo();

	/**
	 * @Title: getJssscNewestSgInfo
	 * @Description: 获取德州时时彩开奖 统计信息
	 * @author HANS
	 * @date 2019年5月15日下午2:00:17
	 */
	Map<String, Object> getJssscStasticSgInfo(String date);


}
