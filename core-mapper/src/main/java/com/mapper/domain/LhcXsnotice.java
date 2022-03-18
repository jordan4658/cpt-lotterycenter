package com.mapper.domain;

import java.io.Serializable;
import java.util.Date;

public class LhcXsnotice implements Serializable {
    /**
     * 字段: lhc_xsnotice.id<br/>
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
     * 字段: lhc_xsnotice.content<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 6000<br/>
     * 说明: 内容
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 字段: lhc_xsnotice.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 字段: lhc_xsnotice.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 开始时间
     *
     * @mbggenerated
     */
    private Date startTime;

    /**
     * 字段: lhc_xsnotice.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 结束时间
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * 字段: lhc_xsnotice.update_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: lhc_xsnotice.create_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 创建人
     *
     * @mbggenerated
     */
    private String createUsername;

    /**
     * 字段: lhc_xsnotice.update_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后更新人
     *
     * @mbggenerated
     */
    private Date updateUsername;

    /**
     * 字段: lhc_xsnotice.activity_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 开启状态 0，关闭 1开启
     *
     * @mbggenerated
     */
    private Integer activityStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lhc_xsnotice
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return lhc_xsnotice.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: lhc_xsnotice.id<br/>
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
     * @return lhc_xsnotice.content: 内容
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * 字段: lhc_xsnotice.content<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 6000<br/>
     * 说明: 内容
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return lhc_xsnotice.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: lhc_xsnotice.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return lhc_xsnotice.start_time: 开始时间
     *
     * @mbggenerated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 字段: lhc_xsnotice.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 开始时间
     *
     * @mbggenerated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return lhc_xsnotice.end_time: 结束时间
     *
     * @mbggenerated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 字段: lhc_xsnotice.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 结束时间
     *
     * @mbggenerated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return lhc_xsnotice.update_time: 最后更新时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: lhc_xsnotice.update_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后更新时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return lhc_xsnotice.create_username: 创建人
     *
     * @mbggenerated
     */
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * 字段: lhc_xsnotice.create_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 创建人
     *
     * @mbggenerated
     */
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    /**
     * @return lhc_xsnotice.update_username: 最后更新人
     *
     * @mbggenerated
     */
    public Date getUpdateUsername() {
        return updateUsername;
    }

    /**
     * 字段: lhc_xsnotice.update_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后更新人
     *
     * @mbggenerated
     */
    public void setUpdateUsername(Date updateUsername) {
        this.updateUsername = updateUsername;
    }

    /**
     * @return lhc_xsnotice.activity_status: 开启状态 0，关闭 1开启
     *
     * @mbggenerated
     */
    public Integer getActivityStatus() {
        return activityStatus;
    }

    /**
     * 字段: lhc_xsnotice.activity_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 开启状态 0，关闭 1开启
     *
     * @mbggenerated
     */
    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xsnotice
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
        LhcXsnotice other = (LhcXsnotice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUsername() == null ? other.getCreateUsername() == null : this.getCreateUsername().equals(other.getCreateUsername()))
            && (this.getUpdateUsername() == null ? other.getUpdateUsername() == null : this.getUpdateUsername().equals(other.getUpdateUsername()))
            && (this.getActivityStatus() == null ? other.getActivityStatus() == null : this.getActivityStatus().equals(other.getActivityStatus()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xsnotice
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUsername() == null) ? 0 : getCreateUsername().hashCode());
        result = prime * result + ((getUpdateUsername() == null) ? 0 : getUpdateUsername().hashCode());
        result = prime * result + ((getActivityStatus() == null) ? 0 : getActivityStatus().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xsnotice
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
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUsername=").append(createUsername);
        sb.append(", updateUsername=").append(updateUsername);
        sb.append(", activityStatus=").append(activityStatus);
        sb.append("]");
        return sb.toString();
    }
}