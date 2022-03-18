package com.caipiao.core.library.vo.appmember;

import java.math.BigDecimal;

/**
 * 1、投注总额包含普通投注和追号投注，扣除停追退款、主动撤单、系统撤单、打和退回后的总额，属于支出；
 * 2、中奖总额为一般中奖彩金去除撤销开奖的部分，属于收入；
 * 3、返点总额包含每周VIP返点，属于收入；
 * 活动总额包含周周首充和活动首充赠送实际返还的金额，属于收入；
 * 4、推单分红收入即如果该会员是大神，推单后其他会员跟单且中奖后，从跟单会员彩金中按比例分得的分红收入，属于 收入；
 * 5、跟单分红支出指会员或者大神作为普通会员跟别的大神的单后中奖，按大神推单设置的比例分给大神的支出，属于支出；
 * 6、VIP升级奖励即会员的有效投注达到后台的配置标准后，从低VIP等级升级到高VIP等级时，系统直接给予的奖励金，属于收入；
 * 7、分享赠送是会员用户通过自己邀请码分享彩票通系统，被分享人下载了APP或直接在WEB版上填入分享人的邀请码注册成会员，并投注满一定额度后，系统给分享者发放的一次性鼓励金，是用户积极分享推广的动力，属于收入；
 * 8、分享返水是被分享的用户每次下注时，分享人按后台配置比例获得的返水。按照后台配置，该分享返水业务有2层，即A分享给B后，B分享给C，A可以获得来自B和C的返水，这也是促进用户积极推广的机制，属于用户收入；
 * 9、个人盈亏=收入-支出=中奖总额+返点总额+活动总额+推单分红收入+VIP升级奖励+分享赠送+分享返水-投注总额-跟单分红支出；
 **/
public class ReportFormsVO {
    private BigDecimal betAmount; // 投注总额
    private BigDecimal winAmount; // 中奖总额
    private BigDecimal backAmount; // 返点总额
    private BigDecimal activityAmount; // 活动总额
    private BigDecimal orderPushBonus; // 推单分红
    private BigDecimal orderFollowBonus; // 跟单分红
    private BigDecimal vipUpgradeAwards; // VIP升级奖励
    private BigDecimal shareAward; // 分享赠送
    private BigDecimal shareBack; // 分享返水
    private BigDecimal profitAmount; // 个人盈亏

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public BigDecimal getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    public BigDecimal getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(BigDecimal activityAmount) {
        this.activityAmount = activityAmount;
    }

    public BigDecimal getOrderPushBonus() {
        return orderPushBonus;
    }

    public void setOrderPushBonus(BigDecimal orderPushBonus) {
        this.orderPushBonus = orderPushBonus;
    }

    public BigDecimal getOrderFollowBonus() {
        return orderFollowBonus;
    }

    public void setOrderFollowBonus(BigDecimal orderFollowBonus) {
        this.orderFollowBonus = orderFollowBonus;
    }

    public BigDecimal getVipUpgradeAwards() {
        return vipUpgradeAwards;
    }

    public void setVipUpgradeAwards(BigDecimal vipUpgradeAwards) {
        this.vipUpgradeAwards = vipUpgradeAwards;
    }

    public BigDecimal getShareAward() {
        return shareAward;
    }

    public void setShareAward(BigDecimal shareAward) {
        this.shareAward = shareAward;
    }

    public BigDecimal getShareBack() {
        return shareBack;
    }

    public void setShareBack(BigDecimal shareBack) {
        this.shareBack = shareBack;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }
}
