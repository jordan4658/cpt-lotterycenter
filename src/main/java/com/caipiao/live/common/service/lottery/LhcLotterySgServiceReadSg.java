package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.RequestInfo;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.BeadWayAttributeReturn;
import com.caipiao.live.common.model.dto.result.BeadWayColorReturn;
import com.caipiao.live.common.model.dto.result.BeadWayFiveReturn;
import com.caipiao.live.common.model.dto.result.LhcSgDTO;
import com.caipiao.live.common.model.dto.result.ThreeWayDTO;
import com.caipiao.live.common.model.vo.LhcWsdxVO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.MapStringVO;
import com.caipiao.live.common.model.vo.MapVO;
import com.caipiao.live.common.model.vo.lottery.LhcCountVO;
import com.caipiao.live.common.model.vo.lottery.LhcLotterySgVO;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;

import java.util.List;
import java.util.Map;

public interface LhcLotterySgServiceReadSg {

    /**
     * 获取六合彩资讯,如:波色特码，波色正码，特码两面，特码尾数，正码尾数, 正码总分
     *
     * @param type
     * @param issue
     * @return
     */
    ResultInfo<List<MapVO>> lhcInformation(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:特码历史,正码历史
     *
     * @param type
     * @param issue
     * @return
     */
    ResultInfo<List<List<MapVO>>> lhcInfo(String type, Integer issue);

    /**
     * 取六合彩资讯,如:尾数大小,连码走势,连肖走势
     *
     * @param type
     * @param year
     * @return
     */
    ResultInfo<List<List<String>>> lhcInfoC(String type, String year);

    /**
     * 获取六合彩资讯,如:生肖特码,生肖正码
     *
     * @param type
     * @param issue
     * @return
     */
    ResultInfo<List<MapVO>> lhcInfoD(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:资讯统计
     *
     * @param type
     * @param issue
     * @return
     */
    ResultInfo<List<MapListVO>> lhcInfoE(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:历史开奖
     * @param lhcLotterySgVO
     * @return
     */
    ResultInfo<List<List<MapStringVO>>> lhcInfoF(LhcLotterySgVO lhcLotterySgVO);

    /**
     * AI智能选号
     *
     * @param date
     * @param dateb
     * @return
     */
    ResultInfo<List<Integer>> getAiNum(String date, String dateb);

    /**
     * 开奖列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize);

    /**
     * 获取期次详情
     *
     * @param date
     * @return
     */
    ResultInfo<Map<String, Object>> sgDetails(String date);

    /**
     * APP : 最近一期的开奖结果
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNewestSgInfo();

    /**
     * Web : 最近一期的开奖结果
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNewestSgInfoWeb();

    /**
     * 后台 : 最近一期的开奖结果
     *
     * @return
     */
    LhcLotterySgVO getNewestSg();

    /**
     * 当前期的期号与开奖时间
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNowIssueAndTime();

    /**
     * 获取六合彩资讯,如:特码历史621,正码历史622
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    ResultInfo<Map<String, List<MapVO>>> lhcWebInfoB(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:波色特码，波色正码，特码尾数，正码尾数
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    ResultInfo<Map<String, List<MapVO>>> lhcWebInformation(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:特码两面，正码总分
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    ResultInfo<List<LhcCountVO>> lhcWebInfoH(String type, Integer issue);

    /**
     * 获取六合彩资讯,如:生肖特码,生肖正码
     *
     * @param type  类型
     * @param issue 期数
     * @return
     */
    ResultInfo<Map<String, List<MapVO>>> lhcWebInfoD(String type, Integer issue);

    /**
     * 取六合彩资讯,如:尾数大小,连码走势,连肖走势
     *
     * @param type 类型
     * @param year 年份
     * @return
     */
    ResultInfo<List<LhcWsdxVO>> lhcWebInfoC(String type, String year);

    /**
     * 查询助手
     *
     * @param year     年份
     * @param sort     排序 -1 降序 | 1 升序
     * @param pageNo  页码
     * @param pageSize 每页数量
     * @return
     */
    List<LhcSgDTO> queryHelper(String year, Integer sort, Integer pageNo, Integer pageSize);

    /**
     * web首页六合彩特码历史统计和生肖历史统计
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getTeMaAndShenXiaoDistory(Integer issue);

    /**
     * 六合彩开奖资讯详情
     *
     * @param pageNo
     * @param pageSize
     * @param year
     * @return
     */
    ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer pageSize, String year);

    /**
     * 查询配置信息
     *
     * @param palyId
     * @return
     */
    LotteryPlaySetting selectSetting(Integer palyId);

    /**
     * 分页获取六合彩近期赛果数据
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return
     */
    List<LhcLotterySgVO> queryHistories(Integer pageNo, Integer pageSize);

    /**
     * 获取特码家禽野兽资讯
     *
     * @return
     */
    ResultInfo<Map<String, Object>> specialPoultryOrBeast();

    /**
     * 获取特码 1-6 正特单双资讯
     *
     * @param type
     * @return
     */
    ResultInfo<Map<String, Object>> lhcSingleOrDouble(Integer type);

    /**
     * 获取特码 1-6 正特合单双资讯
     *
     * @param type
     * @return
     */
    ResultInfo<Map<String, Object>> lhcJoinSingleOrDouble(Integer type);

    /**
     * 获取特码 1-6 正特尾大小合资讯
     *
     * @param type
     * @return
     */
    ResultInfo<Map<String, Object>> lhcTailBigOrSmall(Integer type);

    /**
     * 获取特码 1-6 正特大小资讯
     *
     * @param type
     * @return
     */
    ResultInfo<Map<String, Object>> lhcBigOrSmall(Integer type);

    /**
     * 获取正码龙虎
     *
     * @return
     */
    ResultInfo<Map<String, Object>> lhcDragonOrTiger();

    /**
     * 遗漏统计
     *
     * @param type
     * @return
     */
    ResultInfo<List<Map<String, Integer>>> lhcOmitCensus(Integer type);

    /**
     * 波色统计
     *
     * @param type
     * @return
     */
    ResultInfo<Map<String, Object>> lhcWaveColor(Integer type);

    /**
     * 生肖
     *
     * @param type
     * @return
     */
    ResultInfo<List<Map<String, Object>>> lhcAttribute(Integer type);

    /**
     * 五行
     *
     * @return
     */
    ResultInfo<Map<String, Object>> lhcFiveElements();

}
