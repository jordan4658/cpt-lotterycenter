package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.MapIntegerVO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.ThereIntegerVO;
import com.caipiao.live.common.model.vo.ThereMemberVO;
import com.caipiao.live.common.model.vo.lottery.LhcCountVO;
import com.caipiao.live.common.model.vo.lottery.ThereMemberListVO;
import com.caipiao.live.common.mybatis.entity.BjpksLotterySg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BjpksLotterySgServiceReadSg {
    /**
     * 获取今日号码资讯
     * @param type
     * @return
     */
    ResultInfo<List<MapListVO>> todayNumber(String type);
    /**
     * 获取冷热分析资讯
     * @param type
     * @param issue
     * @return
     */
    ResultInfo<List<MapListVO>> lengRe(String type, Integer issue);
    /**
     * 获取两面长龙
     * @param type
     * @return
     */
    ResultInfo<List<ThereMemberVO>> liangMianC(String type);
    /**
     * 获取冠军和路珠
     * @param type
     * @param date
     * @return
     */
    ResultInfo<Map<String, ThereMemberListVO>> luzhuG(String type, String date);
    /**
     * 获取前后路珠
     * @param type
     * @param date
     * @return
     */
    ResultInfo<Map<String,ThereMemberListVO>> luzhuQ(String type, String date);
    /**
     * 获取历史开奖
     * @param type
     * @param date
     * @return
     */
    ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize);
    /**
     * 获取号码遗漏
     * @return
     */
    ResultInfo<List<MapListVO>> numNoOpen();
    /**
     * 获取冠亚和统计
     * @return
     */
    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount();
    /**
     * 获取横版走势
     * @param issue
     * @return
     */
    ResultInfo<List<BjpksSgVO>> getSgTrend(Integer issue);
    /**
     * 获取两面遗漏之大小,单双
     * @param type 资讯类型
     * @param way 时间段, 如1代表近一个月
     * @param number 第几位,只能传入1-10
     * @return
     */
    ResultInfo<Map<String,ArrayList<MapIntegerVO>>> lianMianYl(String type, String way, Integer number);
    /**
     * 历史开奖
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String,Object>> lishiSg(Integer pageNo, Integer pageSize);
    /**
     * 获取期次详情
     * @param sgIssue
     * @return
     */
    ResultInfo<Map<String,Object>> sgDetails(String sgIssue);
    /**
     * 最近一期的开奖结果
     * @return
     */
    ResultInfo<Map<String,Object>> getNewestSgInfo();
    /**
     * 最近一期的开奖结果
     * @return
     */
    ResultInfo<Map<String,Object>> getNewestSgInfoWeb();
    /**
     * 当前期的期号与开奖时间
     * @return
     */
    ResultInfo<Map<String,Object>> getNowIssueAndTime();
    /**
     * 获取两面遗漏之大小,单双
     * @param type 资讯类型
     * @param way 时间段, 如1代表近一个月
     * @return
     */
    ResultInfo<Map<Integer, ArrayList<Integer>>> lianMianYlWeb(String type, String way);
    /**
     * 两面长龙 web端
     *
     * @param type 资讯类型
     * @param issue 期数
     * @param count 位置, 0 代表 冠亚和
     * @return
     */
    ResultInfo<List<LhcCountVO>> liangMianCWeb(String type, Integer issue, Integer count);
    /**
     * 获取web资讯：北京PK10历史开奖
     * @param pageNo  当前第几页
     * @param pageSize  页大小
     * @return
     */
    ResultInfo<Map<String, Object>> getPK10HistoryLottery(Integer pageNo, Integer pageSize, String date);
    /**
     * 资讯：冠亚和统计
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String,Object>> getGuanYaCount(Integer pageSize);
    /**
     * web首页：今日统计
     */
    ResultInfo<Map<String,Object>> getBjpksTodayStatistics();
    /**
     * web首页彩票幸运飞艇今日历史开奖
     * @return
     */
    ResultInfo<Map<String, Object>> getBjpksTodayLotteryHistory();
    /**
     * 获取最后一期赛果
     * @return
     */
    BjpksLotterySg queryBjpksLastSg();
    /**
     * web端pk10开奖资讯详情
     * @param pageNo 第几页
     * @param pageSize  每页条数
     * @param date 日期（选填）
     * @return
     */
    ResultInfo<Map<String, Object>> bjpksTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date);

    Map<String,Object> queryTotal(String date);

    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCountbySize(int size);

    /**
     * @Title: getBjPksSgLong
     * @Description: 统计北京PK10长龙数据
     * @author HANS
     * @date 2019年5月29日下午10:12:44
     */
    List<Map<String, Object>> getBjPksSgLong();

}
