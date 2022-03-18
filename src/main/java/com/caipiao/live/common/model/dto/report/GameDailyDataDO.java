package com.caipiao.live.common.model.dto.report;

import java.math.BigDecimal;
import java.util.Date;


public class GameDailyDataDO {

    /**
     * 日期
     */
    private String date;
    /**
     * 游戏总投注
     */
    private BigDecimal gameamt;
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

    public BigDecimal getGameawardamt() {
        return gameawardamt;
    }

    public void setGameawardamt(BigDecimal gameawardamt) {
        this.gameawardamt = gameawardamt;
    }


    public static GameDailyDataDO getDefault() {
        GameDailyDataDO gameDailyDataDO = new GameDailyDataDO();
        gameDailyDataDO.setGameamt(BigDecimal.ZERO);
        gameDailyDataDO.setGameawardamt(BigDecimal.ZERO);
        return gameDailyDataDO;

    }
}
