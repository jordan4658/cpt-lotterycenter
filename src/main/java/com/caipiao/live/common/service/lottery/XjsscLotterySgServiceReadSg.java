package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.XjsscMissDTO;
import com.caipiao.live.common.model.dto.result.XjsscSizeMissDTO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.mybatis.entity.XjsscLotterySg;

import java.util.List;
import java.util.Map;

public interface XjsscLotterySgServiceReadSg {

    /**
     * 根据日期获取开奖结果
     *
     * @param date 日期, 如2018-08-21
     * @return
     */
    List<XjsscLotterySg> getSgByDate(String date);

    /**
     * 根据期数获取开奖结果
     *
     * @param issues 期数
     * @return
     */
    List<XjsscLotterySg> getSgByIssues(Integer issues);

    /**
     * 今日统计
     *
     * @param type
     * @param date 日期, 如2018-08-21
     * @return
     */
    ResultInfo<Map<String, Object>> todayCount(String type, String date);

    /**
     * 获取历史开奖之开奖101
     *
     * @param type
     * @param issues 期数
     * @return
     */
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues);

    /**
     * 获取历史开奖之冷热104
     *
     * @param type
     * @return
     */
    ResultInfo<List<MapListVO>> lishiLengRe(String type);

    /**
     * 获取重庆时时彩：遗漏统计
     *
     * @return
     */
    ResultInfo<List<XjsscMissDTO>> getXjsscMissCount(String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<XjsscSizeMissDTO>> getXjsscSizeMissCount(Integer count, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<XjsscSizeMissDTO>> getXjsscSingleMissCount(Integer count, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     *
     * @param date   日期
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<XjsscSizeMissDTO>> getXjsscSizeMissCount(String date, Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     *
     * @param date   日期
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    Map<String, List<XjsscSizeMissDTO>> getXjsscSingleMissCount(String date, Integer number);

    /**
     * 获取重庆时时彩：历史开奖 - 直选走势
     *
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit  显示条数
     * @return
     */
    Map<String, Object> getXjsscTrend(Integer number, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 直选振幅
     *
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit  显示条数
     * @return
     */
    Map<String, Object> getXjsscAmplitude(Integer number, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组合走势
     *
     * @param type  类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscTrendGroup(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组合跨度
     *
     * @param type  类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscSpan(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度 - 最大/最小
     *
     * @param number 类型 ： 2 二星 / 3 三星
     * @param type   类型：1 最大 / 2 最小
     * @param limit  显示条数
     * @return
     */
    Map<String, Object> getXjsscSpanMaxMin(Integer number, Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和尾走势
     *
     * @param type  类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscSumTail(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 形态
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位 | 6 和值
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getXjsscShape(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 012路
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getXjssc012Way(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 走向
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getXjsscToGo(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 比例
     *
     * @param number 类型：2 二星组选， 3 三星组选
     * @param type   类型 ： 1 大小比  2 奇偶比  3 质合比
     * @param limit  近几期
     * @return
     */
    Map<String, Object> getXjsscRatio(Integer number, Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 三星组选 - 号码类型
     *
     * @param limit 近几期
     * @return
     */
    Map<String, Object> getXjsscNumType(Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和值走势
     *
     * @param type  类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscSumVal(Integer type, Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双个数分布
     *
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscSizeCount(Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双位置分布
     *
     * @param limit 显示条数
     * @return
     */
    Map<String, Object> getXjsscSizePosition(Integer limit);

    /**
     * 重庆时时彩：历史开奖
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize);

    /**
     * 获取期次详情
     *
     * @param issue
     * @return
     */
    ResultInfo<Map<String, Object>> sgDetails(String issue);

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNewestSgInfo();

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNewestSgInfoWeb();

    /**
     * 当前期的期号与开奖时间
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNowIssueAndTime();


    /*******************************************************
     *****                   WEB端接口                 *****
     ******************************************************/

    /**
     * 获取指定彩种基本走势信息
     *
     * @param number 几星
     * @param limit  近几期
     * @return
     */
    Map<String, Object> getXjsscBaseTrend(Integer number, Integer limit);

    /**
     * 获取历史开奖信息
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return
     */
    List<XjsscLotterySg> queryHistorySg(Integer pageNo, Integer pageSize);

    /**
     * 获取当前最后一期赛果
     *
     * @return
     */
    XjsscLotterySg queryXjsscLastSg();

    /**
     * 获取资讯开奖详情
     *
     * @param date
     * @return
     */
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer PageSize, String date);

    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getXjsscTodayCount();

	/**
	 * 获取某天数据
	 * @param pageSize 
	 * @param pageNo
	 * @param date 
	 * @param type 
	 * @return
	 */
	ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize);
	
	/**
	 * 获取某天数据
	 * @param pageSize 
	 * @param pageNo
	 * @param date 
	 * @return
	 */
	List<XjsscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize);
	
	/** 
	* @Title: getxjsscSgLong 
	* @Description: 获取新疆时时彩长龙 
	* @author HANS
	* @date 2019年5月17日下午2:41:22
	*/ 
	public List<Map<String, Object>> getxjsscSgLong();

}
