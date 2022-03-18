package com.caipiao.live.common.model.dto.order;

/**
 * ClassName:    RepealBetOrderDTO
 * Package:    com.caipiao.live.common.model.dto.order
 * Description:
 * Datetime:    2020/5/8   15:10
 * Author:   木鱼
 */
public class RepealBetOrderDTO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer lotteryId;
    private String issue;
    private String orderCode;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
}
