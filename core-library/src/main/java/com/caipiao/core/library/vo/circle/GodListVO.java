package com.caipiao.core.library.vo.circle;

public class GodListVO {
    private Integer godId;      //大神id
    private Integer memberId;    //用户id
    private String heads;   //头像
    private String nickname;    //昵称，没有的话展示用户名
    private Integer isFocus;    //是否关注  0否1是
    private String showRate;    //胜率、盈利率、连中

    public Integer getGodId() {
        return godId;
    }

    public void setGodId(Integer godId) {
        this.godId = godId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }

    public String getShowRate() {
        return showRate;
    }

    public void setShowRate(String showRate) {
        this.showRate = showRate;
    }
}
