package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;
import java.util.Map;

/**
 * @author ShaoMing
 * @datetime 2018/7/26 16:36
 */
public interface JspksLotterySgService {
	
	/** 
	* @Title: getJspksNewestSgInfo 
	* @Description: 获取德州PK10赛果 
	* @author HANS
	* @date 2019年5月15日下午2:14:42
	*/ 
	ResultInfo<Map<String, Object>> getJspksNewestSgInfo();


}
