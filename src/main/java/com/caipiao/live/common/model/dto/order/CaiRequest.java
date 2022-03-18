package com.caipiao.live.common.model.dto.order;

import java.util.List;

public class CaiRequest {
    private String accno;
    // 开始和结束时间
    private String starttime;
    private String endtime;
    // 1 =今天 ；2 =昨天； 3 =7天
    private Integer day;
    private Integer ordertype;
    private String orderstatus;
    private List<Long> lotkindids;

    //开奖状态： 1 未中奖 2 中奖 9未开奖
    private String status;
    private Long orderid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public List<Long> getLotkindids() {
        return lotkindids;
    }

    public void setLotkindids(List<Long> lotkindids) {
        this.lotkindids = lotkindids;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
