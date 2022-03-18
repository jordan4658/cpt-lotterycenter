package com.caipiao.core.library.vo.web;

/**
 * @author lzy
 * @create 2018-10-23 15:48
 **/
public class LhcCountVO {

    private Integer open; // 出现的次数

    private Integer noOpen; // 遗漏的次数

    private String value;

    public LhcCountVO() {
    }

    public LhcCountVO(Integer open, Integer noOpen, String value) {
        this.open = open;
        this.noOpen = noOpen;
        this.value = value;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getOpen() {
        return open;
    }

    public Integer getNoOpen() {
        return noOpen;
    }

    public void setNoOpen(Integer noOpen) {
        this.noOpen = noOpen;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
