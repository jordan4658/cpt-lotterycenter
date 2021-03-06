package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class SysParameter implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.param_id
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Long paramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.param_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String paramCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.param_name
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String paramName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.remark
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.param_value
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String paramValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.sort_by
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Integer sortBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.param_status
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Integer paramStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.is_delete
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Boolean isDelete;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.create_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.create_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.update_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.update_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_parameter.merchant_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private String merchantCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.param_id
     *
     * @return the value of sys_parameter.param_id
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.param_id
     *
     * @param paramId the value for sys_parameter.param_id
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.param_code
     *
     * @return the value of sys_parameter.param_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.param_code
     *
     * @param paramCode the value for sys_parameter.param_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.param_name
     *
     * @return the value of sys_parameter.param_name
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.param_name
     *
     * @param paramName the value for sys_parameter.param_name
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.remark
     *
     * @return the value of sys_parameter.remark
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.remark
     *
     * @param remark the value for sys_parameter.remark
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.param_value
     *
     * @return the value of sys_parameter.param_value
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.param_value
     *
     * @param paramValue the value for sys_parameter.param_value
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.sort_by
     *
     * @return the value of sys_parameter.sort_by
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Integer getSortBy() {
        return sortBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.sort_by
     *
     * @param sortBy the value for sys_parameter.sort_by
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.param_status
     *
     * @return the value of sys_parameter.param_status
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Integer getParamStatus() {
        return paramStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.param_status
     *
     * @param paramStatus the value for sys_parameter.param_status
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setParamStatus(Integer paramStatus) {
        this.paramStatus = paramStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.is_delete
     *
     * @return the value of sys_parameter.is_delete
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.is_delete
     *
     * @param isDelete the value for sys_parameter.is_delete
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.create_user
     *
     * @return the value of sys_parameter.create_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.create_user
     *
     * @param createUser the value for sys_parameter.create_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.create_time
     *
     * @return the value of sys_parameter.create_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.create_time
     *
     * @param createTime the value for sys_parameter.create_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.update_user
     *
     * @return the value of sys_parameter.update_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.update_user
     *
     * @param updateUser the value for sys_parameter.update_user
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.update_time
     *
     * @return the value of sys_parameter.update_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.update_time
     *
     * @param updateTime the value for sys_parameter.update_time
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_parameter.merchant_code
     *
     * @return the value of sys_parameter.merchant_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_parameter.merchant_code
     *
     * @param merchantCode the value for sys_parameter.merchant_code
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paramId=").append(paramId);
        sb.append(", paramCode=").append(paramCode);
        sb.append(", paramName=").append(paramName);
        sb.append(", remark=").append(remark);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", sortBy=").append(sortBy);
        sb.append(", paramStatus=").append(paramStatus);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", merchantCode=").append(merchantCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}