package com.caipiao.core.library.vo.betorder;

import java.math.BigDecimal;

/**
 * 会员注单统计
 *
 * @author lzy
 * @create 2018-07-13 14:46
 **/
public class MemberBetCountVO {

    //总投注
    private BigDecimal totalBet = BigDecimal.ZERO;

    //有效投注
    private BigDecimal totalValidBet = BigDecimal.ZERO;

    //中奖总额
    private BigDecimal totalWins = BigDecimal.ZERO;

    //返点总额
    private BigDecimal totalBackWater = BigDecimal.ZERO;

    //盈亏总额
    private BigDecimal totalChange = BigDecimal.ZERO;

    //总用户
    private Integer totalMember;

    //充值用户
    private Integer totalTopupMember;

    public static MemberBetCountVO getInstance(int totalMember, int totalTopupMember) {
        MemberBetCountVO memberBetCountVO = new MemberBetCountVO();
        memberBetCountVO.setTotalMember(totalMember);
        memberBetCountVO.setTotalTopupMember(totalTopupMember);
        return memberBetCountVO;
    }

    public BigDecimal getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(BigDecimal totalBet) {
        this.totalBet = totalBet;
    }

    public BigDecimal getTotalValidBet() {
        return totalValidBet;
    }

    public void setTotalValidBet(BigDecimal totalValidBet) {
        this.totalValidBet = totalValidBet;
    }

    public BigDecimal getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(BigDecimal totalWins) {
        this.totalWins = totalWins;
    }

    public BigDecimal getTotalBackWater() {
        return totalBackWater;
    }

    public void setTotalBackWater(BigDecimal totalBackWater) {
        this.totalBackWater = totalBackWater;
    }

    public BigDecimal getTotalChange() {
        return totalChange;
    }

    public void setTotalChange(BigDecimal totalChange) {
        this.totalChange = totalChange;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Integer getTotalTopupMember() {
        return totalTopupMember;
    }

    public void setTotalTopupMember(Integer totalTopupMember) {
        this.totalTopupMember = totalTopupMember;
    }
}
