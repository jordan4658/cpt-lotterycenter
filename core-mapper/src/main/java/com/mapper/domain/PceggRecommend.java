package com.mapper.domain;

import java.io.Serializable;

public class PceggRecommend implements Serializable {
    /**
     * 字段: pcegg_recommend.id<br/>
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
     * 字段: pcegg_recommend.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: pcegg_recommend.region_one_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一区推荐号码
     *
     * @mbggenerated
     */
    private String regionOneNumber;

    /**
     * 字段: pcegg_recommend.region_one_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一区推荐单双
     *
     * @mbggenerated
     */
    private String regionOneSingle;

    /**
     * 字段: pcegg_recommend.region_one_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一区推荐大小
     *
     * @mbggenerated
     */
    private String regionOneSize;

    /**
     * 字段: pcegg_recommend.region_two_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二区推荐号码
     *
     * @mbggenerated
     */
    private String regionTwoNumber;

    /**
     * 字段: pcegg_recommend.region_two_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二区推荐单双
     *
     * @mbggenerated
     */
    private String regionTwoSingle;

    /**
     * 字段: pcegg_recommend.region_two_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二区推荐大小
     *
     * @mbggenerated
     */
    private String regionTwoSize;

    /**
     * 字段: pcegg_recommend.region_three_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三区推荐号码
     *
     * @mbggenerated
     */
    private String regionThreeNumber;

    /**
     * 字段: pcegg_recommend.region_three_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三区推荐单双
     *
     * @mbggenerated
     */
    private String regionThreeSingle;

    /**
     * 字段: pcegg_recommend.region_three_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三区推荐大小
     *
     * @mbggenerated
     */
    private String regionThreeSize;

    /**
     * 字段: pcegg_recommend.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pcegg_recommend
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return pcegg_recommend.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: pcegg_recommend.id<br/>
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
     * @return pcegg_recommend.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: pcegg_recommend.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return pcegg_recommend.region_one_number: 第一区推荐号码
     *
     * @mbggenerated
     */
    public String getRegionOneNumber() {
        return regionOneNumber;
    }

    /**
     * 字段: pcegg_recommend.region_one_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一区推荐号码
     *
     * @mbggenerated
     */
    public void setRegionOneNumber(String regionOneNumber) {
        this.regionOneNumber = regionOneNumber;
    }

    /**
     * @return pcegg_recommend.region_one_single: 第一区推荐单双
     *
     * @mbggenerated
     */
    public String getRegionOneSingle() {
        return regionOneSingle;
    }

    /**
     * 字段: pcegg_recommend.region_one_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一区推荐单双
     *
     * @mbggenerated
     */
    public void setRegionOneSingle(String regionOneSingle) {
        this.regionOneSingle = regionOneSingle;
    }

    /**
     * @return pcegg_recommend.region_one_size: 第一区推荐大小
     *
     * @mbggenerated
     */
    public String getRegionOneSize() {
        return regionOneSize;
    }

    /**
     * 字段: pcegg_recommend.region_one_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一区推荐大小
     *
     * @mbggenerated
     */
    public void setRegionOneSize(String regionOneSize) {
        this.regionOneSize = regionOneSize;
    }

    /**
     * @return pcegg_recommend.region_two_number: 第二区推荐号码
     *
     * @mbggenerated
     */
    public String getRegionTwoNumber() {
        return regionTwoNumber;
    }

    /**
     * 字段: pcegg_recommend.region_two_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二区推荐号码
     *
     * @mbggenerated
     */
    public void setRegionTwoNumber(String regionTwoNumber) {
        this.regionTwoNumber = regionTwoNumber;
    }

    /**
     * @return pcegg_recommend.region_two_single: 第二区推荐单双
     *
     * @mbggenerated
     */
    public String getRegionTwoSingle() {
        return regionTwoSingle;
    }

    /**
     * 字段: pcegg_recommend.region_two_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二区推荐单双
     *
     * @mbggenerated
     */
    public void setRegionTwoSingle(String regionTwoSingle) {
        this.regionTwoSingle = regionTwoSingle;
    }

    /**
     * @return pcegg_recommend.region_two_size: 第二区推荐大小
     *
     * @mbggenerated
     */
    public String getRegionTwoSize() {
        return regionTwoSize;
    }

    /**
     * 字段: pcegg_recommend.region_two_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二区推荐大小
     *
     * @mbggenerated
     */
    public void setRegionTwoSize(String regionTwoSize) {
        this.regionTwoSize = regionTwoSize;
    }

    /**
     * @return pcegg_recommend.region_three_number: 第三区推荐号码
     *
     * @mbggenerated
     */
    public String getRegionThreeNumber() {
        return regionThreeNumber;
    }

    /**
     * 字段: pcegg_recommend.region_three_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三区推荐号码
     *
     * @mbggenerated
     */
    public void setRegionThreeNumber(String regionThreeNumber) {
        this.regionThreeNumber = regionThreeNumber;
    }

    /**
     * @return pcegg_recommend.region_three_single: 第三区推荐单双
     *
     * @mbggenerated
     */
    public String getRegionThreeSingle() {
        return regionThreeSingle;
    }

    /**
     * 字段: pcegg_recommend.region_three_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三区推荐单双
     *
     * @mbggenerated
     */
    public void setRegionThreeSingle(String regionThreeSingle) {
        this.regionThreeSingle = regionThreeSingle;
    }

    /**
     * @return pcegg_recommend.region_three_size: 第三区推荐大小
     *
     * @mbggenerated
     */
    public String getRegionThreeSize() {
        return regionThreeSize;
    }

    /**
     * 字段: pcegg_recommend.region_three_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三区推荐大小
     *
     * @mbggenerated
     */
    public void setRegionThreeSize(String regionThreeSize) {
        this.regionThreeSize = regionThreeSize;
    }

    /**
     * @return pcegg_recommend.create_time: 创建时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: pcegg_recommend.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pcegg_recommend
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
        PceggRecommend other = (PceggRecommend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getRegionOneNumber() == null ? other.getRegionOneNumber() == null : this.getRegionOneNumber().equals(other.getRegionOneNumber()))
            && (this.getRegionOneSingle() == null ? other.getRegionOneSingle() == null : this.getRegionOneSingle().equals(other.getRegionOneSingle()))
            && (this.getRegionOneSize() == null ? other.getRegionOneSize() == null : this.getRegionOneSize().equals(other.getRegionOneSize()))
            && (this.getRegionTwoNumber() == null ? other.getRegionTwoNumber() == null : this.getRegionTwoNumber().equals(other.getRegionTwoNumber()))
            && (this.getRegionTwoSingle() == null ? other.getRegionTwoSingle() == null : this.getRegionTwoSingle().equals(other.getRegionTwoSingle()))
            && (this.getRegionTwoSize() == null ? other.getRegionTwoSize() == null : this.getRegionTwoSize().equals(other.getRegionTwoSize()))
            && (this.getRegionThreeNumber() == null ? other.getRegionThreeNumber() == null : this.getRegionThreeNumber().equals(other.getRegionThreeNumber()))
            && (this.getRegionThreeSingle() == null ? other.getRegionThreeSingle() == null : this.getRegionThreeSingle().equals(other.getRegionThreeSingle()))
            && (this.getRegionThreeSize() == null ? other.getRegionThreeSize() == null : this.getRegionThreeSize().equals(other.getRegionThreeSize()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pcegg_recommend
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getRegionOneNumber() == null) ? 0 : getRegionOneNumber().hashCode());
        result = prime * result + ((getRegionOneSingle() == null) ? 0 : getRegionOneSingle().hashCode());
        result = prime * result + ((getRegionOneSize() == null) ? 0 : getRegionOneSize().hashCode());
        result = prime * result + ((getRegionTwoNumber() == null) ? 0 : getRegionTwoNumber().hashCode());
        result = prime * result + ((getRegionTwoSingle() == null) ? 0 : getRegionTwoSingle().hashCode());
        result = prime * result + ((getRegionTwoSize() == null) ? 0 : getRegionTwoSize().hashCode());
        result = prime * result + ((getRegionThreeNumber() == null) ? 0 : getRegionThreeNumber().hashCode());
        result = prime * result + ((getRegionThreeSingle() == null) ? 0 : getRegionThreeSingle().hashCode());
        result = prime * result + ((getRegionThreeSize() == null) ? 0 : getRegionThreeSize().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pcegg_recommend
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
        sb.append(", regionOneNumber=").append(regionOneNumber);
        sb.append(", regionOneSingle=").append(regionOneSingle);
        sb.append(", regionOneSize=").append(regionOneSize);
        sb.append(", regionTwoNumber=").append(regionTwoNumber);
        sb.append(", regionTwoSingle=").append(regionTwoSingle);
        sb.append(", regionTwoSize=").append(regionTwoSize);
        sb.append(", regionThreeNumber=").append(regionThreeNumber);
        sb.append(", regionThreeSingle=").append(regionThreeSingle);
        sb.append(", regionThreeSize=").append(regionThreeSize);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}