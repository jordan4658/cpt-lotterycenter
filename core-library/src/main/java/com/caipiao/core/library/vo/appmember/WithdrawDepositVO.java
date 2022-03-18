package com.caipiao.core.library.vo.appmember;

/**
 * 用户提现记录VO
 *
 * @author lzy
 * @create 2018-08-09 18:21
 **/
public class WithdrawDepositVO {

    private String createTime;

    private Integer money;

    private String status;

    private String remark;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
