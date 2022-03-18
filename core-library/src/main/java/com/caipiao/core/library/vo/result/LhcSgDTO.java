package com.caipiao.core.library.vo.result;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/11/8 14:15
 */
public class LhcSgDTO {

    private String issue; // 期号
    private String year; // 年份
    private String number; // 开奖号码
    private String date; // 日期
    private String size; // 特码大小
    private String single; // 特码单双
    private Integer sum; // 和值

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
