package com.mapper.domain;

import java.io.Serializable;

public class LhcPhoto implements Serializable {
    /**
     * 字段: lhc_photo.id<br/>
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
     * 字段: lhc_photo.category_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 分类id
     *
     * @mbggenerated
     */
    private Integer categoryId;

    /**
     * 字段: lhc_photo.issue<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: lhc_photo.url<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 图片路径
     *
     * @mbggenerated
     */
    private String url;

    /**
     * 字段: lhc_photo.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: lhc_photo.deleted<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lhc_photo
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return lhc_photo.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: lhc_photo.id<br/>
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
     * @return lhc_photo.category_id: 分类id
     *
     * @mbggenerated
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 字段: lhc_photo.category_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 分类id
     *
     * @mbggenerated
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return lhc_photo.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: lhc_photo.issue<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return lhc_photo.url: 图片路径
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * 字段: lhc_photo.url<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 图片路径
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return lhc_photo.create_time: 创建时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: lhc_photo.create_time<br/>
     * 必填: false<br/>
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
     * @return lhc_photo.deleted: 是否删除
     *
     * @mbggenerated
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 字段: lhc_photo.deleted<br/>
     * 必填: false<br/>
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
     * This method corresponds to the database table lhc_photo
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
        LhcPhoto other = (LhcPhoto) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_photo
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_photo
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
        sb.append(", categoryId=").append(categoryId);
        sb.append(", issue=").append(issue);
        sb.append(", url=").append(url);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}