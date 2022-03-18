package com.caipiao.core.library.vo.common;

/**
 * @author lzy
 * @create 2018-07-27 15:50
 **/
public class MapStringVO {

    private String type;

    private String value;

    public MapStringVO() {
    }

    public MapStringVO(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
