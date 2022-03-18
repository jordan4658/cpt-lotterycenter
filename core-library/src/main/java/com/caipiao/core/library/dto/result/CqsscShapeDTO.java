package com.caipiao.core.library.dto.result;

public class CqsscShapeDTO {
    private String issue; // 期号
    private String openNumber; // 完整开奖号码
    private String number; // 所选部分
    private Integer big; // 大
    private Integer small; // 小
    private Integer singular; // 单数
    private Integer quantity; // 双数
    private Integer prime; // 质数
    private Integer composite; // 和数

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

    public Integer getBig() {
        return big;
    }

    public void setBig(Integer big) {
        this.big = big;
    }

    public Integer getSmall() {
        return small;
    }

    public void setSmall(Integer small) {
        this.small = small;
    }

    public Integer getSingular() {
        return singular;
    }

    public void setSingular(Integer singular) {
        this.singular = singular;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrime() {
        return prime;
    }

    public void setPrime(Integer prime) {
        this.prime = prime;
    }

    public Integer getComposite() {
        return composite;
    }

    public void setComposite(Integer composite) {
        this.composite = composite;
    }
}
