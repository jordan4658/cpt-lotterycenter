package com.mapper.domain;

import java.io.Serializable;

public class ManualStartlottoRecord implements Serializable {
    /**
     * 字段: manual_startlotto_record.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: manual_startlotto_record.lottery_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种id
     *
     * @mbggenerated
     */
    private Integer lotteryId;

    /**
     * 字段: manual_startlotto_record.issue<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: manual_startlotto_record.push_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 开奖号码
     *
     * @mbggenerated
     */
    private String pushNumber;

    /**
     * 字段: manual_startlotto_record.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: manual_startlotto_record.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作员账号
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table manual_startlotto_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return manual_startlotto_record.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: manual_startlotto_record.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return manual_startlotto_record.lottery_id: 彩种id
     *
     * @mbggenerated
     */
    public Integer getLotteryId() {
        return lotteryId;
    }

    /**
     * 字段: manual_startlotto_record.lottery_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种id
     *
     * @mbggenerated
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * @return manual_startlotto_record.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: manual_startlotto_record.issue<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return manual_startlotto_record.push_number: 开奖号码
     *
     * @mbggenerated
     */
    public String getPushNumber() {
        return pushNumber;
    }

    /**
     * 字段: manual_startlotto_record.push_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 开奖号码
     *
     * @mbggenerated
     */
    public void setPushNumber(String pushNumber) {
        this.pushNumber = pushNumber;
    }

    /**
     * @return manual_startlotto_record.create_time: 添加时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: manual_startlotto_record.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return manual_startlotto_record.operater: 操作员账号
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 字段: manual_startlotto_record.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作员账号
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manual_startlotto_record
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ManualStartlottoRecord other = (ManualStartlottoRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLotteryId() == null ? other.getLotteryId() == null : this.getLotteryId().equals(other.getLotteryId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getPushNumber() == null ? other.getPushNumber() == null : this.getPushNumber().equals(other.getPushNumber()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOperater() == null ? other.getOperater() == null : this.getOperater().equals(other.getOperater()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manual_startlotto_record
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLotteryId() == null) ? 0 : getLotteryId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getPushNumber() == null) ? 0 : getPushNumber().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOperater() == null) ? 0 : getOperater().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manual_startlotto_record
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lotteryId=").append(lotteryId);
        sb.append(", issue=").append(issue);
        sb.append(", pushNumber=").append(pushNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", operater=").append(operater);
        sb.append("]");
        return sb.toString();
    }
}