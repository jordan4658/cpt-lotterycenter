package com.caipiao.live.common.model.dto.report;

import java.math.BigDecimal;


public class GameUserDataDO {

    /**
     * 用户id
     */
    private String userid;
    /**
     * 游戏总投注
     */
    private BigDecimal gameamt;
    /**
     * 游戏总中奖金额
     */
    private BigDecimal gameawardamt;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getGameamt() {
        return gameamt;
    }

    public void setGameamt(BigDecimal gameamt) {
        this.gameamt = gameamt;
    }

    public BigDecimal getGameawardamt() {
        return gameawardamt;
    }

    public void setGameawardamt(BigDecimal gameawardamt) {
        this.gameawardamt = gameawardamt;
    }

    public static GameUserDataDO getDefault() {
        GameUserDataDO gameUserDataDO = new GameUserDataDO();
        gameUserDataDO.setUserid(null);
        gameUserDataDO.setGameamt(BigDecimal.ZERO);
        gameUserDataDO.setGameawardamt(BigDecimal.ZERO);
        return gameUserDataDO;

    }
}
