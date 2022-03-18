package com.caipiao.core.library.model;

import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.model.base.BaseInfo;

/**
 * 返回结果封装
 *
 * @param <T>
 * @author Qiang
 * @date 2017年7月27日 上午11:16:40
 */
public class ResultInfo<T> extends BaseInfo {

    /**
     * 返回数据
     */
    private T data;

    public static <T> ResultInfo<T> getInstance() {
        return new ResultInfo<>();
    }

    public ResultInfo() {
        super();
    }

    public ResultInfo(T data) {
        super();
        this.data = data;
    }

    public String toJSONString() {
        return JSONObject.toJSONString(this);
    }

    public static <T> ResultInfo<T> ok(T data) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatus(StatusCode.OK.getCode());
        resultInfo.setInfo(StatusCode.OK.getValue());
        resultInfo.setData(data);
        return resultInfo;
    }

    public static <T> ResultInfo<T> error(String msg) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatus("500");
        resultInfo.setInfo(msg);
        resultInfo.setData(null);
        return resultInfo;
    }

    public static <T> ResultInfo<T> getInstance(T data, StatusCode code) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatus(code.getCode());
        resultInfo.setInfo(code.getValue());
        resultInfo.setData(data);
        return resultInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "data=" + data +
                '}';
    }
}
