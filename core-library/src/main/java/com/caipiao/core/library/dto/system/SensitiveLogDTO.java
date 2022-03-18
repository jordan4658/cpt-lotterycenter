package com.caipiao.core.library.dto.system;

import com.caipiao.core.library.dto.PageInfoDTO;

/**
 * 敏感操作日志DTO
 */
public class SensitiveLogDTO extends PageInfoDTO {

    //帐号
    private String account;

    //模块名
    private String module;

    //方法名
    private String methods;

    //响应结果，0执行成功，1执行失败，其它不做处理
    private int responseResult = -1;

    //搜索条件--开始时间(YYYY-MM-DD)
    private String startDate;

    //结束时间
    private String endDate;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public int getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(int responseResult) {
        this.responseResult = responseResult;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
