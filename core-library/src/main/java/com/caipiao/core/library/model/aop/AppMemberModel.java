package com.caipiao.core.library.model.aop;

/**
 * @Author: admin
 * @Description: app用户信息
 * @Version: 1.0.0
 * @Date; 2018/5/26 026 17:28
 */
public class AppMemberModel {

    //用户id
    private int id;

    //用户昵称
    private String nickname;

    //手机号
    private String phone;

    //登录tonken
    private String token;

    //token时间（10位时间戳）
    private long tokenTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }
}
