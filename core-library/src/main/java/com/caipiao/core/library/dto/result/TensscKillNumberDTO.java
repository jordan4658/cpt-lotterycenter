package com.caipiao.core.library.dto.result;

public class TensscKillNumberDTO {
    private String issue; // 期号
    private String time; // 时间
    private String number; // 开奖完整结果
    private Integer openNumber; // 开奖号码
    private Integer sin;
    private Integer sec;
    private Integer cos;
    private Integer cot;
    private Integer tan;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(Integer openNumber) {
        this.openNumber = openNumber;
    }

    public Integer getSin() {
        return sin;
    }

    public void setSin(Integer sin) {
        this.sin = sin;
    }

    public Integer getSec() {
        return sec;
    }

    public void setSec(Integer sec) {
        this.sec = sec;
    }

    public Integer getCos() {
        return cos;
    }

    public void setCos(Integer cos) {
        this.cos = cos;
    }

    public Integer getCot() {
        return cot;
    }

    public void setCot(Integer cot) {
        this.cot = cot;
    }

    public Integer getTan() {
        return tan;
    }

    public void setTan(Integer tan) {
        this.tan = tan;
    }
}
