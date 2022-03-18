package com.caipiao.live.common.model.request;

public class BettingRecordRequest {
    private Long lotkindid;
    private Long lotruleid;
    private Integer status;
    private String searchname;
    private String luckynum;
    private Long sschistoryid;
    private String period;

    private Integer ordertype;
    private Long orderid;

    private String starttime;

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

    String endtime;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    Integer day;

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    String accno;

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSschistoryid() {
        return sschistoryid;
    }

    public void setSschistoryid(Long sschistoryid) {
        this.sschistoryid = sschistoryid;
    }

    public String getLuckynum() {
        return luckynum;
    }

    public void setLuckynum(String luckynum) {
        this.luckynum = luckynum;
    }

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }

    public Long getLotruleid() {
        return lotruleid;
    }

    public void setLotruleid(Long lotruleid) {
        this.lotruleid = lotruleid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }
}
