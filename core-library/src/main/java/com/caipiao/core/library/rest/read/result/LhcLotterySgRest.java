package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.caipiao.core.library.vo.common.MapStringVO;
import com.caipiao.core.library.vo.common.MapVO;
import com.caipiao.core.library.vo.lottery.LhcPhotoListVO;
import com.caipiao.core.library.vo.lotterymanage.LhcLotterySgVO;
import com.caipiao.core.library.vo.result.LhcSgDTO;
import com.caipiao.core.library.vo.web.LhcCountVO;
import com.caipiao.core.library.vo.web.LhcLskjVO;
import com.caipiao.core.library.vo.web.LhcWsdxVO;
import com.mapper.domain.LhcHandicap;
import com.mapper.domain.LotteryPlaySetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * 六合彩的资讯接口
 */
@FeignClient(name = BUSINESS_READ)
public interface LhcLotterySgRest {

    /**
     * 获取六合彩资讯,如:波色特码，波色正码，特码两面，特码尾数，正码尾数, 正码总分
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcInformation.json")
    ResultInfo<List<MapVO>> lhcInformation(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取六合彩资讯,如:特码历史621,正码历史622
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcInfo.json")
    ResultInfo<List<List<MapVO>>> lhcInfo(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 取六合彩资讯,如:尾数大小,连码走势,连肖走势
     *
     * @param type 类型
     * @param year 年份
     * @return
     */
    @PostMapping("/lhcSg/lhcInfoC.json")
    ResultInfo<List<List<String>>> lhcInfoC(@RequestParam("type") String type, @RequestParam("year") String year);

    /**
     * 获取六合彩资讯,如:生肖特码,生肖正码
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcInfoD.json")
    ResultInfo<List<MapVO>> lhcInfoD(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取六合彩资讯,如:资讯统计
     *
     * @param type
     * @param issue
     * @return
     */
    @PostMapping("/lhcSg/lhcInfoE.json")
    ResultInfo<List<MapListVO>> lhcInfoE(@RequestParam("type") String type, @RequestParam("issue") Integer issue);


    /***
     * 获取六合彩资讯,如:历史开奖
     * @param type
     * @param year
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/lhcSg/lhcInfoF.json")
    ResultInfo<List<List<MapStringVO>>> lhcInfoF(@RequestParam("type") String type, @RequestParam("year") String year, @RequestParam("sort") Integer sort, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取六合彩资讯,如:六合图库分类
     *
     * @param count 父级id
     * @return
     */
    @PostMapping("/lhcSg/getPhotoCategory.json")
    ResultInfo<Map<String, Object>> getPhotoCategory(@RequestParam("count") Integer count);

    /**
     * 获取六合彩资讯,如:六合图库
     *
     * @param type
     * @return
     */
    @PostMapping("/lhcSg/getphotoA.json")
    ResultInfo<List<LhcPhotoListVO>> getphotoA(@RequestParam("type") String type);

    /**
     * AI智能选号
     *
     * @param date
     * @param dateb
     * @return
     */
    @PostMapping("/lhcSg/getAiNum.json")
    ResultInfo<List<Integer>> getAiNum(@RequestParam("date") String date, @RequestParam("dateb") String dateb);

    /**
     * 开奖列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/lhcSg/lishiSg.json")
    ResultInfo<Map<String, Object>> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取期次详情
     *
     * @param date
     * @return
     */
    @PostMapping("/lhcSg/sgDetails.json")
    ResultInfo<Map<String, Object>> sgDetails(@RequestParam("date") String date);

    /**
     * app : 最近一期的开奖结果
     *
     * @return
     */
    @PostMapping("/lhcSg/getNewestSgInfo.json")
    ResultInfo<Map<String, Object>> getNewestSgInfo();

    /**
     * 后台 : 最近一期的开奖结果
     *
     * @return
     */
    @PostMapping("/lhcSg/getNewestSg.json")
    LhcLotterySgVO getNewestSg();

    /**
     * 公式杀号
     *
     * @param issue
     * @return
     */
    @PostMapping("/lhcSg/killNumber.json")
    ResultInfo<Map<String, Object>> killNumber(@RequestParam("issue") Integer issue);

    /**
     * 开奖日历
     *
     * @param date
     * @return
     */
    @PostMapping("/lhcSg/startlottoDate.json")
    ResultInfo<Map<String, Object>> startlottoDate(@RequestParam("date") String date);

    /**
     * 当前期的期号与开奖时间
     *
     * @return
     */
    @PostMapping("/lhcSg/getNowIssueAndTime.json")
    ResultInfo<Map<String, Object>> getNowIssueAndTime();

    /**
     * web端六合彩历史开奖
     *
     * @param year 年份
     * @return
     */
    @PostMapping("/lhcSg/lhcWebLskj.json")
    ResultInfo<List<LhcLskjVO>> lhcWebLskj(@RequestParam("year") String year);

    /**
     * 获取六合彩资讯,如:特码历史621,正码历史622
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcWebInfoB.json")
    ResultInfo<Map<String, List<MapVO>>> lhcWebInfoB(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取六合彩资讯,如:波色特码，波色正码，特码尾数，正码尾数
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcWebInformation.json")
    ResultInfo<Map<String, List<MapVO>>> lhcWebInformation(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取六合彩资讯,如:特码两面, 正码总分
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcWebInfoH.json")
    ResultInfo<List<LhcCountVO>> lhcWebInfoH(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 获取六合彩资讯,如:生肖特码,生肖正码
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    @PostMapping("/lhcSg/lhcWebInfoD.json")
    ResultInfo<Map<String, List<MapVO>>> lhcWebInfoD(@RequestParam("type") String type, @RequestParam("issue") Integer issue);

    /**
     * 取六合彩资讯,如:尾数大小,连码走势,连肖走势
     *
     * @param type 类型
     * @param year 年份
     * @return
     */
    @PostMapping("/lhcSg/lhcWebInfoC.json")
    ResultInfo<List<LhcWsdxVO>> lhcWebInfoC(@RequestParam("type") String type, @RequestParam("year") String year);

    /**
     * 查询助手
     *
     * @param year     年份
     * @param sort     排序 -1 降序 | 1 升序
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/lhcSg/queryHelper.json")
    List<LhcSgDTO> queryHelper(@RequestParam("year") String year, @RequestParam(name = "sort", defaultValue = "-1") Integer sort, @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);


    /**
     * web首页六合彩特码历史统计和生肖历史统计
     *
     * @return
     */
    @PostMapping("/lhcSg/getTeMaAndShenXiaoDistory.json")
    ResultInfo<Map<String, Object>> getTeMaAndShenXiaoDistory(@RequestParam("issue") Integer issue);

    /**
     * 六合彩开奖资讯详情
     *
     * @param pageNum
     * @param pageSize
     * @param year
     * @return
     */
    @PostMapping("/lhcSg/lhcTodayAndHistoryNews.json")
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("year") String year);

    /**
     * 查询下一期开盘时间
     *
     * @param issue 当前期号
     * @return
     */
    @PostMapping("/lhcSg/selectNextHandicap.json")
    LhcHandicap selectNextHandicap(@RequestParam("issue") String issue);

    /**
     * 查询配置信息
     *
     * @param playId 玩法id
     * @return
     */
    @PostMapping("/lhcSg/selectSetting")
    LotteryPlaySetting selectSetting(@RequestParam("playId") Integer playId);

    /**
     * 分页获取六合彩近期赛果数据
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/lhcSg/queryHistories.json")
    List<LhcLotterySgVO> queryHistories(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/lhcSg/getLhPhotoInfo.json")
    ResultInfo<List<Map<String, Object>>> getLhPhotoInfo(@RequestParam("id") Integer id, @RequestParam("issue") Integer issue, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
    
    @PostMapping("/lhcSg/getWenLhPhotoInfo.json")
    ResultInfo<List<Map<String, Object>>> getWebLhPhotoInfo(@RequestParam("id") Integer id, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
}
