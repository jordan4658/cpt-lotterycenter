package com.caipiao.core.library.vo.circle;

public class GodFansOrFocusListVO extends FansAndFocusNumberVO {
    private String heads;
    private String nickname;
    private Integer godId;
    private String personalContent;
    private Integer isFocus;

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

    public Integer getGodId() {
        return godId;
    }

    public void setGodId(Integer godId) {
        this.godId = godId;
    }

    public String getPersonalContent() {
        return personalContent;
    }

    public void setPersonalContent(String personalContent) {
        this.personalContent = personalContent;
    }

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }
}
