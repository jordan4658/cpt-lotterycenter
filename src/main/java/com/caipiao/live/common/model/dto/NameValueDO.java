package com.caipiao.live.common.model.dto;

public class NameValueDO {
    private String name;
    private Integer value;
    //基数
    private Integer maxvalue;

    public NameValueDO() {
    }

    public NameValueDO(String name, Integer value) {
        super();
        this.name = name;
        this.value = value;
    }

    public NameValueDO(String name, Integer value, Integer maxvalue) {
        super();
        this.name = name;
        this.value = value;
        this.maxvalue = maxvalue;
    }

    public Integer getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(Integer maxvalue) {
        this.maxvalue = maxvalue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
