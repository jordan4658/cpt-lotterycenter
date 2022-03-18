package com.caipiao.core.library.vo.result;

/**
 * 公式杀号VO
 *
 * @author lzy
 * @create 2018-08-06 14:55
 */
public class KillNumberVO implements Comparable<KillNumberVO> {
    private String issue; // 期号
    private String time; // 开奖时间
    private String sgNumber; // 完整开奖号码
    private String sgNum; // 指定号码
    private String sin; // sin 公式杀号
    private String sec; // sec 公式杀号
    private String cos; // cos 公式杀号
    private String cot; // cot 公式杀号
    private String tan; // tan 公式杀号

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

    public String getSgNumber() {
        return sgNumber;
    }

    public void setSgNumber(String sgNumber) {
        this.sgNumber = sgNumber;
    }

    public String getSgNum() {
        return sgNum;
    }

    public void setSgNum(String sgNum) {
        this.sgNum = sgNum;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public String getCot() {
        return cot;
    }

    public void setCot(String cot) {
        this.cot = cot;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    @Override
    public int compareTo(KillNumberVO o) {
        return o.getIssue().compareTo(this.issue);
    }
}
