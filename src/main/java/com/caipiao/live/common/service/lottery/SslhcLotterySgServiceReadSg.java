package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;

import java.util.List;
import java.util.Map;


/** 
 * @ClassName: SslhcLotterySgService 
 * @Description: 时时六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午3:31:36  
 */
public interface SslhcLotterySgServiceReadSg {
	
	/** 
	* @Title: getOnelhcNewestSgInfo 
	* @Description: 获取时时六合彩开奖 
	* @author HANS
	* @date 2019年5月15日下午1:51:21
	*/ 
	ResultInfo<Map<String, Object>> getSslhcNewestSgInfo();
	
    /** 
    * @Title: getJspksSgLong 
    * @Description: 获取时时六合彩长龙 
    * @author HANS
    * @date 2019年5月10日上午16:48:40
    */ 
    List<Map<String, Object>> getSslhcSgLong();

}
