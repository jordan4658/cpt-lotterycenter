package com.caipiao.core.library.vo.startlotto;

/**
 * @author lzy
 * @create 2018-06-06 15:43
 **/
public class OpenRepealOrderVO {

    private Integer id;

    /**
     * 说明: 彩种名称
     */
    private String lotteryName;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 订单号
     */
    private String orderCode;

    /**
     * 说明: 添加时间
     */
    private String createTime;

    /**
     * 说明: 操作员账号
     */
    private String operater;

    /**
     * 说明: 操作
     */
    private String operate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
