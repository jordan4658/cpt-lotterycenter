package com.caipiao.live.common.model.dto.order;

/**
 * 会员投注信息DTO
 *
 * @author lzy
 * @create 2018-07-19 14:46
 **/
public class MemberBetInfoDTO {

    private Integer memberId;

    private String account;

    private Integer lotteryId;

    private String issue;

    private Integer lotteryPlayId;

    private String betMessage;

    private Integer betNums;

    private Integer betMoney;

    private String source;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Integer getLotteryPlayId() {
        return lotteryPlayId;
    }

    public void setLotteryPlayId(Integer lotteryPlayId) {
        this.lotteryPlayId = lotteryPlayId;
    }

    public String getBetMessage() {
        return betMessage;
    }

    public void setBetMessage(String betMessage) {
        this.betMessage = betMessage;
    }

    public Integer getBetNums() {
        return betNums;
    }

    public void setBetNums(Integer betNums) {
        this.betNums = betNums;
    }

    public Integer getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
