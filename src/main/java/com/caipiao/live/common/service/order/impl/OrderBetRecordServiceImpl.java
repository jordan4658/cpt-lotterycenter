package com.caipiao.live.common.service.order.impl;

import com.caipiao.live.common.mybatis.mapperext.order.OrderBetRecordMapperExt;
import com.caipiao.live.common.service.order.OrderBetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import static com.caipiao.live.common.util.ViewUtil.getTradeOffAmount;

/**
 * @author lucien
 * @create 2020/6/23 21:29
 */
@Service
public class OrderBetRecordServiceImpl implements OrderBetRecordService {

    @Autowired
    private OrderBetRecordMapperExt orderBetRecordMapperExt;


    @Override
    public BigDecimal querySumBetAmount(Long roomid) {
        return getTradeOffAmount(orderBetRecordMapperExt.querySumBetAmount(roomid));
    }

    @Override
    public double sumBetamountbyUserid(Map map) {
        Double amt = orderBetRecordMapperExt.sumBetamountbyUserid(map);
        if (null == amt) {
            return 0;
        } else {
            return amt.doubleValue();
        }
    }
}
