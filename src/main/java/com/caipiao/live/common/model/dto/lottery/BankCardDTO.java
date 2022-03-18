package com.caipiao.live.common.model.dto.lottery;

/**
 * ClassName:    BankCardDTO
 * Package:    com.caipiao.live.common.model.dto.lottery
 * Description:
 * Datetime:    2020/5/8   14:29
 * Author:   木鱼
 */
public class BankCardDTO {

    private String memberId;
    private String account;
    private String number;
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
