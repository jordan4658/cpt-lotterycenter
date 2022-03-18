package com.caipiao.core.library.dto.result;

/**
 * @Author: xiaomi
 * @CreateDate: 2018/11/23$ 15:25$
 * @Version: 1.0
 */
public class PceggLotteryTodayAndHistoryNews {
    //期号
    private String issue;

    //开奖号码
    private String number;

    // 开奖号码，也就是和值
    private String realNum;

    private String bigOrSmall; // 大小

    private String singleOrDouble; // 单双


    public String getRealNum() {
        return realNum;
    }

    public void setRealNum(String realNum) {
        this.realNum = realNum;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getBigOrSmall() {
        return bigOrSmall;
    }

    public void setBigOrSmall(String bigOrSmall) {
        this.bigOrSmall = bigOrSmall;
    }

    public String getSingleOrDouble() {
        return singleOrDouble;
    }

    public void setSingleOrDouble(String singleOrDouble) {
        this.singleOrDouble = singleOrDouble;
    }
}
