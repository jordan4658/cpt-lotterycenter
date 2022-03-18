package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;
import java.util.Date;

public class GdUserVO {
    private String userName;
    private Date gdTime;    //跟单时间
    private BigDecimal gdBs;    //跟单倍数
    private BigDecimal gdAmount;    //跟单总额

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getGdTime() {
        return gdTime;
    }

    public void setGdTime(Date gdTime) {
        this.gdTime = gdTime;
    }

    public BigDecimal getGdBs() {
        return gdBs;
    }

    public void setGdBs(BigDecimal gdBs) {
        this.gdBs = gdBs;
    }

    public BigDecimal getGdAmount() {
        return gdAmount;
    }

    public void setGdAmount(BigDecimal gdAmount) {
        this.gdAmount = gdAmount;
    }
}
