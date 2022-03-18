package com.caipiao.live.common.model.vo.lottery;

public class NoLotteryRecordVO {
    private Integer id;
    private String lotteryName;
    private String issue;
    private String status;
    private String createTime;
    private String dealTime;

    public NoLotteryRecordVO() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLotteryName() {
        return this.lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getIssue() {
        return this.issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDealTime() {
        return this.dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}

