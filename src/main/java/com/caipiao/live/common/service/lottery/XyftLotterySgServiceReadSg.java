package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.MapListVO;

import java.util.List;
import java.util.Map;

public interface XyftLotterySgServiceReadSg {

	/**
	 * 获取今日号码资讯
	 *
	 * @param type
	 * @return
	 */
	ResultInfo<List<MapListVO>> todayNumber(String type);

	/**
	 * 获取历史开奖
	 *
	 * @param type
	 * @param date
	 * @return
	 */
	ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize);

	/**
	 * 获取横版走势
	 *
	 * @param issue
	 * @return
	 */
	ResultInfo<List<BjpksSgVO>> getSgTrend(Integer issue);

	/**
	 * 历史开奖
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize);

	/**
	 * 获取期次详情
	 *
	 * @param sgIssue
	 * @return
	 */
	ResultInfo<Map<String, Object>> sgDetails(String sgIssue);

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


	/**
	 * 获取web资讯：幸运飞艇历史开奖
	 *
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @param date     日期
	 * @return
	 */
	ResultInfo<Map<String, Object>> getXyftHistoryLottery(Integer pageNo, Integer pageSize, String date);


	/**
	 * web资讯：幸运飞艇冠亚和统计
	 *
	 * @param pageSize
	 * @return
	 */
	ResultInfo<Map<String, Object>> getXyftGuanYaCount(Integer pageSize);

	/**
	 * 首页幸运飞艇
	 *
	 * @return
	 */
	ResultInfo<Map<String, Object>> getXyftTodayStatistics();


	/**
	 * web首页彩票幸运飞艇今日历史开奖
	 *
	 * @return
	 */
	ResultInfo<Map<String, Object>> getXyftTodayLotteryHistory();

	/**
	 * 幸运飞艇开奖资讯详情
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param date
	 * @return
	 */
	ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date);


	/**
	 * @Title: queryTotal
	 * @Description: 统计大小单双，3-19出现的总次数
	 * @author HANS
	 * @date 2019年5月29日下午10:11:09
	 */
	Map<String, Object> queryTotal(String date);

	/**
	 * @Title: getXyftPksSgLong
	 * @Description: 统计幸运飞艇长龙数据
	 * @author HANS
	 * @date 2019年5月29日下午10:12:44
	 */
	List<Map<String, Object>> getXyftPksSgLong();

}
