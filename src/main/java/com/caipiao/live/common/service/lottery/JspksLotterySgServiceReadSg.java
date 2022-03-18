package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author ShaoMing
 * @datetime 2018/7/26 16:36
 */
public interface JspksLotterySgServiceReadSg {
	
	/** 
	* @Title: getJspksNewestSgInfo 
	* @Description: 获取德州PK10赛果 
	* @author HANS
	* @date 2019年5月15日下午2:14:42
	*/ 
	ResultInfo<Map<String, Object>> getJspksNewestSgInfo();
	
    /** 
    * @Title: getJspksSgLong 
    * @Description: 获取德州PK10长龙 
    * @author HANS
    * @date 2019年5月10日上午16:48:40
    */ 
    List<Map<String, Object>> getJspksSgLong();
    
    /** 
    * @Title: getPKtenOddInfo 
    * @Description: 获取赔率 
    * @author HANS
    * @date 2019年5月18日下午1:30:39
    */ 
    Map<String, Object> getPKtenOddInfo(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep);

}
