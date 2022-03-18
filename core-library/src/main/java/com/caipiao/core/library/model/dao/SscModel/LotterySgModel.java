package com.caipiao.core.library.model.dao.SscModel;

/**
 * 彩票赛果model,包含属性有期号和开奖结果
 *
 * @author lzy
 * @create 2018-07-23 15:43
 **/
public class LotterySgModel implements Comparable<LotterySgModel> {

    private String issue; // 期号

    private String sg; // 赛果

    private String date; // 日期

    private String cpkNumber; // 彩票控开奖结果

    private String kcwNumber; // 开彩网开奖结果

    private String openTime; // 开奖时间

    private String status; // 状态

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCpkNumber() {
        return cpkNumber;
    }

    public void setCpkNumber(String cpkNumber) {
        this.cpkNumber = cpkNumber;
    }

    public String getKcwNumber() {
        return kcwNumber;
    }

    public void setKcwNumber(String kcwNumber) {
        this.kcwNumber = kcwNumber;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LotterySgModel() { }

    public LotterySgModel(String issue, String sg, String date, String cpkNumber, String kcwNumber, String openTime, String status) {
        this.issue = issue;
        this.sg = sg;
        this.date = date;
        this.cpkNumber = cpkNumber;
        this.kcwNumber = kcwNumber;
        this.openTime = openTime;
        this.status = status;
    }

    @Override
    public int compareTo(LotterySgModel o) {
        return this.issue.compareTo(o.getIssue());
    }
}
