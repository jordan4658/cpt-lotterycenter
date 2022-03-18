package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO2;
import com.caipiao.live.common.mybatis.entity.PceggLotterySg;

import java.util.List;
import java.util.Map;

/**
 * @author ShaoMing
 * @datetime 2018/7/26 16:36
 */
public interface PceggLotterySgServiceReadSg {

	/**
	 * 获取PC蛋蛋【首页信息】
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getSgInfo();

	/**
	 * 获取PC蛋蛋最近一期赛果 web端
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getSgInfoWeb();

	/**
	 * 获取PC蛋蛋下一期开奖剩余时间信息
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getNextIssue();

	/**
	 * 获取PC蛋蛋开奖历史
	 * 
	 * @param date 日期
	 * @return
	 */
	ResultInfo<List<PceggLotterySgDTO>> pceggSgHistoryList(String date, Integer pageNo, Integer pageSize);

	/**
	 * web 获取PC蛋蛋开奖历史
	 * 
	 * @param date 日期
	 * @return
	 */
	ResultInfo<Map<String, Object>> webPcEggSgHistoryList(String date, Integer pageNo, Integer pageSize);

	/**
	 * PC蛋蛋统计
	 * 
	 * @param date 日期
	 * @return
	 */
	ResultInfo<Map<String, Object>> pceggStatistics(String date);

	/**
	 * 获取PC蛋蛋：曲线统计-开奖历史
	 * 
	 * @param pageSize 近几期
	 * @return
	 */
	ResultInfo<List<PceggLotterySgDTO2>> pceggSgHistoryList2(Integer pageSize);

	/**
	 * 获取PC蛋蛋：曲线统计-冷热
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getColdHot();

	/**
	 * 获取PC蛋蛋：曲线统计-遗漏值列表
	 * 
	 * @param region 第几区（只能为：1 | 2 | 3）
	 * @return
	 */
	ResultInfo<Map<String, Object>> getRegionMissingValueList(Integer region, Integer pageSize);

	/**
	 * 获取期次详情
	 * 
	 * @param sgIssue
	 * @return
	 */
	ResultInfo<Map<String, Object>> sgDetails(String sgIssue);

	/**
	 * 当前期的期号与开奖时间
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getNowIssueAndTime();

	/**
	 * web端PC蛋蛋资讯 今日统计
	 * 
	 * @param date
	 * @return
	 */
	ResultInfo<Map<String, Object>> getPcEggWebStatistics(String date);

	/**
	 * 获取PC蛋蛋web端：曲线图特码走势
	 * 
	 * @param pageSize 近几期
	 * @return
	 */
	ResultInfo<Map<String, Object>> getPcEggWebCurveTemaList(Integer pageSize);

	/**
	 * 获取pc蛋蛋web端：今日单个号码出现的次数
	 * 
	 * @return
	 */
	ResultInfo<Map<String, Object>> getPceggLotteryNumber(String date);

	/************************************************************************
	 * 修改后新增接口 **
	 ************************************************************************/

	/**
	 * 获取当前最后一期赛果
	 * 
	 * @return
	 */
	PceggLotterySg queryLastSg(Boolean isOpen);

	/**
	 * 获取当前的下一期开奖期号及倒计时
	 * 
	 * @return
	 */
	PceggLotterySg queryNextSg();

	/**
	 * 获取今日已开期数
	 * 
	 * @return
	 */
	Integer queryOpenedCount();

	/**
	 * 查询下一期开奖时间（时间戳：秒）
	 * 
	 * @return
	 */
	Long queryCountDown();

	/**
	 * 查询指定日期已开历史记录（默认：当天）
	 * 
	 * @param date     日期，例如：2018-11-13
	 * @param sort     排序方式：ASC 顺序 | DESC 倒序
	 * @param pageNo   页码
	 * @param pageSize 每页数量
	 * @return
	 */
	List<PceggLotterySg> querySgList(String date, String sort, Integer pageNo, Integer pageSize);

	/**
	 * 根据期号查询赛果信息
	 * 
	 * @param issue 期号
	 * @return
	 */
	PceggLotterySg querySgByIssue(String issue);

	/**
	 * web 获取pc蛋蛋：开奖资讯详情
	 * 
	 * @param date 日期
	 * @return
	 */
	ResultInfo<Map<String, Object>> getTodayAndHistoryNews(String date);
	
    /** 
    * @Title: getActSgLong 
    * @Description: 获取PC蛋蛋长龙 
    * @return Map<String,Object>
    * @author HANS
    * @date 2019年5月10日上午16:48:40
    */ 
    List<Map<String, Object>> getPceggSgLong();
    
    /** 
    * @Title: lishitenpksSg 
    * @Description: 获取PC蛋蛋的历史开奖数据 
    * @author HANS
    * @date 2019年5月22日上午11:17:39
    */ 
    public ResultInfo<Map<String, Object>> lishitenpksSg(Integer pageNo, Integer pageSize);

}
