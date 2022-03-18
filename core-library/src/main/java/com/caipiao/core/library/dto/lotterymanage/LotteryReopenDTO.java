package com.caipiao.core.library.dto.lotterymanage;

/**
 * @Author: xiaomi
 * @CreateDate: 2018/12/11$ 14:52$
 * @Version: 1.0
 */
public class LotteryReopenDTO {
    private Integer type; // 类型
    private Integer lotteryId; // 彩种id
    private String issue; // 期号
    private String number; // 开奖号码
    private String orderSn; // 订单号

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
