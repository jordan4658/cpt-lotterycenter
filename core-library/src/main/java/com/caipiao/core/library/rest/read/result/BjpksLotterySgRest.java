package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.*;
import com.caipiao.core.library.vo.result.BjpksSgVO;
import com.caipiao.core.library.vo.web.LhcCountVO;
import com.mapper.domain.BjpksCountSgds;
import com.mapper.domain.BjpksCountSgdx;
import com.mapper.domain.BjpksCountSglh;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface BjpksLotterySgRest {

    /**
     * 获取今日号码资讯
     * @param type
     * @return
     */
    @PostMapping("/bjpksSg/todayNumber.json")
    ResultInfo<List<MapListVO>> todayNumber(@RequestParam("type") String type);

    /**
     * 获取冷热分析资讯
     * @param type
     * @param issue
     * @return
     */
    @PostMapping("/bjpksSg/lengRe.json")
    ResultInfo<List<MapListVO>> lengRe(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取两面长龙
     * @param type
     * @return
     */
    @PostMapping("/bjpksSg/liangMianC.json")
    ResultInfo<List<ThereMemberVO>> liangMianC(@RequestParam("type") String type);

    /**
     * 获取冠军和路珠
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/bjpksSg/luzhuG.json")
    ResultInfo<Map<String, ThereMemberListVO>> luzhuG(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取前后路珠371
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/bjpksSg/luzhuQ.json")
    ResultInfo<Map<String,ThereMemberListVO>> luzhuQ(@RequestParam("type") String type, @RequestParam("date") String date);

    /**
     * 获取历史开奖
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/bjpksSg/historySg.json")
    ResultInfo<List<KjlsVO>> historySg(@RequestParam("type") String type, @RequestParam("date") String date,
                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    /**
     * 获取两面历史之大小
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/bjpksSg/lianMianDx.json")
    ResultInfo<List<BjpksCountSgdx>> lianMianDx(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面历史之单双
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/bjpksSg/lianMianDs.json")
    ResultInfo<List<BjpksCountSgds>> lianMianDs(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面历史之龙虎
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/bjpksSg/lianMianLh.json")
    ResultInfo<List<BjpksCountSglh>> lianMianLh(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取号码遗漏
     * @return
     */
    @PostMapping("/bjpksSg/numNoOpen.json")
    ResultInfo<List<MapListVO>> numNoOpen();

    /**
     * 获取冠亚和统计
     * @return
     */
    @PostMapping("/bjpksSg/guanYaCount.json")
    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount();

    @PostMapping("/bjpksSg/guanYaCountbySize.json")
    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCountbySize(@RequestParam("size") int size);
    /**
     * 获取横版走势
     * @param issue 期数
     * @return
     */
    @PostMapping("/bjpksSg/getSgTrend.json")
    ResultInfo<List<BjpksSgVO>> getSgTrend(@RequestParam("issue") Integer issue);

    /**
     * 获取北京PK10 免费推荐列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/bjpksSg/recommendList.json")
    ResultInfo<Map<String, Object>> recommendList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取两面遗漏之大小,单双
     * @param type 资讯类型
     * @param way 时间段, 如1代表近一个月
     * @param number 第几位,只能传入1-10
     * @return
     */
    @PostMapping("/bjpksSg/lianMianYl.json")
    ResultInfo<Map<String,ArrayList<MapIntegerVO>>> lianMianYl(@RequestParam("type") String type, @RequestParam("way") String way, @RequestParam("number") Integer number);

    /**
     * 公式杀号
     * @param date 日期
     * @param limit 期数
     * @param number 第几名
     * @return
     */
    @PostMapping("/bjpksSg/killNumber.json")
    ResultInfo<Map<String,Object>> killNumber(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("number") Integer number);

    /**
     * 历史开奖
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/bjpksSg/lishiSg.json")
    ResultInfo<Map<String,Object>> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     * @param sgIssue
     * @return
     */
    @PostMapping("/bjpksSg/sgDetails.json")
    ResultInfo<Map<String,Object>> sgDetails(@RequestParam("sgIssue") String sgIssue);

    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/bjpksSg/getNewestSgInfo.json")
    ResultInfo<Map<String,Object>> getNewestSgInfo();

    /**
     * 当前期的期号与开奖时间
     * @return
     */
    @PostMapping("/bjpksSg/getNowIssueAndTime.json")
    ResultInfo<Map<String,Object>> getNowIssueAndTime();

    /**
     * 获取两面遗漏之大小,单双
     * @param type 资讯类型
     * @param way 时间段, 如1代表近一个月
     * @return
     */
    @PostMapping("/bjpksSg/lianMianYlWeb.json")
    ResultInfo<Map<Integer, ArrayList<Integer>>> lianMianYlWeb(@RequestParam("type") String type, @RequestParam("way") String way);

    /**
     * 两面长龙 web端
     *
     * @param type 资讯类型
     * @param issue 期数
     * @param count 位置, 0 代表 冠亚和
     * @return
     */
    @PostMapping("/bjpksSg/liangMianCWeb.json")
    ResultInfo<List<LhcCountVO>> liangMianCWeb(@RequestParam("type") String type, @RequestParam("issue") Integer issue, @RequestParam("count") Integer count);

    /**
     * 获取web资讯：北京PK10历史开奖
     * @param pageNum  当前第几页
     * @param pageSize  页大小
     * @return
     */
    @PostMapping("/bjpksSg/getPK10HistoryLottery.json")
    ResultInfo<Map<String, Object>> getPK10HistoryLottery(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);


    /**
     *咨询;冠亚和统计
     * @param pageSize 多少期
     * @return
     */
    @PostMapping("/bjpksSg/getYaGuanCount.json")
    ResultInfo<Map<String, Object>> getGuanYaCount(@RequestParam("pageSize") Integer pageSize);



    /**
     * web首页：今日统计
     */
    @PostMapping("/bjpksSg/getBjpksTodayStatistics.json")
    ResultInfo<Map<String, Object>> getBjpksTodayStatistics();


    /**
     * web首页彩票北京PK10今日历史开奖
     * @return
     */
    @PostMapping("/bjpksSg/getBjpksTodayLotteryHistory.json")
    ResultInfo<Map<String, Object>> getBjpksTodayLotteryHistory();


    /**
     * web端pk10开奖资讯详情
     * @param pageNum 第几页
     * @param pageSize  每页条数
     * @param date 日期（选填）
     * @return
     */
    @PostMapping("/bjpksSg/bjpksTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> bjpksTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("date") String date);
}
