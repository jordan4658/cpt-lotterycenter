package com.caipiao.live.common.model.vo;

/**
 * 北京PK10资讯返回类
 *
 * @author lzy
 * @create 2018-08-06 15:46
 **/
public class MapIntegerVO {
    private Integer number;
    private Integer times;

    public MapIntegerVO() {
    }

    public MapIntegerVO(Integer number, Integer times) {
        this.number = number;
        this.times = times;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}

