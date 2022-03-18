package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.dto.result.TxffcMissDTO;
import com.caipiao.core.library.dto.result.TxffcSizeMissDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.TxffcLotterySg;
import com.mapper.domain.TxffcRecommend;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface TxffcLotterySgRest {

    /**
     * 今日统计
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/txffcSg/todayCount.json")
    ResultInfo<Map<String, Object>> todayCount(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取历史开奖之开奖101
     * @param type
     * @param issue 期数
     * @return
     */
    @PostMapping("/txffcSg/lishiKaiJiang.json")
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取历史开奖之冷热104
     * @param type
     * @return
     */
    @PostMapping("/txffcSg/lishiLengRe.json")
    ResultInfo<List<MapListVO>> lishiLengRe(@RequestParam("type") String type);

    /**
     * 获取重庆时时彩：免费推荐列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/txffcSg/getTxffcRecommends.json")
    Map<String, Object> getTxffcRecommends(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    /**
     * 获取重庆时时彩：遗漏统计
     * @return
     */
    @PostMapping("/txffcSg/getTxffcMissCount.json")
    ResultInfo<List<TxffcMissDTO>> getTxffcMissCount(@RequestParam("date") String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSizeMissCount.json")
    Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSingleMissCount.json")
    Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param date 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSizeMissCountByDate.json")
    Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(@RequestParam("date") String date, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param date 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSingleMissCountByDate.json")
    Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(@RequestParam("date") String date, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：公式杀号
     * @param date 日期
     * @param num 球号
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcGssh.json")
    Map<String, Object> getTxffcGssh(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("num") Integer num);

    /**
     * 获取重庆时时彩：历史开奖 - 直选走势
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcTrend.json")
    Map<String, Object> getTxffcTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 振幅
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcAmplitude.json")
    Map<String, Object> getTxffcAmplitude(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 组选走势
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcTrendGroup.json")
    Map<String, Object> getTxffcTrendGroup(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSpanMaxMin.json")
    Map<String, Object> getTxffcSpan(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度 - 最大/最小
     * @param number 类型 ： 2 二星 / 3 三星
     * @param type 类型：1 最大 / 2 最小
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSpan.json")
    Map<String, Object> getTxffcSpanMaxMin(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和尾走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSumTail.json")
    Map<String, Object> getTxffcSumTail(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

//    /**
//     * 获取重庆时时彩：曲线图 - 和值走势
//     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
//     * @param limit 显示条数
//     * @return
//     */
//    @PostMapping("/txffcSg/getTxffcSumValue.json")
//    Map<String, Object> getTxffcSumValue(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 形态
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/txffcSg/getTxffcShape.json")
    Map<String, Object> getTxffcShape(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 012路
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/txffcSg/getTxffc012Way.json")
    Map<String, Object> getTxffc012Way(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 走向
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/txffcSg/getTxffcToGo.json")
    Map<String, Object> getTxffcToGo(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 比例
     * @param number 类型：2 二星组选， 3 三星组选
     * @param type 类型 ： 1 大小比  2 奇偶比  3 质合比
     * @param limit 近几期
     * @return
     */
    @PostMapping("/txffcSg/getTxffcRatio.json")
    Map<String, Object> getTxffcRatio(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 三星组选 - 号码类型
     * @param limit 近几期
     * @return
     */
    @PostMapping("/txffcSg/getTxffcNumType.json")
    Map<String, Object> getTxffcNumType(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和值走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSumVal.json")
    Map<String, Object> getTxffcSumVal(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双个数分布
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSizeCount.json")
    Map<String, Object> getTxffcSizeCount(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双位置分布
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/txffcSg/getTxffcSizePosition.json")
    Map<String, Object> getTxffcSizePosition(@RequestParam("limit") Integer limit);

    /**
     * 重庆时时彩：历史开奖
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/txffcSg/lishiSg.json")
    Map<String, Object> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     * @param issue
     * @return
     */
    @PostMapping("/txffcSg/sgDetails.json")
    ResultInfo<Map<String, Object>> sgDetails(@RequestParam("issue") String issue);

    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/txffcSg/getNewestSgInfo.json")
    Map<String, Object> getNewestSgInfo();

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    @PostMapping("/txffcSg/getNowIssueAndTime.json")
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
    @PostMapping("/txffcSg/getTxffcBaseTrend.json")
    Map<String, Object> getTxffcBaseTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 分页获取开奖历史
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/txffcSg/queryHistorySg.json")
    List<TxffcLotterySg> queryHistorySg(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 获取重庆时时彩最后一期免费推荐
     * @return
     */
    @PostMapping("/txffcSg/queryLastRecommend.json")
    TxffcRecommend queryLastRecommend();

    /**
     *分分彩开奖资讯详情
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @PostMapping("/txffcSg/txffcTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);

    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     * @return
     */
    @PostMapping("/txffcSg/txffcTodayCount.json")
    ResultInfo<Map<String, Object>> getTxffcTodayCount();

   /**
    * 腾讯分分彩开奖结果
    * @return
    */
    @PostMapping("/txffcSg/getNextTxffcRecommend.json")
	Map<String, Object> getNextTxffcRecommend();
    
    /**
     * 腾讯纷纷彩某天结果
     * @param type
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/txffcSg/todayData.json")
    ResultInfo<List<Map<String, Object>>> todayData(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

   
}
