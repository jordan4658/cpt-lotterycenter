package com.caipiao.live.common.model.dto.report;

import java.math.BigDecimal;


public class GameDataDO {

    /**
     * 金额
     */
    private BigDecimal gameamt;

    /**
     * 总人数
     */
    private Long totalPeople;

    /**
     * 游戏总中奖金额
     */
    private BigDecimal gameawardamt;

    public BigDecimal getGameamt() {
        return gameamt;
    }

    public void setGameamt(BigDecimal gameamt) {
        this.gameamt = gameamt;
    }

    public Long getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(Long totalPeople) {
        this.totalPeople = totalPeople;
    }

    public BigDecimal getGameawardamt() {
        return gameawardamt;
    }

    public void setGameawardamt(BigDecimal gameawardamt) {
        this.gameawardamt = gameawardamt;
    }
}
