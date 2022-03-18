package com.caipiao.live.common.mybatis.mapperext.order;

import com.caipiao.live.common.model.dto.order.OrderBetRecordCalcDTO;
import com.caipiao.live.common.model.dto.order.OrderBetRecordResultDTO;
import com.caipiao.live.common.model.dto.report.LotteryBetDataDO;
import com.caipiao.live.common.model.dto.report.LotteryDataDO;
import com.caipiao.live.common.model.vo.order.OrderBetVo;
import com.caipiao.live.common.mybatis.entity.OrderBetRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date:Created in 15:412020/3/14
 * @Descriotion
 * @Author
 **/
public interface OrderBetRecordMapperExt {
    HashMap<String, Object> selectMoneyByplayId(@Param("playids") List<Integer> playids, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("issue") String issue, @Param("orderCode") String orderCode, @Param("lotteries") List<Integer> lotteries, @Param("cateId") Integer cateId);

    HashMap<String, Object> selectVaildMoneyByplayId(@Param("playids") List<Integer> playids, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("issue") String issue, @Param("orderCode") String orderCode, @Param("lotteries") List<Integer> lotteries, @Param("cateId") Integer cateId);

//    HashMap<String, Object> selectMoneyByIssue(@Param("playids") List<Integer> playids, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("issue") String issue, @Param("orderCode") String orderCode, @Param("lotteries") List<Integer> lotteries);

//    HashMap<String, Object> selectVaildMoneyByIssue(@Param("playids") List<Integer> playids, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("issue") String issue, @Param("orderCode") String orderCode, @Param("lotteries") List<Integer> lotteries);

    List<OrderBetRecordResultDTO> selectAllOrder(@Param("playids") List<Integer> playids, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("issue") String issue, @Param("orderCode") String orderCode, @Param("lotteries") List<Integer> lotteries, @Param("sortType") String sortType, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("cateId") Integer cateId);

    int countdl(@Param("map") HashMap<String, Object> map);

    List<HashMap<String, Object>> selectdltree(@Param("map") HashMap<String, Object> map);

    double sumBetamountbyUserid(Map params);

    List<HashMap<String, Object>> selectdllist(@Param("params") HashMap<String, Object> example);

    List<OrderBetRecord> orderBetListById(@Param("userId") Integer userId);

    int countJoinByExample(@Param("whereSelect") String whereSelect);

    List<OrderBetRecord> selectJoinByExample(@Param("params") HashMap<String, Object> example);

    HashMap<String, Object> selectSumBetamountByWait(@Param("params") HashMap<String, Object> example);  //统计未开奖状态的 总投注额

    HashMap<String, Object> selectSumBetamountByAll(@Param("params") HashMap<String, Object> example);  //统计 总投注额 ，总中奖额，总返点

    HashMap<String, Object> selectSumBetamountByValid(@Param("params") HashMap<String, Object> example);  //统计有效 总投注额

    HashMap<String, Object> selectSumUser(@Param("params") HashMap<String, Object> example);  //统计 总用户

    List<OrderBetVo> queryWinOrLossEveryDay(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderBetVo> queryWinOrLossEveryDayNew(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderBetVo> queryBetEveryDayNew(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    OrderBetRecordCalcDTO selectBetNumberByPlayIdAndPlayNameAndShuxing(@Param("playId") Integer playId, @Param("playName") String playName, @Param("shuxing") String shuxing, @Param("issue") String issue);

    List<OrderBetRecord> selectByPlayIdAndIssue(@Param("playId") Integer playId, @Param("issue") String issue);

    BigDecimal querySumBetAmount(@Param("roomId") Long roomId);

    BigDecimal getOrderBetRecordAmount(List<Integer> list);


}
