package com.caipiao.live.common.model.vo;

/**
 * web端六合彩尾数大小VO
 *
 * @author lzy
 * @create 2018-10-23 17:35
 **/
public class LhcWsdxVO {

    private String issue; // 期号

    private String date; // 开奖日期

    private String zhengMa; //正码

    private String teMa; //特码

    private String teMaDx; //特码大小

    private String teMaDs; //特码单双

    private Integer total; //总和

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZhengMa() {
        return zhengMa;
    }

    public void setZhengMa(String zhengMa) {
        this.zhengMa = zhengMa;
    }

    public String getTeMa() {
        return teMa;
    }

    public void setTeMa(String teMa) {
        this.teMa = teMa;
    }

    public String getTeMaDx() {
        return teMaDx;
    }

    public void setTeMaDx(String teMaDx) {
        this.teMaDx = teMaDx;
    }

    public String getTeMaDs() {
        return teMaDs;
    }

    public void setTeMaDs(String teMaDs) {
        this.teMaDs = teMaDs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
