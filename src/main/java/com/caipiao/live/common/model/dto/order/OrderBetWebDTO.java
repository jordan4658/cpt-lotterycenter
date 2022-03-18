package com.caipiao.live.common.model.dto.order;

import java.util.Date;

public class OrderBetWebDTO  {
    private Integer lotteryId;  //彩种id，不传则获取所有
    private Date startDate;     //开始日期
    private Date endDate;       //结束日期
    private String status;     //WIN中奖、NO_WIN未中奖、WAIT等待开奖，不传获取所有
    private Integer userId;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
