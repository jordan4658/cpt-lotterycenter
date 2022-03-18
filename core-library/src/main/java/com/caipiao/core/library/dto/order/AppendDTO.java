package com.caipiao.core.library.dto.order;

import java.util.List;

public class AppendDTO {

    private Integer userId; // 用户id
    private Integer type; // 追号类型：1 同倍追号 | 2 翻倍追号
    private Double betMultiples; // 投注倍数
    private Double doubleMultiples; // 翻倍倍数
    private Integer appendCount; // 追号期数
    private Boolean winStop; // 中奖后停止
    private String source; // 来源 Android | IOS | WEB
    private String issue; // 来源 Android | IOS | WEB

    private List<AppendBetDTO> appendBet; // 投注信息

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getBetMultiples() {
        return betMultiples;
    }

    public void setBetMultiples(Double betMultiples) {
        this.betMultiples = betMultiples;
    }

    public Double getDoubleMultiples() {
        return doubleMultiples;
    }

    public void setDoubleMultiples(Double doubleMultiples) {
        this.doubleMultiples = doubleMultiples;
    }

    public Integer getAppendCount() {
        return appendCount;
    }

    public void setAppendCount(Integer appendCount) {
        this.appendCount = appendCount;
    }

    public Boolean getWinStop() {
        return winStop;
    }

    public void setWinStop(Boolean winStop) {
        this.winStop = winStop;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<AppendBetDTO> getAppendBet() {
        return appendBet;
    }

    public void setAppendBet(List<AppendBetDTO> appendBet) {
        this.appendBet = appendBet;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
