package com.caipiao.live.common.model.vo;

public class ManualStartlottoRecordVO {
    private Integer id;
    private String lotteryName;
    private String issue;
    private String pushNumber;
    private String createTime;

    public ManualStartlottoRecordVO() {
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

    public String getPushNumber() {
        return this.pushNumber;
    }

    public void setPushNumber(String pushNumber) {
        this.pushNumber = pushNumber;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

