package com.caipiao.live.common.model.vo;

/**
 * 北京PK10资讯返回类
 *
 * @author lzy
 * @create 2018-07-31 13:57
 **/
public class ThereMemberVO {
    private String key;
    private String type;
    private Integer value;

    public ThereMemberVO() {
    }

    public ThereMemberVO(String key, String type, Integer value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

