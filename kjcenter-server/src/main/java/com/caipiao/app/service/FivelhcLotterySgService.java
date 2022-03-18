package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;
import java.util.Map;


/** 
 * @ClassName: FivelhcLotterySgService 
 * @Description: 5分六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午3:31:36  
 */
public interface FivelhcLotterySgService {
	
	/** 
	* @Title: getOnelhcNewestSgInfo 
	* @Description: 获取5分六合彩开奖 
	* @author HANS
	* @date 2019年5月15日下午1:51:21
	*/ 
	ResultInfo<Map<String, Object>> getFivelhcNewestSgInfo();


}
