package com.caipiao.core.library.dto.result;

import com.mapper.domain.CqsscLotterySg;

public class TxffcLotterySgDTO extends CqsscLotterySg {
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
