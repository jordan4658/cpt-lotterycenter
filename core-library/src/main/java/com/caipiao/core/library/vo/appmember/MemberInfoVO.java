package com.caipiao.core.library.vo.appmember;

import java.math.BigDecimal;

/**
 * 用户账户信息VO
 *
 * @author lzy
 * @create 2018-08-10 16:05
 **/
public class MemberInfoVO {

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * vip等级
     */
    private String vip;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 可提额度
     */
    private BigDecimal withdrawalAmount;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 说明: 会员头像
     */
    private String heads;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 上次登录时间
     */
    private String loginTime;

    /**
     * 上次登录地区
     */
    private String region;

    /**
     * 上次登录ip
     */
    private String ip;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 推广码
     */
    private String promotionCode;

    private String qq;
    private String phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
