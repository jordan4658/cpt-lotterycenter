package com.caipiao.core.library.vo.startlotto.number;

/**
 * @author lzy
 * @create 2018-06-07 10:22
 **/
public class NoLotteryRecordVO {

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
     * 说明: 状态
     */
    private String status;

    /**
     * 说明: 添加时间
     */
    private String createTime;

    /**
     * 说明: 处理时间
     */
    private String dealTime;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
