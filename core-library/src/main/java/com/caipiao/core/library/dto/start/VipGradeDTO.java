package com.caipiao.core.library.dto.start;

import com.mapper.domain.VipGrade;

import java.math.BigDecimal;

/**
 * @author lzy
 * @create 2018-09-03 11:12
 **/
public class VipGradeDTO {

    private Integer id;

    /**
     * 说明: 等级名称
     */
    private String name;

    /**
     * 说明: 有效投注
     */
    private BigDecimal validBet;

    /**
     * 说明: 升级奖励
     */
    private BigDecimal handsel;

    /**
     * 说明: 返水分子
     */
    private Integer backwater;

    /**
     * 说明: 返水分母
     */
    private Integer backwaterDenominator;

    /**
     * 说明: 特权
     */
    private String privilege;

    public VipGrade getVipGrade() {
        VipGrade vipGrade = new VipGrade();
        vipGrade.setId(this.id);
        vipGrade.setName(this.name);
        vipGrade.setValidBet(this.validBet);
        vipGrade.setHandsel(this.handsel);
        vipGrade.setBackwater(this.backwater);
        vipGrade.setBackwaterDenominator(this.backwaterDenominator);
        vipGrade.setPrivilege(this.privilege);
        return vipGrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValidBet() {
        return validBet;
    }

    public void setValidBet(BigDecimal validBet) {
        this.validBet = validBet;
    }

    public BigDecimal getHandsel() {
        return handsel;
    }

    public void setHandsel(BigDecimal handsel) {
        this.handsel = handsel;
    }

    public Integer getBackwater() {
        return backwater;
    }

    public void setBackwater(Integer backwater) {
        this.backwater = backwater;
    }

    public Integer getBackwaterDenominator() {
        return backwaterDenominator;
    }

    public void setBackwaterDenominator(Integer backwaterDenominator) {
        this.backwaterDenominator = backwaterDenominator;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
