package com.caipiao.live.common.model.dto.result;

/**
 * PC蛋蛋列表
 */
public class PceggLotterySgDTO2 extends PceggLotterySgDTO {
    //限制数量
    private String limitValue;
    //开奖
    private String leopard;

    public String getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }

    public String getLeopard() {
        return leopard;
    }

    public void setLeopard(String leopard) {
        this.leopard = leopard;
    }

}
