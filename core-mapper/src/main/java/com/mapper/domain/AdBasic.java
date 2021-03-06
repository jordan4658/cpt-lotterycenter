package com.mapper.domain;

import java.io.Serializable;

public class AdBasic implements Serializable {
    /**
     * 字段: ad_basic.id<br/>
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
     * 字段: ad_basic.title<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 字段: ad_basic.start_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效开始时间
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * 字段: ad_basic.end_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效结束时间
     *
     * @mbggenerated
     */
    private String endTime;

    /**
     * 字段: ad_basic.hide<br/>
     * 必填: true<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 自动隐藏：0，否；1，是
     *
     * @mbggenerated
     */
    private Integer hide;

    /**
     * 字段: ad_basic.close<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否关闭：0，否；1，是
     *
     * @mbggenerated
     */
    private Integer close;

    /**
     * 字段: ad_basic.sort<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 排序值
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * 字段: ad_basic.publish<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 发布系统
     *
     * @mbggenerated
     */
    private String publish;

    /**
     * 字段: ad_basic.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: ad_basic.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad_basic
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return ad_basic.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: ad_basic.id<br/>
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
     * @return ad_basic.title: 标题
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * 字段: ad_basic.title<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 标题
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return ad_basic.start_time: 有效开始时间
     *
     * @mbggenerated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 字段: ad_basic.start_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效开始时间
     *
     * @mbggenerated
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return ad_basic.end_time: 有效结束时间
     *
     * @mbggenerated
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 字段: ad_basic.end_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 有效结束时间
     *
     * @mbggenerated
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return ad_basic.hide: 自动隐藏：0，否；1，是
     *
     * @mbggenerated
     */
    public Integer getHide() {
        return hide;
    }

    /**
     * 字段: ad_basic.hide<br/>
     * 必填: true<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 自动隐藏：0，否；1，是
     *
     * @mbggenerated
     */
    public void setHide(Integer hide) {
        this.hide = hide;
    }

    /**
     * @return ad_basic.close: 是否关闭：0，否；1，是
     *
     * @mbggenerated
     */
    public Integer getClose() {
        return close;
    }

    /**
     * 字段: ad_basic.close<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否关闭：0，否；1，是
     *
     * @mbggenerated
     */
    public void setClose(Integer close) {
        this.close = close;
    }

    /**
     * @return ad_basic.sort: 排序值
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 字段: ad_basic.sort<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 排序值
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return ad_basic.publish: 发布系统
     *
     * @mbggenerated
     */
    public String getPublish() {
        return publish;
    }

    /**
     * 字段: ad_basic.publish<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 发布系统
     *
     * @mbggenerated
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * @return ad_basic.create_time: 创建时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: ad_basic.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return ad_basic.deleted: 是否删除
     *
     * @mbggenerated
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 字段: ad_basic.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_basic
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
        AdBasic other = (AdBasic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getHide() == null ? other.getHide() == null : this.getHide().equals(other.getHide()))
            && (this.getClose() == null ? other.getClose() == null : this.getClose().equals(other.getClose()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getPublish() == null ? other.getPublish() == null : this.getPublish().equals(other.getPublish()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_basic
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getHide() == null) ? 0 : getHide().hashCode());
        result = prime * result + ((getClose() == null) ? 0 : getClose().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getPublish() == null) ? 0 : getPublish().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_basic
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
        sb.append(", title=").append(title);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", hide=").append(hide);
        sb.append(", close=").append(close);
        sb.append(", sort=").append(sort);
        sb.append(", publish=").append(publish);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}