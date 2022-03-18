package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.dto.result.PceggLotterySgDTO;
import com.caipiao.core.library.dto.result.PceggLotterySgDTO2;
import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * PC蛋蛋的资讯接口
 *
 * @author ShaoMing
 * @datetime 2018/7/26 16:32
 */
@FeignClient(name = BUSINESS_READ)
public interface PceggLotterySgRest {

    /**
     * 获取PC蛋蛋【首页信息】
     * @return
     */
    @PostMapping("/pceggSg/getSgInfo.json")
    ResultInfo<Map<String,Object>> getSgInfo();

    /**
     * 获取PC蛋蛋下一期开奖剩余时间信息
     * @return
     */
    @PostMapping("/pceggSg/getNextIssue.json")
    ResultInfo<Map<String,Object>> getNextIssue();

    /**
     * 获取PC蛋蛋开奖历史
     * @param date 日期
     * @return
     */
    @PostMapping("/pceggSg/pcEggSgHistoryList.json")
    ResultInfo<List<PceggLotterySgDTO>> pcEggSgHistoryList(@RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
      * WEB获取PC蛋蛋开奖历史
     * @param date 日期
     * @return
     */
    @PostMapping("/pceggSg/webPcEggSgHistoryList.json")
    ResultInfo<Map<String, Object>> webPcEggSgHistoryList(@RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * PC蛋蛋统计
     * @param date 日期
     * @return
     */
    @PostMapping("/pceggSg/pcEggStatistics.json")
    ResultInfo<Map<String, Object>> pcEggStatistics(@RequestParam("date") String date);

    /**
     * 获取PC蛋蛋：曲线统计-开奖历史
     * @param pageSize 近几期
     * @return
     */
    @PostMapping("/pceggSg/getSgHistoryList2.json")
    ResultInfo<List<PceggLotterySgDTO2>> pcEggSgHistoryList2(@RequestParam("pageSize") Integer pageSize);

    /**
     * 获取PC蛋蛋：曲线统计-冷热
     * @return
     */
    @PostMapping("/pceggSg/getColdHot.json")
    ResultInfo<Map<String,Object>> getColdHot();

    /**
     * 获取PC蛋蛋：曲线统计-遗漏值列表
     * @param region 第几区（只能为：1 | 2 | 3）
     * @return
     */
    @PostMapping("/pceggSg/getRegionMissingValueList.json")
    ResultInfo<Map<String, Object>> getRegionMissingValueList(@RequestParam("region") Integer region, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取PC蛋蛋：免费推荐列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/pceggSg/getPceggRecommends.json")
    ResultInfo<Map<String,Object>> getPceggRecommends(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    /**
     * 获取期次详情
     * @param sgIssue
     * @return
     */
    @PostMapping("/pceggSg/sgDetails.json")
    ResultInfo<Map<String,Object>> sgDetails(@RequestParam("sgIssue") String sgIssue);

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    @PostMapping("/pceggSg/getNowIssueAndTime.json")
    ResultInfo<Map<String,Object>> getNowIssueAndTime();


    /**
     * web端PC蛋蛋资讯 今日统计
     * @param date 日期
     * @return
     */
    @PostMapping("/pceggSgss/getPcEggWebStatistics.json")
    ResultInfo<Map<String, Object>> getPcEggWebStatistics(@RequestParam("date") String date);

    /**
     * 获取PC蛋蛋：曲线图--特码走势
     * @param pageSize
     * @return
     */
    @PostMapping("/pceggSgss/getPcEggWebCurveTemaList.json")
    ResultInfo<Map<String,Object>> getPcEggWebCurveTemaList(@RequestParam("pageSize") Integer pageSize);


    /**
     * 获取pc蛋蛋web端：投注参考
     * @return
     */
    @PostMapping("/pceggSgss/getPcEggRecommendWebReference.json")
    ResultInfo<Map<String, Object>> getPcEggRecommendWebReference();

    /**
     * 获取pc蛋蛋web端：今日单个号码出现的次数
     * @return
     */
    @PostMapping("/pceggSgss/getPceggLotteryNumber.json")
    ResultInfo<Map<String, Object>> getPceggLotteryNumber(@RequestParam("date") String date);


    /**
     * 获取pc蛋蛋web端：开奖资讯详情
     * @param date 日期
     * @return
     */
    @PostMapping("/pceggSgss/getTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(@RequestParam("date") String date);
}
