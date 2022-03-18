package com.caipiao.live.common.model.vo;

/**
 * @author lzy
 * @create 2018-08-04 11:45
 **/
public class ThereIntegerVO {
    //号码
    private Integer num;
    //开奖数量
    private Integer open;
    //未开奖数量
    private Integer noOpen;

    public ThereIntegerVO() {
    }

    public ThereIntegerVO(Integer num, Integer open, Integer noOpen) {
        this.num = num;
        this.open = open;
        this.noOpen = noOpen;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getNoOpen() {
        return noOpen;
    }

    public void setNoOpen(Integer noOpen) {
        this.noOpen = noOpen;
    }
}
