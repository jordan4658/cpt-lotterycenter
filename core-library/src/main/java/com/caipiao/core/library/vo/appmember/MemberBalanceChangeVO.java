package com.caipiao.core.library.vo.appmember;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员的帐变记录VO
 *
 * @author lzy
 * @create 2018-08-10 14:06
 **/
public class MemberBalanceChangeVO {

    private Date createTime;

    private BigDecimal money;

    private BigDecimal balance;

    private String type;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
