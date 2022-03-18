package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author ShaoMing
 * @datetime 2018/7/26 16:36
 */
public interface OnelhcLotterySgServiceReadSg {
	
	/** 
	* @Title: getOnelhcNewestSgInfo 
	* @Description: 获取1分六合彩开奖 
	* @author HANS
	* @date 2019年5月15日下午1:51:21
	*/ 
	ResultInfo<Map<String, Object>> getOnelhcNewestSgInfo();
	
    /** 
    * @Title: getJspksSgLong 
    * @Description: 获取1分六合彩长龙 
    * @author HANS
    * @date 2019年5月10日上午16:48:40
    */ 
    List<Map<String, Object>> getOnelhcSgLong();
    
	/** 
	* @Title: calculateResult 
	* @Description: 获取计算结果  
	* @author HANS
	* @date 2019年5月21日下午4:29:20
	*/ 
	String calculateResult(int type, String number);
	
    /** 
    * @Title: addTmlmDoublePlayMapList 
    * @Description: 特码两面单双和大小赔率 
    * @author HANS
    * @date 2019年5月21日下午8:15:53
    */ 
    Map<String, Object> addTmlmDoublePlayMapList(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep);
    
    /** 
    * @Title: addZmtotalDoublePlayMapList 
    * @Description: 正码总单总双\正码总大总小
    * @author HANS
    * @date 2019年5月21日下午8:15:34
    */ 
    Map<String, Object> addZmtotalDoublePlayMapList(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep);
    
    /** 
    * @Title: addZytDoublePlayMapList 
    * @Description: 正特单双\正特大小 
    * @author HANS
    * @date 2019年5月21日下午8:15:15
    */ 
    Map<String, Object> addZytDoublePlayMapList(String playTyep, PlayAndOddListInfoVO twoWallplayAndOddListInfo);

}
