package com.caipiao.live.common.model.dto.lottery;


public class LotteryOrderBetDTO {
    //彩种大类id
    private Integer cateId;
    //彩种类型id
    private Integer typeId;
    //计划id
    private Integer planId;
    //玩法id
    private Integer playId;
    //彩种id
    private Integer lotteryId;
    //期号
    private String issue;
    //订单号
    private String orderCode;
    //投注开始时间
    private String startDate;
    //投注结束时间
    private String endDate;
    //呢称
    private String nick;
    //用户名
    private String account;
    //状态
    private String status;
    //是否正常开奖，暂没用，保留
    private String kjStatus;
    //排序类型
    private String sortType;

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKjStatus() {
        return kjStatus;
    }

    public void setKjStatus(String kjStatus) {
        this.kjStatus = kjStatus;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
