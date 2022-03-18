package com.caipiao.live.common.service.read;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.order.OrderBetDTO;
import com.caipiao.live.common.model.vo.order.OrderTodayListVo;
import com.caipiao.live.common.mybatis.entity.OrderBetRecord;
import com.caipiao.live.common.mybatis.entity.OrderRecord;

import java.util.List;

public interface OrderReadRestService {

    List<OrderRecord> selectOrders(Integer lotteryId, String issue, String status);

    /**
     * 根据条件获取投注信息
     *
     * @param orderIds 订单id集合
     * @param playIds  玩法id集合
     * @param status   状态
     * @return
     */
    List<OrderBetRecord> selectOrderBets(List<Integer> orderIds, List<Integer> playIds, String status);


    /**
     * 根据条件获取投注信息
     *
     * @param orderIds 订单id集合
     * @param playId   玩法id
     * @param status   状态
     * @return
     */
    List<OrderBetRecord> selectOrderBetsSinglePlay(List<Integer> orderIds, Integer playId, String status);

    ResultInfo<OrderTodayListVo> queryOrderTodayBetList(OrderBetDTO data);
}
