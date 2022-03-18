package com.caipiao.live.common.service.lottery;

import java.util.List;
import java.util.Map;

public interface FivesscLotterySgServiceReadSg {
	
	/** 
	* @Title: getJssscNewestSgInfo 
	* @Description: 获取德州时时彩开奖 
	* @author HANS
	* @date 2019年5月15日下午2:00:17
	*/ 
	Map<String, Object> getFivesscNewestSgInfo();
		
	/** 
	* @Title: getActSgLong 
	* @Description: 获取德州时时彩长龙
	* @author HANS
	* @date 2019年5月12日下午11:08:30
	*/ 
	public List<Map<String, Object>> getFivesscSgLong();

}
