package com.caipiao.core.library.dto.circle;

import java.math.BigDecimal;

public class PushOrderDTO {
    private Integer[] orderBetIds;
    /**
     * 字段: circle_god_push_order.ensure_odds<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 保障赔率
     *
     * @mbggenerated
     */
    private BigDecimal ensureOdds;

    /**
     * 字段: circle_god_push_order.bonus_scale<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 分红比例
     *
     * @mbggenerated
     */
    private BigDecimal bonusScale;

    /**
     * 字段: circle_god_push_order.secret_status<br/>
     * 必填: false<br/>
     * 缺省: 2<br/>
     * 长度: 10<br/>
     * 说明: 保密设置：1跟单后公开2开奖后公开
     *
     * @mbggenerated
     */
    private Integer secretStatus;
    /**
     * 字段: circle_god_push_order.god_analyze<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 800<br/>
     * 说明: 大神分析
     *
     * @mbggenerated
     */
    private String godAnalyze;

    public Integer[] getOrderBetIds() {
        return orderBetIds;
    }

    public void setOrderBetIds(Integer[] orderBetIds) {
        this.orderBetIds = orderBetIds;
    }

    public BigDecimal getEnsureOdds() {
        return ensureOdds;
    }

    public void setEnsureOdds(BigDecimal ensureOdds) {
        this.ensureOdds = ensureOdds;
    }

    public BigDecimal getBonusScale() {
        return bonusScale;
    }

    public void setBonusScale(BigDecimal bonusScale) {
        this.bonusScale = bonusScale;
    }

    public Integer getSecretStatus() {
        return secretStatus;
    }

    public void setSecretStatus(Integer secretStatus) {
        this.secretStatus = secretStatus;
    }

    public String getGodAnalyze() {
        return godAnalyze;
    }

    public void setGodAnalyze(String godAnalyze) {
        this.godAnalyze = godAnalyze;
    }
}
