package com.caipiao.core.library.vo.ssc;

/**
 * 时时彩遗漏统计VO
 *
 * @author lzy
 * @create 2018-07-02 17:30
 **/
public class SscOmitCountVO {

    private Integer num;

    private Integer total = 0;

    private Integer omit = 0;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOmit() {
        return omit;
    }

    public void setOmit(Integer omit) {
        this.omit = omit;
    }
}
