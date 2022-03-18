package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.TensscLotterySg;

import java.util.List;
import java.util.Map;

public interface TensscLotterySgServiceReadSg {

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    Map<String, Object> getTensscNewestSgInfo();

    /**
     * APP 5分时时彩当天数据
     *
     * @param type
     * @param date
     * @param pageSize
     * @param pageNo
     * @return
     */
    ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize);

    /**
     * 根据日期获取开奖分页结果
     *
     * @param
     * @return
     */
    List<TensscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize);

    /**
     * 根据期数获取开奖结果
     *
     * @param issues 期数
     * @return
     */
    List<TensscLotterySg> getSgByIssues(Integer issues);

    /**
     * 获取历史开奖之开奖101
     *
     * @param type
     * @param issues 期数
     * @return
     */
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues);

    /**
     * 今日统计
     *
     * @param type
     * @param date 日期, 如2018-08-21
     * @return
     */
    ResultInfo<Map<String, Object>> todayCount(String type, String date);

    /**
     * 根据日期获取开奖结果
     *
     * @param date 日期, 如2018-08-21
     * @return
     */
    List<TensscLotterySg> getSgByDate(String date);

    /**
     * @Title: getActSgLong
     * @Description: 获取 10分时时彩 长龙
     * @author HANS
     * @date 2019年5月16日下午8:37:28
     */
    public List<Map<String, Object>> getTensscSgLong();

}
