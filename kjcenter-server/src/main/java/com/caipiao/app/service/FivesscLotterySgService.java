package com.caipiao.app.service;

import java.util.Map;

public interface FivesscLotterySgService {
	
	/** 
	* @Title: getJssscNewestSgInfo 
	* @Description: 获取5分时时彩开奖
	* @author HANS
	* @date 2019年5月15日下午2:00:17
	*/ 
	Map<String, Object> getFivesscNewestSgInfo();

	/**
	 * @Title: getJssscNewestSgInfo
	 * @Description: 获取5分时时彩开奖 统计信息
	 * @author HANS
	 * @date 2019年5月15日下午2:00:17
	 */
	Map<String, Object> getFivesscStasticSgInfo(String date);


}
