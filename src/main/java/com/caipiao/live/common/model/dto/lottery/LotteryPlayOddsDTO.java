package com.caipiao.live.common.model.dto.lottery;


import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;

public class LotteryPlayOddsDTO extends LotteryPlayOdds {

    private String odds;

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
