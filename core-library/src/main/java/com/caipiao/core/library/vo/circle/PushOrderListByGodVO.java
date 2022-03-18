package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;

public class PushOrderListByGodVO extends PushOrderListVO {
    /**
     * 字段: circle_god.show_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示盈利率
     *
     * @mbggenerated
     */
    private BigDecimal showProfitRate;

    /**
     * 字段: circle_god.show_max_lz<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示最大连中
     *
     * @mbggenerated
     */
    private Integer showMaxLz;

    /**
     * 字段: circle_god.show_win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示胜率
     *
     * @mbggenerated
     */
    private BigDecimal showWinRate;

    private BigDecimal winAmount;   //中奖金额  0   表示未中奖
    private BigDecimal fenhongAmount;   //分红金额


    public BigDecimal getShowProfitRate() {
        return showProfitRate;
    }

    public void setShowProfitRate(BigDecimal showProfitRate) {
        this.showProfitRate = showProfitRate;
    }

    public Integer getShowMaxLz() {
        return showMaxLz;
    }

    public void setShowMaxLz(Integer showMaxLz) {
        this.showMaxLz = showMaxLz;
    }

    public BigDecimal getShowWinRate() {
        return showWinRate;
    }

    public void setShowWinRate(BigDecimal showWinRate) {
        this.showWinRate = showWinRate;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public BigDecimal getFenhongAmount() {
        return fenhongAmount;
    }

    public void setFenhongAmount(BigDecimal fenhongAmount) {
        this.fenhongAmount = fenhongAmount;
    }
}
