package com.caipiao.core.library.dto.result;

public class XjsscRatioDTO {
    private String issue; // 期号
    private String openNumber; // 完整开奖号码
    private String number; // 所选部分
    private Integer ratio1; // 比例1
    private Integer ratio2; // 比例2
    private Integer ratio3; // 比例3
    private Integer ratio4; // 比例4

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getRatio1() {
        return ratio1;
    }

    public void setRatio1(Integer ratio1) {
        this.ratio1 = ratio1;
    }

    public Integer getRatio2() {
        return ratio2;
    }

    public void setRatio2(Integer ratio2) {
        this.ratio2 = ratio2;
    }

    public Integer getRatio3() {
        return ratio3;
    }

    public void setRatio3(Integer ratio3) {
        this.ratio3 = ratio3;
    }

    public Integer getRatio4() {
        return ratio4;
    }

    public void setRatio4(Integer ratio4) {
        this.ratio4 = ratio4;
    }
}
