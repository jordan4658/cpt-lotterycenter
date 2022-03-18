package com.mapper.domain;

import java.io.Serializable;

public class LhcHandicap implements Serializable {
    /**
     * 字段: lhc_handicap.id<br/>
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
     * 字段: lhc_handicap.issue<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: lhc_handicap.startlotto_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖时间
     *
     * @mbggenerated
     */
    private String startlottoTime;

    /**
     * 字段: lhc_handicap.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 自动开盘时间
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * 字段: lhc_handicap.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 自动封盘时间
     *
     * @mbggenerated
     */
    private String endTime;

    /**
     * 字段: lhc_handicap.automation<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 允许自动开盘: 0 不允许,1 允许
     *
     * @mbggenerated
     */
    private Integer automation;

    /**
     * 字段: lhc_handicap.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: lhc_handicap.deleted<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 是否删除 : 0 否, 1 是
     *
     * @mbggenerated
     */
    private Integer deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lhc_handicap
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return lhc_handicap.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: lhc_handicap.id<br/>
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
     * @return lhc_handicap.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: lhc_handicap.issue<br/>
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
     * @return lhc_handicap.startlotto_time: 开奖时间
     *
     * @mbggenerated
     */
    public String getStartlottoTime() {
        return startlottoTime;
    }

    /**
     * 字段: lhc_handicap.startlotto_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖时间
     *
     * @mbggenerated
     */
    public void setStartlottoTime(String startlottoTime) {
        this.startlottoTime = startlottoTime;
    }

    /**
     * @return lhc_handicap.start_time: 自动开盘时间
     *
     * @mbggenerated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 字段: lhc_handicap.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 自动开盘时间
     *
     * @mbggenerated
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return lhc_handicap.end_time: 自动封盘时间
     *
     * @mbggenerated
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 字段: lhc_handicap.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 自动封盘时间
     *
     * @mbggenerated
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return lhc_handicap.automation: 允许自动开盘: 0 不允许,1 允许
     *
     * @mbggenerated
     */
    public Integer getAutomation() {
        return automation;
    }

    /**
     * 字段: lhc_handicap.automation<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 允许自动开盘: 0 不允许,1 允许
     *
     * @mbggenerated
     */
    public void setAutomation(Integer automation) {
        this.automation = automation;
    }

    /**
     * @return lhc_handicap.create_time: 添加时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: lhc_handicap.create_time<br/>
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
     * @return lhc_handicap.deleted: 是否删除 : 0 否, 1 是
     *
     * @mbggenerated
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 字段: lhc_handicap.deleted<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 是否删除 : 0 否, 1 是
     *
     * @mbggenerated
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_handicap
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
        LhcHandicap other = (LhcHandicap) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getStartlottoTime() == null ? other.getStartlottoTime() == null : this.getStartlottoTime().equals(other.getStartlottoTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getAutomation() == null ? other.getAutomation() == null : this.getAutomation().equals(other.getAutomation()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_handicap
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getStartlottoTime() == null) ? 0 : getStartlottoTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getAutomation() == null) ? 0 : getAutomation().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_handicap
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
        sb.append(", issue=").append(issue);
        sb.append(", startlottoTime=").append(startlottoTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", automation=").append(automation);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}