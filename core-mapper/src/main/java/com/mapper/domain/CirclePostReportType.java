package com.mapper.domain;

import java.io.Serializable;
import java.util.Date;

public class CirclePostReportType implements Serializable {
    /**
     * 字段: circle_post_report_type.id<br/>
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
     * 字段: circle_post_report_type.type<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 字段: circle_post_report_type.sort<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 排序
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * 字段: circle_post_report_type.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 添加人
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * 字段: circle_post_report_type.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table circle_post_report_type
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return circle_post_report_type.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: circle_post_report_type.id<br/>
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
     * @return circle_post_report_type.type: 
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * 字段: circle_post_report_type.type<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return circle_post_report_type.sort: 排序
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 字段: circle_post_report_type.sort<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 排序
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return circle_post_report_type.operater: 添加人
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 字段: circle_post_report_type.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 添加人
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * @return circle_post_report_type.create_time: 
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: circle_post_report_type.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_post_report_type
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
        CirclePostReportType other = (CirclePostReportType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getOperater() == null ? other.getOperater() == null : this.getOperater().equals(other.getOperater()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_post_report_type
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getOperater() == null) ? 0 : getOperater().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_post_report_type
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
        sb.append(", type=").append(type);
        sb.append(", sort=").append(sort);
        sb.append(", operater=").append(operater);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}