package com.caipiao.live.common.model.vo;

import java.util.List;

/**
 * 资讯返回类 VO
 *
 * @author lzy
 * @create 2018-08-01 17:19
 **/
public class KjlsVO {
    private String issue; //期号
    private String date; //日期
    private String time; // 时间
    private List<Integer> num; //开奖号码

    public KjlsVO() {
    }

    public KjlsVO(String issue, String date, String time, List<Integer> num) {
        this.issue = issue;
        this.date = date;
        this.time = time;
        this.num = num;
    }

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

    public List<Integer> getNum() {
        return num;
    }

    public void setNum(List<Integer> num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
