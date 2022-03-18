package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;

import java.util.List;
import java.util.Map;

public interface JssscLotterySgServiceReadSg {
	
	/** 
	* @Title: getJssscNewestSgInfo 
	* @Description: 获取德州时时彩开奖 
	* @author HANS
	* @date 2019年5月15日下午2:00:17
	*/ 
	Map<String, Object> getJssscNewestSgInfo();
	
	/** 
	* @Title: getActSgLong 
	* @Description: 获取德州时时彩长龙
	* @author HANS
	* @date 2019年5月12日下午11:08:30
	*/ 
	List<Map<String, Object>> getJssscSgLong();
	
	/** 
	* @Title: getOddInfo 
	* @Description: 获取赔率
	* @author HANS
	* @date 2019年5月16日下午10:40:14
	*/ 
	Map<String, Object> getOddInfo(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep);

}
