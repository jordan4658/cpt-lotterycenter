package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.*;
import com.caipiao.core.library.vo.result.BjpksSgVO;
import com.caipiao.core.library.vo.web.LhcCountVO;
import com.mapper.domain.XyftCountSgds;
import com.mapper.domain.XyftCountSgdx;
import com.mapper.domain.XyftCountSglh;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface XyftLotterySgRest {

    /**
     * 获取今日号码资讯
     * @param type
     * @return
     */
    @PostMapping("/xyftSg/todayNumber.json")
    ResultInfo<List<MapListVO>> todayNumber(@RequestParam("type") String type);

    /**
     * 获取冷热分析资讯
     * @param type
     * @param issue
     * @return
     */
    @PostMapping("/xyftSg/lengRe.json")
    ResultInfo<List<MapListVO>> lengRe(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取两面长龙
     * @param type
     * @return
     */
    @PostMapping("/xyftSg/liangMianC.json")
    ResultInfo<List<ThereMemberVO>> liangMianC(@RequestParam("type") String type);

    /**
     * 获取冠军和路珠
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/xyftSg/luzhuG.json")
    ResultInfo<Map<String, ThereMemberListVO>> luzhuG(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取前后路珠371
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/xyftSg/luzhuQ.json")
    ResultInfo<Map<String,ThereMemberListVO>> luzhuQ(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取历史开奖
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/xyftSg/historySg.json")
    ResultInfo<List<KjlsVO>> historySg(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面历史之大小
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/lianMianDx.json")
    ResultInfo<List<XyftCountSgdx>> lianMianDx(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面历史之单双
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/lianMianDs.json")
    ResultInfo<List<XyftCountSgds>> lianMianDs(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面历史之龙虎
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/lianMianLh.json")
    ResultInfo<List<XyftCountSglh>> lianMianLh(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取号码遗漏
     * @return
     */
    @PostMapping("/xyftSg/numNoOpen.json")
    ResultInfo<List<MapListVO>> numNoOpen();

    /**
     * 获取冠亚和统计
     * @return
     */
    @PostMapping("/xyftSg/guanYaCount.json")
    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount();

    /**
     * 获取横版走势
     * @return
     */
    @PostMapping("/xyftSg/getSgTrend.json")
    ResultInfo<List<BjpksSgVO>> getSgTrend(@RequestParam("issue") Integer issue);

    /**
     * 免费推荐列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/recommendList.json")
    ResultInfo<Map<String, Object>> recommendList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面遗漏之大小,单双
     * @param type
     * @param way
     * @param number
     * @return
     */
    @PostMapping("/xyftSg/lianMianYl.json")
    ResultInfo<Map<String,ArrayList<MapIntegerVO>>> lianMianYl(@RequestParam("type") String type, @RequestParam("way") String way, @RequestParam("number") Integer number);

    /**
     * 公式杀号
     * @param date 日期
     * @param issue 期数
     * @param number 第几名
     * @return
     */
    @PostMapping("/xyftSg/killNumber.json")
    ResultInfo<Map<String,Object>> killNumber(@RequestParam("date") String date, @RequestParam("issue") Integer issue, @RequestParam("number") Integer number);

    /**
     * 历史开奖
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/lishiSg.json")
    ResultInfo<Map<String,Object>> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     * @param sgIssue
     * @return
     */
    @PostMapping("/xyftSg/sgDetails.json")
    ResultInfo<Map<String,Object>> sgDetails(@RequestParam("sgIssue") String sgIssue);

    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/xyftSg/getNewestSgInfo.json")
    ResultInfo<Map<String,Object>> getNewestSgInfo();

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    @PostMapping("/xyftSg/getNowIssueAndTime.json")
    ResultInfo<Map<String,Object>> getNowIssueAndTime();

    /**
     * 获取两面遗漏之大小,单双
     * @param type 资讯类型
     * @param way 时间段, 如1代表近一个月
     * @return
     */
    @PostMapping("/xyftSg/lianMianYlWeb.json")
    ResultInfo<Map<Integer, ArrayList<Integer>>> lianMianYlWeb(@RequestParam("type") String type, @RequestParam("way") String way);

    /**
     * 两面长龙 web端
     *
     * @param type 资讯类型
     * @param issue 期数
     * @param count 位置, 0 代表 冠亚和
     * @return
     */
    @PostMapping("/xyftSg/liangMianCWeb.json")
    ResultInfo<List<LhcCountVO>> liangMianCWeb(@RequestParam("type") String type, @RequestParam("issue") Integer issue, @RequestParam("count") Integer count);


    /**
     * 获取web资讯：历史开奖
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @PostMapping("/xyftSg/getXyftHistoryLottery.json")
    ResultInfo<Map<String, Object>> getXyftHistoryLottery(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);

    /**
     * 咨讯:幸运飞艇冠亚和统计
     * @param pageSize
     * @return
     */
    @PostMapping("/xyftSg/getXyftGuanYaCount.json")
    ResultInfo<Map<String, Object>> getXyftGuanYaCount(@RequestParam("pageSize") Integer pageSize);

    /**
     * 首页：幸运飞艇
     * @return
     */
    @PostMapping("/xyftSg/getXyftTodayStatistics.json")
    ResultInfo<Map<String, Object>> getXyftTodayStatistics();

    /**
     * web首页彩票幸运飞艇今日历史开奖
     * @return
     */
    @PostMapping("/xyftSg/getXyftTodayLotteryHistory.json")
    ResultInfo<Map<String, Object>> getXyftTodayLotteryHistory();

    /**
     * 幸运飞艇开奖资讯详情
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @PostMapping("/xyftSg/xyftTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);
}
