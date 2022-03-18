package com.mapper.domain;

import java.io.Serializable;

public class PasswordFreeze implements Serializable {
    /**
     * 字段: password_freeze.id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: password_freeze.member_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 字段: password_freeze.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: password_freeze.login_password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 登录密码
     *
     * @mbggenerated
     */
    private String loginPassword;

    /**
     * 字段: password_freeze.pay_password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 支付密码
     *
     * @mbggenerated
     */
    private String payPassword;

    /**
     * 字段: password_freeze.verify_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 验证码
     *
     * @mbggenerated
     */
    private String verifyCode;

    /**
     * 字段: password_freeze.freeze_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 冻结时间
     *
     * @mbggenerated
     */
    private String freezeTime;

    /**
     * 字段: password_freeze.unfreeze_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 解冻时间
     *
     * @mbggenerated
     */
    private String unfreezeTime;

    /**
     * 字段: password_freeze.status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 冻结状态:0,解冻;1冻结
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 字段: password_freeze.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 解冻人的账号
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table password_freeze
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return password_freeze.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: password_freeze.id<br/>
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
     * @return password_freeze.member_id: 会员id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 字段: password_freeze.member_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 会员id
     *
     * @mbggenerated
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * @return password_freeze.account: 会员账号
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * 字段: password_freeze.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员账号
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return password_freeze.login_password: 登录密码
     *
     * @mbggenerated
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * 字段: password_freeze.login_password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 登录密码
     *
     * @mbggenerated
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * @return password_freeze.pay_password: 支付密码
     *
     * @mbggenerated
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 字段: password_freeze.pay_password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 支付密码
     *
     * @mbggenerated
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /**
     * @return password_freeze.verify_code: 验证码
     *
     * @mbggenerated
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * 字段: password_freeze.verify_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 验证码
     *
     * @mbggenerated
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * @return password_freeze.freeze_time: 冻结时间
     *
     * @mbggenerated
     */
    public String getFreezeTime() {
        return freezeTime;
    }

    /**
     * 字段: password_freeze.freeze_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 冻结时间
     *
     * @mbggenerated
     */
    public void setFreezeTime(String freezeTime) {
        this.freezeTime = freezeTime;
    }

    /**
     * @return password_freeze.unfreeze_time: 解冻时间
     *
     * @mbggenerated
     */
    public String getUnfreezeTime() {
        return unfreezeTime;
    }

    /**
     * 字段: password_freeze.unfreeze_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 解冻时间
     *
     * @mbggenerated
     */
    public void setUnfreezeTime(String unfreezeTime) {
        this.unfreezeTime = unfreezeTime;
    }

    /**
     * @return password_freeze.status: 冻结状态:0,解冻;1冻结
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 字段: password_freeze.status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 冻结状态:0,解冻;1冻结
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return password_freeze.operater: 解冻人的账号
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 字段: password_freeze.operater<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 解冻人的账号
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_freeze
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
        PasswordFreeze other = (PasswordFreeze) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getLoginPassword() == null ? other.getLoginPassword() == null : this.getLoginPassword().equals(other.getLoginPassword()))
            && (this.getPayPassword() == null ? other.getPayPassword() == null : this.getPayPassword().equals(other.getPayPassword()))
            && (this.getVerifyCode() == null ? other.getVerifyCode() == null : this.getVerifyCode().equals(other.getVerifyCode()))
            && (this.getFreezeTime() == null ? other.getFreezeTime() == null : this.getFreezeTime().equals(other.getFreezeTime()))
            && (this.getUnfreezeTime() == null ? other.getUnfreezeTime() == null : this.getUnfreezeTime().equals(other.getUnfreezeTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOperater() == null ? other.getOperater() == null : this.getOperater().equals(other.getOperater()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_freeze
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getLoginPassword() == null) ? 0 : getLoginPassword().hashCode());
        result = prime * result + ((getPayPassword() == null) ? 0 : getPayPassword().hashCode());
        result = prime * result + ((getVerifyCode() == null) ? 0 : getVerifyCode().hashCode());
        result = prime * result + ((getFreezeTime() == null) ? 0 : getFreezeTime().hashCode());
        result = prime * result + ((getUnfreezeTime() == null) ? 0 : getUnfreezeTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOperater() == null) ? 0 : getOperater().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_freeze
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
        sb.append(", memberId=").append(memberId);
        sb.append(", account=").append(account);
        sb.append(", loginPassword=").append(loginPassword);
        sb.append(", payPassword=").append(payPassword);
        sb.append(", verifyCode=").append(verifyCode);
        sb.append(", freezeTime=").append(freezeTime);
        sb.append(", unfreezeTime=").append(unfreezeTime);
        sb.append(", status=").append(status);
        sb.append(", operater=").append(operater);
        sb.append("]");
        return sb.toString();
    }
}