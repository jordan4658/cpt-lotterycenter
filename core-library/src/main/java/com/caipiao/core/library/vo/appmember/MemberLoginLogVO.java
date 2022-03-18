package com.caipiao.core.library.vo.appmember;

/**
 * 用户登录历史VO
 *
 * @author lzy
 * @create 2018-08-10 18:15
 **/
public class MemberLoginLogVO {


    /**
     * 说明: 登录时间
     */
    private String loginTime;

    /**
     * 说明: 客户端
     */
    private String client;

    /**
     * 说明: 地址
     */
    private String address;

    /**
     * 说明: 说明
     */
    private String state;

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
