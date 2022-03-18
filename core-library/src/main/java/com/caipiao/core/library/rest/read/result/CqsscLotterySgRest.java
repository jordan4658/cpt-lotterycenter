package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.dto.result.CqsscMissDTO;
import com.caipiao.core.library.dto.result.CqsscSizeMissDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.CqsscLotterySg;
import com.mapper.domain.CqsscRecommend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CqsscLotterySgRest {

    /**
     * 今日统计
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/cqsscSg/todayCount.json")
    ResultInfo<Map<String, Object>> todayCount(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取历史开奖之开奖101
     * @param type
     * @param issue 期数
     * @return
     */
    @PostMapping("/cqsscSg/lishiKaiJiang.json")
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(@RequestParam("type") String type, @RequestParam("issue") Integer issue);
    
    /**
	 * 重庆时时彩 - 当天数据
	 * @param type
     * @param pageSize 
     * @param pageNum 
	 * @param string
	 * @return
	 */
    @PostMapping("/cqsscSg/todayData.json")
	ResultInfo<List<Map<String, Object>>> todayData(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
    
    /**
     * 获取历史开奖之冷热104
     * @param type
     * @return
     */
    @PostMapping("/cqsscSg/lishiLengRe.json")
    ResultInfo<List<MapListVO>> lishiLengRe(@RequestParam("type") String type);

    /**
     * 获取重庆时时彩：免费推荐列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscRecommends.json")
    Map<String, Object> getCqsscRecommends(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    /**
     * 获取重庆时时彩：遗漏统计
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscMissCount.json")
    ResultInfo<List<CqsscMissDTO>> getCqsscMissCount(@RequestParam("date") String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscSizeMissCount.json")
    Map<String, List<CqsscSizeMissDTO>> getCqsscSizeMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscSingleMissCount.json")
    Map<String, List<CqsscSizeMissDTO>> getCqsscSingleMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：公式杀号
     * @param date 日期
     * @param num 球号
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscGssh.json")
    Map<String, Object> getCqsscGssh(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("num") Integer num);

    /**
     * 获取重庆时时彩：历史开奖 - 直选走势
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscTrend.json")
    Map<String, Object> getCqsscTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 振幅
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscAmplitude.json")
    Map<String, Object> getCqsscAmplitude(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 组选走势
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscTrendGroup.json")
    Map<String, Object> getCqsscTrendGroup(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度
     * @param type 类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscSpanMaxMin.json")
    Map<String, Object> getCqsscSpan(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度 - 最大/最小
     * @param number 类型 ： 2 二星 / 3 三星
     * @param type 类型：1 最大 / 2 最小
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscSpan.json")
    Map<String, Object> getCqsscSpanMaxMin(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和尾走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscSumTail.json")
    Map<String, Object> getCqsscSumTail(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

//    /**
//     * 获取重庆时时彩：曲线图 - 和值走势
//     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
//     * @param limit 显示条数
//     * @return
//     */
//    @PostMapping("/cqsscSg/getCqsscSumValue.json")
//    Map<String, Object> getCqsscSumValue(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 形态
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位 | 6 后二之和
     * @param limit 近几期
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscShape.json")
    Map<String, Object> getCqsscShape(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 012路
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位 | 6 后二之和
     * @param limit 近几期
     * @return
     */
    @PostMapping("/cqsscSg/getCqssc012Way.json")
    Map<String, Object> getCqssc012Way(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 走向
     * @param type 类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscToGo.json")
    Map<String, Object> getCqsscToGo(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 比例
     * @param number 类型：2 二星组选， 3 三星组选
     * @param type 类型 ： 1 大小比  2 奇偶比  3 质合比
     * @param limit 近几期
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscrRatio.json")
    Map<String, Object> getCqsscRatio(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 三星组选 - 号码类型
     * @param limit 近几期
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscrNumType.json")
    Map<String, Object> getCqsscNumType(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和值走势
     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscrSumVal.json")
    Map<String, Object> getCqsscSumVal(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双个数分布
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscrSizeCount.json")
    Map<String, Object> getCqsscSizeCount(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双位置分布
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscrSizePosition.json")
    Map<String, Object> getCqsscSizePosition(@RequestParam("limit") Integer limit);

    /**
     * 重庆时时彩：历史开奖
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/cqsscSg/lishiSg.json")
    Map<String, Object> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     * @param issue
     * @return
     */
    @PostMapping("/cqsscSg/sgDetails.json")
    ResultInfo<Map<String, Object>> sgDetails(@RequestParam("issue") String issue);

    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/cqsscSg/getNewestSgInfo.json")
    Map<String, Object> getNewestSgInfo();

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    @PostMapping("/cqsscSg/getNowIssueAndTime.json")
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
    @PostMapping("/cqsscSg/getCqsscBaseTrend.json")
    Map<String, Object> getCqsscBaseTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 分页获取开奖历史
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/cqsscSg/queryHistorySg.json")
    List<CqsscLotterySg> queryHistorySg(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 获取重庆时时彩最后一期免费推荐
     * @return
     */
    @PostMapping("/cqsscSg/queryLastRecommend.json")
    CqsscRecommend queryLastRecommend();





    /**
     * web开奖资讯：重庆时时彩今日开奖历史以及历史开奖
     */
    @PostMapping("/cqsscSg/cqsscTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> cqsscTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);

    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     * @return
     */
    @PostMapping("/cqsscSg/cqsscTodayCount.json")
    ResultInfo<Map<String, Object>> getCqsscTodayCount();


    /**
     * web端;重庆时时彩大小遗漏统计
     * @param date 按时间日期统计
     * @param number  球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscWebSizeMissCount.json")
    Map<String, List<CqsscSizeMissDTO>> getCqsscWebSizeMissCount(@RequestParam("date") String date, @RequestParam("number") int number);

    /**
     *
     * 重庆时时彩 - 遗漏统计 - 单双遗漏
     * @param date
     * @param number
     * @return
     */
    @PostMapping("/cqsscSg/getCqsscWebSingleMissCount.json")
    Map<String, List<CqsscSizeMissDTO>> getCqsscWebSingleMissCount(@RequestParam("date") String date, @RequestParam("number") int number);

	
}
