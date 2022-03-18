package com.caipiao.core.library.vo.money;

import com.mapper.domain.PaymentSummary;

public class OfflinePaymentVO extends PaymentSummary {
    private String channelName;
    private String bankName;
    private String bankSkf;
    private String bankAccountLastFour; //银行卡后4位

    private String userName;
    private String realName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankSkf() {
        return bankSkf;
    }

    public void setBankSkf(String bankSkf) {
        this.bankSkf = bankSkf;
    }

    public String getBankAccountLastFour() {
        return bankAccountLastFour;
    }

    public void setBankAccountLastFour(String bankAccountLastFour) {
        this.bankAccountLastFour = bankAccountLastFour;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
