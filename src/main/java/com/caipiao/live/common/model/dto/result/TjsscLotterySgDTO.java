package com.caipiao.live.common.model.dto.result;

import com.caipiao.live.common.mybatis.entity.CqsscLotterySg;

public class TjsscLotterySgDTO extends CqsscLotterySg {
    private Integer sum; // 和值

    private String dragonTiger; // 龙虎

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getDragonTiger() {
        return dragonTiger;
    }

    public void setDragonTiger(String dragonTiger) {
        this.dragonTiger = dragonTiger;
    }
}
