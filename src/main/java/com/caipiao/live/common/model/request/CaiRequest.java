package com.caipiao.live.common.model.request;

import java.util.ArrayList;
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
    //开奖状态
    private String settStatus;

    private List<String> selected = new ArrayList<String>();
    private Long roomid;
    //投注倍数
    private Integer multiples = 1;
    //投注金额
    private Double amount;
    private String selectids;
    private String orderno;
    private String issue;
    private String totalCount;
    private String winCount;
    private Integer settingId;
    private Integer lotteryId;

    /**
     * 个人彩票报表统计类型
     */
    private String countType;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getWinCount() {
        return winCount;
    }

    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSettStatus() {
        return settStatus;
    }

    public void setSettStatus(String settStatus) {
        this.settStatus = settStatus;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Integer getMultiples() {
        return multiples;
    }

    public void setMultiples(Integer multiples) {
        this.multiples = multiples;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSelectids() {
        return selectids;
    }

    public void setSelectids(String selectids) {
        this.selectids = selectids;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getGtkey() {
        return gtkey;
    }

    public void setGtkey(String gtkey) {
        this.gtkey = gtkey;
    }

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }

    /**
     * 跟投key
     */
    String gtkey;
    Long lotkindid;

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
