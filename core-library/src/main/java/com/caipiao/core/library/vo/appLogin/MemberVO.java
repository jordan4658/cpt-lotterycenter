package com.caipiao.core.library.vo.appLogin;

import java.math.BigDecimal;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2017-12-18 15:55
 */
public class MemberVO {

    //用户id
    private Integer uid;

    //用户账号
    private String account;

    //用户昵称
    private String nickname;

    //头像url
    private String heads;

    /**
     * 我方生成的唯一标识（验证登录使用）
     */
    private String token;

    private BigDecimal balance;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "uid=" + uid +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", heads='" + heads + '\'' +
                ", token='" + token + '\'' +
                ", balance=" + balance +
                '}';
    }
}
