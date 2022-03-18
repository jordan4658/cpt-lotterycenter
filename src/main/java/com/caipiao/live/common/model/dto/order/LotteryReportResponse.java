package com.caipiao.live.common.model.dto.order;

import com.caipiao.live.common.model.vo.LotteryReportVo;

import java.math.BigDecimal;
import java.util.List;

public class LotteryReportResponse {
    /**
     * 下单数
     */
    private Integer num;
    /**
     * 下单金额
     */
    private BigDecimal sumamt;
    /**
     * 中奖金额
     */
    private BigDecimal winamt;

    /**
     * 统计类型
     */
    private String countType;

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getSumamt() {
        return sumamt;
    }

    public void setSumamt(BigDecimal sumamt) {
        this.sumamt = sumamt;
    }

    public BigDecimal getWinamt() {
        return winamt;
    }

    public void setWinamt(BigDecimal winamt) {
        this.winamt = winamt;
    }

}
