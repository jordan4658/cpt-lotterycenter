package com.caipiao.core.library.dto.result;

/**
 * @Author: xiaomi
 * @CreateDate: 2018/11/25$ 11:18$
 * @Version: 1.0
 */
public class BjpksHistoryDTO {
    //期号
    private String issue;

    //开奖号码
    private String number;

    //大小
    private String bigOrSmall;
    //单双
    private String singleOrTwo;


    //冠亚和值
    private Integer GuanYaHe;


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

    public String getSingleOrTwo() {
        return singleOrTwo;
    }

    public void setSingleOrTwo(String singleOrTwo) {
        this.singleOrTwo = singleOrTwo;
    }

    public Integer getGuanYaHe() {
        return GuanYaHe;
    }

    public void setGuanYaHe(Integer guanYaHe) {
        GuanYaHe = guanYaHe;
    }
}
