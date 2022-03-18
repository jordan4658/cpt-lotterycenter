package com.mapper.domain;

import java.io.Serializable;

public class FirstRechargeBackwater implements Serializable {
    /**
     * 字段: first_recharge_backwater.id<br/>
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
     * 字段: first_recharge_backwater.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作管理员账号
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * 字段: first_recharge_backwater.backwater_scale<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 5<br/>
     * 说明: 返水比例
     *
     * @mbggenerated
     */
    private Double backwaterScale;

    /**
     * 字段: first_recharge_backwater.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效投注开始时间
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * 字段: first_recharge_backwater.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效投注结束时间
     *
     * @mbggenerated
     */
    private String endTime;

    /**
     * 字段: first_recharge_backwater.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return first_recharge_backwater.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: first_recharge_backwater.id<br/>
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
     * @return first_recharge_backwater.operater: 操作管理员账号
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 字段: first_recharge_backwater.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作管理员账号
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * @return first_recharge_backwater.backwater_scale: 返水比例
     *
     * @mbggenerated
     */
    public Double getBackwaterScale() {
        return backwaterScale;
    }

    /**
     * 字段: first_recharge_backwater.backwater_scale<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 5<br/>
     * 说明: 返水比例
     *
     * @mbggenerated
     */
    public void setBackwaterScale(Double backwaterScale) {
        this.backwaterScale = backwaterScale;
    }

    /**
     * @return first_recharge_backwater.start_time: 有效投注开始时间
     *
     * @mbggenerated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 字段: first_recharge_backwater.start_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效投注开始时间
     *
     * @mbggenerated
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return first_recharge_backwater.end_time: 有效投注结束时间
     *
     * @mbggenerated
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 字段: first_recharge_backwater.end_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效投注结束时间
     *
     * @mbggenerated
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return first_recharge_backwater.create_time: 添加时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: first_recharge_backwater.create_time<br/>
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
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
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
        FirstRechargeBackwater other = (FirstRechargeBackwater) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperater() == null ? other.getOperater() == null : this.getOperater().equals(other.getOperater()))
            && (this.getBackwaterScale() == null ? other.getBackwaterScale() == null : this.getBackwaterScale().equals(other.getBackwaterScale()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperater() == null) ? 0 : getOperater().hashCode());
        result = prime * result + ((getBackwaterScale() == null) ? 0 : getBackwaterScale().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
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
        sb.append(", operater=").append(operater);
        sb.append(", backwaterScale=").append(backwaterScale);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}