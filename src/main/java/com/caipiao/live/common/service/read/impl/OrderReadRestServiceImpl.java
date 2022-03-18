package com.caipiao.live.common.service.read.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.order.OrderBetDTO;
import com.caipiao.live.common.model.dto.order.OrderBetStatus;
import com.caipiao.live.common.model.vo.order.OrderTodayListVo;
import com.caipiao.live.common.mybatis.entity.OrderBetRecord;
import com.caipiao.live.common.mybatis.entity.OrderBetRecordExample;
import com.caipiao.live.common.mybatis.entity.OrderRecord;
import com.caipiao.live.common.mybatis.entity.OrderRecordExample;
import com.caipiao.live.common.mybatis.mapper.OrderBetRecordMapper;
import com.caipiao.live.common.mybatis.mapper.OrderRecordMapper;
import com.caipiao.live.common.service.read.OrderReadRestService;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderReadRestServiceImpl implements OrderReadRestService {
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private OrderBetRecordMapper orderBetRecordMapper;

    @Override
    public List<OrderRecord> selectOrders(Integer lotteryId, String issue, String status) {
        List<OrderRecord> list = new ArrayList<>();
        // 校验参数
        if (lotteryId == null || StringUtils.isBlank(issue) || StringUtils.isBlank(status)) {
            return list;
        }

//        // TODO 暂时去掉缓存（用于测试）
        OrderRecordExample orderExample = new OrderRecordExample();
        OrderRecordExample.Criteria orderCriteria = orderExample.createCriteria();
        orderCriteria.andLotteryIdEqualTo(lotteryId);
        orderCriteria.andStatusEqualTo(status);
        orderCriteria.andIssueEqualTo(issue);
        orderCriteria.andIsDeleteEqualTo(false);
        list = orderRecordMapper.selectByExample(orderExample);

//        // TODO 暂时去掉缓存（用于测试）end

        // 非测试start
//        // 从缓存中获取相应订单信息
//        if (redisTemplate.hasKey(ORDER_KEY + lotteryId + "_" + issue)) {
//            list = (List<OrderRecord>) redisTemplate.opsForValue().get(ORDER_KEY + lotteryId + "_" + issue);
//        }
//        // 从数据库获取订单信息
//        if (CollectionUtils.isEmpty(list)) {
//            OrderRecordExample orderExample = new OrderRecordExample();
//            OrderRecordExample.Criteria orderCriteria = orderExample.createCriteria();
//            orderCriteria.andIssueEqualTo(issue);
//            orderCriteria.andLotteryIdEqualTo(lotteryId);
//            orderCriteria.andStatusEqualTo(status);
//            orderCriteria.andIsDeleteEqualTo(false);
//            list = orderRecordMapper.selectByExample(orderExample);
//            redisTemplate.opsForValue().set(ORDER_KEY + lotteryId + "_" + issue, list, 2, TimeUnit.MINUTES);
//        }
        // 非测试end
        return list;
    }


    @Override
    public List<OrderBetRecord> selectOrderBets(List<Integer> orderIds, List<Integer> playIds, String status) {
        OrderBetRecordExample betExample = new OrderBetRecordExample();
        OrderBetRecordExample.Criteria betCriteria = betExample.createCriteria();
        betCriteria.andOrderIdIn(orderIds);
        betCriteria.andPlayIdIn(playIds);
        betCriteria.andIsDeleteEqualTo(false);
        betCriteria.andTbStatusEqualTo(status);

        return orderBetRecordMapper.selectByExample(betExample);
    }

    @Override
    public List<OrderBetRecord> selectOrderBetsSinglePlay(List<Integer> orderIds, Integer playId, String status) {
        OrderBetRecordExample betExample = new OrderBetRecordExample();
        OrderBetRecordExample.Criteria betCriteria = betExample.createCriteria();
        betCriteria.andOrderIdIn(orderIds);
        betCriteria.andPlayIdEqualTo(playId);
        betCriteria.andIsDeleteEqualTo(false);
        betCriteria.andTbStatusEqualTo(status);

        return orderBetRecordMapper.selectByExample(betExample);
    }

    @Override
    public ResultInfo<OrderTodayListVo> queryOrderTodayBetList(OrderBetDTO data) {
        OrderTodayListVo orderTodayListVo = new OrderTodayListVo();
        BigDecimal todayEarnAmount = new BigDecimal(0.00);//当日盈亏
        BigDecimal todayAllBetAmount = new BigDecimal(0.00);//当日已结算投注成本
        BigDecimal todayWinAmount = new BigDecimal(0.00);//当日中奖金额
        BigDecimal todayHasSettle = new BigDecimal(0.00);//当日已结算 有效投注
        BigDecimal todayNoSettle = new BigDecimal(0.00);//当日未结  投注金额
        OrderBetRecordExample betRecordExample = new OrderBetRecordExample();
        OrderBetRecordExample.Criteria betRecordCriteria = betRecordExample.createCriteria();
        betRecordCriteria.andUserIdEqualTo(data.getUserId());
        // 根据状态查询
        betRecordCriteria.andTbStatusNotEqualTo(OrderBetStatus.BACK);
        // 根据彩种查询
        betRecordCriteria.andLotteryIdEqualTo(data.getLotteryId());
        // 根据时间查询
        Date todayEnd = DateUtils.todayEndTime();
        Date todayStart = DateUtils.todayStartTime();
        betRecordCriteria.andCreateTimeBetween(todayStart, todayEnd);
        // 查询投注记录
        List<OrderBetRecord> orderBetRecords = orderBetRecordMapper.selectByExample(betRecordExample);
        if (orderBetRecords.size() > 0) {
            for (OrderBetRecord orderBetRecord : orderBetRecords) {
                todayWinAmount = todayWinAmount.add(orderBetRecord.getWinAmount());//当日中奖金额
                if (orderBetRecord.getTbStatus().equals(Constants.WAIT)) {
                    todayNoSettle = todayNoSettle.add(orderBetRecord.getBetAmount());//当日未结投注金额
                } else if (orderBetRecord.getTbStatus().equals(Constants.WIN) || orderBetRecord.getTbStatus().equals(Constants.NO_WIN)
                        || orderBetRecord.getTbStatus().equals(Constants.HE) || "HAS_LOTTERY".equals(orderBetRecord.getTbStatus())) {
                    todayHasSettle = todayHasSettle.add(orderBetRecord.getBetAmount());//当日已结算投注金额
                }
            }
        }
        //当日盈亏 = 当日中奖 - 已结算投注金额   //转为String类型
        String todayWinAmountString = todayWinAmount.toString();
        String todayNoSettleString = todayNoSettle.toString();
        String todayHasSettleString = todayHasSettle.toString();
        todayEarnAmount = todayWinAmount.subtract(todayHasSettle);
        String todayEarnAmountString = todayEarnAmount.toString();
        orderTodayListVo.setTodayWinAmount(todayWinAmountString);
        orderTodayListVo.setTodayNoSettle(todayNoSettleString);
        orderTodayListVo.setTodayHasSettle(todayHasSettleString);
        orderTodayListVo.setTodayEarnAmount(todayEarnAmountString);
        return ResultInfo.ok(orderTodayListVo);
    }

}
