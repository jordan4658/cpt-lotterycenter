package com.caipiao.core.library.model.base;


import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.tool.TimeHelper;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2017-12-04 13:36
 */
public class BaseInfo {

    /**
     * 状态码
     */
    private String status;

    /**
     * 时间
     */
    private Long time;

    /**
     * 状态描述
     */
    private String info;

    public BaseInfo() {
        this.status = StatusCode.OK.getCode();
        this.time = TimeHelper.time();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "status='" + status + '\'' +
                ", time=" + time +
                ", info='" + info + '\'' +
                '}';
    }
}
