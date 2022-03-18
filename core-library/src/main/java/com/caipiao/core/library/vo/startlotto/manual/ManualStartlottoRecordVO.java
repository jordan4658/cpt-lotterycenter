package com.caipiao.core.library.vo.startlotto.manual;

/**
 * @author lzy
 * @create 2018-06-07 17:42
 **/
public class ManualStartlottoRecordVO {

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
     * 说明: 开奖号码
     */
    private String pushNumber;

    /**
     * 说明: 添加时间
     */
    private String createTime;

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

    public String getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(String pushNumber) {
        this.pushNumber = pushNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
