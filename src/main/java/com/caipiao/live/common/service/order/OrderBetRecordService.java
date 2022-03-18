package com.caipiao.live.common.service.order;


import java.math.BigDecimal;
import java.util.Map;

/**
 * @author lucien
 * @create 2020/6/23 21:29
 */
public interface OrderBetRecordService {

    /**
     * 直播间投注金额
     *
     * @param roomid
     * @return
     */
    BigDecimal querySumBetAmount(Long roomid);

    /**
     * 会员投注金额
     *
     * @param map
     * @return
     */
    double sumBetamountbyUserid(Map map);
}
