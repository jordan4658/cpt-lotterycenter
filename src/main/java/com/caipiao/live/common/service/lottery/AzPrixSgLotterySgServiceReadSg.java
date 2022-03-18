package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.MapIntegerVO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.ThereIntegerVO;
import com.caipiao.live.common.model.vo.ThereMemberVO;
import com.caipiao.live.common.model.vo.lottery.ThereMemberListVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AzPrixSgLotterySgServiceReadSg {
	
    /**
     * APP当前期的期号与开奖时间
     * @return
     */
    ResultInfo<Map<String,Object>> getNowIssueAndTime();
    
    /**
     * APP获取号码遗漏
     * @return
     */
    ResultInfo<List<MapListVO>> numNoOpen();
    
    /**
     * APP获取今日号码资讯
     * @return
     */
    ResultInfo<List<MapListVO>> todayNumber(String type);
    
    /**
     * APP获取历史开奖
     * @return
     */
    ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize);
    
    /**
     * APP获取冷热分析资讯
     * @return
     */
    ResultInfo<List<MapListVO>> lengRe(String type, Integer issue);
    
    /**
     * APP获取冠亚和统计
     * @return
     */
    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount();
    
    /**
     * APP获取两面长龙
     * @return
     */
    ResultInfo<List<ThereMemberVO>> liangMianC(String type);
    
    /**
     * APP获取冠军和路珠
     * @return
     */
    ResultInfo<Map<String, ThereMemberListVO>> luzhuG(String type, String date);
    
    /**
     * APP获取前后路珠
     * @return
     */
    ResultInfo<Map<String, ThereMemberListVO>> luzhuQ(String type, String date);
    
    /**
     * APP获取两面遗漏之大小,单双
     * @return
     */
    ResultInfo<Map<String,ArrayList<MapIntegerVO>>> lianMianYl(String type, String way, Integer number);
    
    /**
     * APP获取横版走势
     * @return
     */
    ResultInfo<List<BjpksSgVO>> getSgTrend(Integer issue);
	
}
