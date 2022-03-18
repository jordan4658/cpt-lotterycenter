package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.TjsscMissDTO;
import com.caipiao.live.common.model.dto.result.TjsscSizeMissDTO;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySg;

import java.util.List;
import java.util.Map;

public interface TjsscLotterySgServiceReadSg {
    /**
     * 根据日期获取开奖结果
     * @param date 日期, 如2018-08-21
     * @return
     */
    List<TjsscLotterySg> getSgByDate(String date);	
    /**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getNewestSgInfo();

    /**	

    /**
     * 今日统计
     * @param type
     * @param date 日期, 如2019-03-10
     * @return
     */
    ResultInfo<Map<String, Object>> todayCount(String type, String date);

    /**
     * 获取天津时时彩：遗漏统计
     * @return
     */
    ResultInfo<List<TjsscMissDTO>> getTjsscMissCount(String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TjsscSizeMissDTO>> getTjsscSizeMissCount(Integer count, Integer number);	    
    
    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TjsscSizeMissDTO>> getTjsscSingleMissCount(Integer count, Integer number);
    
    /**
     * 根据日期获取开奖分页结果
     * @param 
     * @return
     */
    List<TjsscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize);

    /**
     * 获取天津时时彩：历史开奖 - 直选走势
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
	Map<String, Object> getTjsscTrend(Integer number, Integer limit);    
 	
	/**
	 * APP 天津时时彩当天数据
	 * @param type
	 * @param date
	 * @param pageSize 
	 * @param pageNo
	 * @return
	 */
	ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize);

    /**
     * 获取历史开奖之开奖101
     * @param type
     * @param issues 期数
     * @return
     */
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues);

    /**
     * 根据期数获取开奖结果
     * @param issues 期数
     * @return
     */
    List<TjsscLotterySg> getSgByIssues(Integer issues);
	   
	
}
