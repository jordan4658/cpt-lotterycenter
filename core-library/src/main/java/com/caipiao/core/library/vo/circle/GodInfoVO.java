package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;

public class GodInfoVO {
    private String heads;   //头像
    private String nickname;    //昵称，没有的话展示用户名
    private Integer isFocus;    //是否关注  0否1是
    private String personalContent;
    private String jizji;   //几中几
    /**
     * 字段: circle_god.show_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示盈利率
     *
     * @mbggenerated
     */
    private BigDecimal showProfitRate;

    /**
     * 字段: circle_god.show_max_lz<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示最大连中
     *
     * @mbggenerated
     */
    private Integer showMaxLz;

    /**
     * 字段: circle_god.show_win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示胜率
     *
     * @mbggenerated
     */
    private BigDecimal showWinRate;

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

    public String getPersonalContent() {
        return personalContent;
    }

    public void setPersonalContent(String personalContent) {
        this.personalContent = personalContent;
    }

    public String getJizji() {
        return jizji;
    }

    public void setJizji(String jizji) {
        this.jizji = jizji;
    }

    public BigDecimal getShowProfitRate() {
        return showProfitRate;
    }

    public void setShowProfitRate(BigDecimal showProfitRate) {
        this.showProfitRate = showProfitRate;
    }

    public Integer getShowMaxLz() {
        return showMaxLz;
    }

    public void setShowMaxLz(Integer showMaxLz) {
        this.showMaxLz = showMaxLz;
    }

    public BigDecimal getShowWinRate() {
        return showWinRate;
    }

    public void setShowWinRate(BigDecimal showWinRate) {
        this.showWinRate = showWinRate;
    }

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }
}
