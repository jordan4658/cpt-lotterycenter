package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.TxffcMissDTO;
import com.caipiao.live.common.model.dto.result.TxffcSizeMissDTO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.mybatis.entity.TxffcLotterySg;

import java.util.List;
import java.util.Map;

public interface TxffcLotterySgServiceReadSg {

    /**
     * 根据日期获取开奖结果
     * @param date 日期, 如2018-08-21
     * @return
     */
    List<TxffcLotterySg> getSgByDate(String date);

    /**
     * 根据期数获取开奖结果
     * @param issues 期数
     * @return
     */
    List<TxffcLotterySg> getSgByIssues(Integer issues);

    /**
     * 今日统计
     * @param type
     * @param date 日期, 如2018-08-21
     * @return
     */
    ResultInfo<Map<String, Object>> todayCount(String type, String date);

    /**
     * 获取历史开奖之开奖101
     * @param type
     * @param issues 期数
     * @return
     */
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues);

    /**
     * 获取历史开奖之冷热104
     * @param type
     * @return
     */
    ResultInfo<List<MapListVO>> lishiLengRe(String type);

    /**
     * 获取重庆时时彩：遗漏统计
     * @return
     */
    ResultInfo<List<TxffcMissDTO>> getTxffcMissCount(String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(Integer count, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param date 日期
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(String date, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(Integer count, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param date 日期
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(String date, Integer number);

    /**
     * 获取重庆时时彩：历史开奖 - 直选走势
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcTrend(Integer number, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 直选振幅
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcAmplitude(Integer number, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组合走势
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcTrendGroup(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组合跨度
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSpan(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度 - 最大/最小
     * @param number 类型 ： 2 二星 / 3 三星
     * @param type 类型：1 最大 / 2 最小
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSpanMaxMin(Integer number, Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和尾走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSumTail(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 形态
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位 | 6 和值
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffcShape(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 012路
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffc012Way(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 走向
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffcToGo(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 比例
     * @param number 类型：2 二星组选， 3 三星组选
     * @param type 类型 ： 1 大小比  2 奇偶比  3 质合比
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffcRatio(Integer number, Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 三星组选 - 号码类型
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffcNumType(Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和值走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSumVal(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双个数分布
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSizeCount(Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双位置分布
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getTxffcSizePosition(Integer limit);

    /**
     * 重庆时时彩：历史开奖
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> lishiSg(Integer pageNo, Integer pageSize);

    /**
     * 获取期次详情
     * @param issue
     * @return
     */
    ResultInfo<Map<String, Object>> sgDetails(String issue);

    /**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getNewestSgInfo();

    /**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getNewestSgInfoWeb();

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    Map<String, Object> getNowIssueAndTime();




    /*******************************************************
     *****                   WEB端接口                 *****
     ******************************************************/

    /**
     * 获取指定彩种基本走势信息
     * @param number 几星
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getTxffcBaseTrend(Integer number, Integer limit);

    /**
     * 获取历史开奖信息
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     */
    List<TxffcLotterySg> queryHistorySg(Integer pageNo, Integer pageSize);

    /**
     *获取开奖开奖资讯详情
     *
     * @param pageNo
     * @param pageSize
     * @param date
     * @return
     */
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date);


    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     * @return
     */
    ResultInfo<Map<String, Object>> getTxffcTodayCount();

    /**
     * 根据期数查结果
     * @return
     */
	Map<String, Object> getNextTxffcRecommend();

	/**
	 * 获取某天数据
	 * @param type
	 * @param date
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize);

	/**
	 * 获取某天数据
	 * @param date
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<TxffcLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize);
	
    /** 
    * @Title: getJssscSgLong 
    * @Description: 获取比特币分分彩长龙 
    * @return List<Map<String,Object>>
    * @author HANS
    * @date 2019年5月28日下午7:44:03
    */ 
    public List<Map<String, Object>> getTxffcSgLong();

}
