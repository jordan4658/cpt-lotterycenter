package com.caipiao.core.library.dto.bet;

/**
 * 接收参数模型
 *
 * @author lzy
 * @create 2018-07-19 14:37
 **/
public class RequestInfo <T> {

    private String apisign;

    private T data;

    public String getApisign() {
        return apisign;
    }

    public void setApisign(String apisign) {
        this.apisign = apisign;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "apisign='" + apisign + '\'' +
                ", data=" + data +
                '}';
    }

}
