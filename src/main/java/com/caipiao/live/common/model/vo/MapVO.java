package com.caipiao.live.common.model.vo;

/**
 * 六合彩赛果接口返回值
 *
 * @author lzy
 * @create 2018-07-19 16:45
 **/
public class MapVO {
    private String type;
    private Integer num;

    public MapVO() {
    }

    public MapVO(String type, Integer num) {
        this.type = type;
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

