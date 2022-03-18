package com.caipiao.live.common.mybatis.mapperbean.provider;

import com.caipiao.live.common.model.dto.order.LotteryReportResponse;
import com.caipiao.live.common.model.vo.LotteryReportVo;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LotteryReportBeanMapper {

    /**
     * 统计个人彩票游戏明细
     */
    List<LotteryReportVo> getLotteryReportDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime, @Param("pageNO") Integer pageNO,
                                                 @Param("pageSize") Integer pageSize, @Param("settStatus") String settStatus);

    /**
     * 统计个人AE游戏明细
     */
    List<LotteryReportVo> getAeReportDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                            @Param("endTime") String endtime, @Param("pageNo") Integer pageNo,
                                            @Param("pageSize") Integer pageSize, @Param("money") BigDecimal money);

    /**
     * 统计个人AG游戏明细
     */
    List<LotteryReportVo> getAgReportDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                            @Param("endTime") String endtime, @Param("pageNo") Integer pageNo,
                                            @Param("pageSize") Integer pageSize);

    /**
     * 统计个人开元游戏明细
     */
    List<LotteryReportVo> getKyRepotDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                           @Param("endTime") String endtime, @Param("pageNo") Integer pageNo,
                                           @Param("pageSize") Integer pageSize);

    /**
     * 统计ES电竞游戏明细
     */
    List<LotteryReportVo> getEsRepotDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                           @Param("endTime") String endtime, @Param("pageNo") Integer pageNo,
                                           @Param("pageSize") Integer pageSize);

    /**
     * 个人游戏报表统计
     */
    List<LotteryReportResponse> getCountReport(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                               @Param("endTime") String endtime);

    /**
     * 注单详情查询
     */
    List<LotteryReportVo> getReportDetailByIssua(@Param("userId") Integer userId, @Param("issue") String issue,
                                                 @Param("startTime") String startTime, @Param("endTime") String endtime,
                                                 @Param("lotteryId") Integer lotteryId);

    /**
     * 通过订单号查询开奖号码
     */
    List<LotteryReportVo> getWinNumberList(@Param("sns") List<String> sns);


    /**
     * 统计MG游戏明细
     */
    List<LotteryReportVo> getMgRepotDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                           @Param("endTime") String endtime, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * AG 捕鱼
     */
    List<LotteryReportVo> getAgByRepotDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                             @Param("endTime") String endtime, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * jdb 捕鱼
     */
    List<LotteryReportVo> getJdbRepotDetail(@Param("userId") Integer userId, @Param("startTime") String startTime,
                                            @Param("endTime") String endtime, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
}
