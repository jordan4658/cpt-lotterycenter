package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.dto.result.XjsscMissDTO;
import com.caipiao.core.library.dto.result.XjsscSizeMissDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.XjsscLotterySg;
import com.mapper.domain.XjsscRecommend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface XjsscLotterySgRest {

    /**
     * 今日统计
     *
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/xjsscSg/todayCount.json")
    ResultInfo<Map<String, Object>> todayCount(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取历史开奖之开奖101
     *
     * @param type
     * @param issue 期数
     * @return
     */
    @PostMapping("/xjsscSg/lishiKaiJiang.json")
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(@RequestParam("type") String type, @RequestParam("issue") Integer issue);
    
    /**
     * 获取某天开奖数据
     * @param type
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xjsscSg/todayData.json")
    ResultInfo<List<Map<String, Object>>> todayData(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
    
    /**
     * 获取历史开奖之冷热104
     *
     * @param type
     * @return
     */
    @PostMapping("/xjsscSg/lishiLengRe.json")
    ResultInfo<List<MapListVO>> lishiLengRe(@RequestParam("type") String type);

    /**
     * 获取重庆时时彩：免费推荐列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscRecommends.json")
    Map<String, Object> getXjsscRecommends(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    /**
     * 获取重庆时时彩：遗漏统计
     *
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscMissCount.json")
    ResultInfo<List<XjsscMissDTO>> getXjsscMissCount(@RequestParam("date") String date);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSizeMissCount.json")
    Map<String, List<XjsscSizeMissDTO>> getXjsscSizeMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSingleMissCount.json")
    Map<String, List<XjsscSizeMissDTO>> getXjsscSingleMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-大小遗漏
     *
     * @param date   日期
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSizeMissCountByDate.json")
    Map<String, List<XjsscSizeMissDTO>> getXjsscSizeMissCount(@RequestParam("date") String date, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：遗漏统计-单双遗漏
     *
     * @param date   近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSingleMissCountByDate.json")
    Map<String, List<XjsscSizeMissDTO>> getXjsscSingleMissCount(@RequestParam("date") String date, @RequestParam("number") Integer number);

    /**
     * 获取重庆时时彩：公式杀号
     *
     * @param date  日期
     * @param num   球号
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscGssh.json")
    Map<String, Object> getXjsscGssh(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("num") Integer num);

    /**
     * 获取重庆时时彩：历史开奖 - 直选走势
     *
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit  显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscTrend.json")
    Map<String, Object> getXjsscTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 振幅
     *
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit  显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscAmplitude.json")
    Map<String, Object> getXjsscAmplitude(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：历史开奖 - 组选走势
     *
     * @param type  类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscTrendGroup.json")
    Map<String, Object> getXjsscTrendGroup(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度
     *
     * @param type  类型：2 二星组选， 3 三星组选
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSpanMaxMin.json")
    Map<String, Object> getXjsscSpan(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 组选跨度 - 最大/最小
     *
     * @param number 类型 ： 2 二星 / 3 三星
     * @param type   类型：1 最大 / 2 最小
     * @param limit  显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSpan.json")
    Map<String, Object> getXjsscSpanMaxMin(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和尾走势
     *
     * @param type  类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSumTail.json")
    Map<String, Object> getXjsscSumTail(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

//    /**
//     * 获取重庆时时彩：曲线图 - 和值走势
//     * @param type 类型 ： 2 二星 | 3 三星 | 5 五星
//     * @param limit 显示条数
//     * @return
//     */
//    @PostMapping("/xjsscSg/getXjsscSumValue.json")
//    Map<String, Object> getXjsscSumValue(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 形态
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscShape.json")
    Map<String, Object> getXjsscShape(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 012路
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/xjsscSg/getXjssc012Way.json")
    Map<String, Object> getXjssc012Way(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 走向
     *
     * @param type  类型 ： 1 万位 | 2 千位 | 3 百位 | 4 十位 | 5 个位
     * @param limit 近几期
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscToGo.json")
    Map<String, Object> getXjsscToGo(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 比例
     *
     * @param number 类型：2 二星组选， 3 三星组选
     * @param type   类型 ： 1 大小比  2 奇偶比  3 质合比
     * @param limit  近几期
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscRatio.json")
    Map<String, Object> getXjsscRatio(@RequestParam("number") Integer number, @RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 三星组选 - 号码类型
     *
     * @param limit 近几期
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscNumType.json")
    Map<String, Object> getXjsscNumType(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 和值走势
     *
     * @param type  类型 ： 2 二星 | 3 三星 | 5 五星
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSumVal.json")
    Map<String, Object> getXjsscSumVal(@RequestParam("type") Integer type, @RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双个数分布
     *
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSizeCount.json")
    Map<String, Object> getXjsscSizeCount(@RequestParam("limit") Integer limit);

    /**
     * 获取重庆时时彩：曲线图 - 大小单双位置分布
     *
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/xjsscSg/getXjsscSizePosition.json")
    Map<String, Object> getXjsscSizePosition(@RequestParam("limit") Integer limit);

    /**
     * 重庆时时彩：历史开奖
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xjsscSg/lishiSg.json")
    ResultInfo<Map<String, Object>> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     *
     * @param issue
     * @return
     */
    @PostMapping("/xjsscSg/sgDetails.json")
    ResultInfo<Map<String, Object>> sgDetails(@RequestParam("issue") String issue);

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    @PostMapping("/xjsscSg/getNewestSgInfo.json")
    ResultInfo<Map<String, Object>> getNewestSgInfo();

    /**
     * 当前期的期号与开奖时间
     *
     * @return
     */
    @PostMapping("/xjsscSg/getNowIssueAndTime.json")
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
    @PostMapping("/xjsscSg/getXjsscBaseTrend.json")
    Map<String, Object> getXjsscBaseTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);

    /**
     * 分页获取开奖历史
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/xjsscSg/queryHistorySg.json")
    List<XjsscLotterySg> queryHistorySg(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 获取重庆时时彩最后一期免费推荐
     *
     * @return
     */
    @PostMapping("/xjsscSg/queryLastRecommend.json")
    XjsscRecommend queryLastRecommend();


    /**
     * 获取开奖开奖资讯详情
     *
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @PostMapping("/xjsscSg/xjsscTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);


    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     *
     * @return
     */
    @PostMapping("/xjsscSg/xjsscTodayCount.json")
    ResultInfo<Map<String, Object>> getXjsscTodayCount();

	
}
