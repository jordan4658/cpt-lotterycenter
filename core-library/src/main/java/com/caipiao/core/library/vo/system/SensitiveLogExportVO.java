package com.caipiao.core.library.vo.system;

/**
 * @Author: admin
 * @Description:系统日志导出VO
 * @Version: 1.0.0
 * @Date; 2017-11-02 15:16
 */
public class SensitiveLogExportVO {

    /**
     * 序号
     */
    private int num;

    /**
     * 字段: admin_operate_log.admin_account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 用户帐号
     *
     * @mbggenerated
     */
    private String adminAccount;

    /**
     * 字段: admin_operate_log.admin_ip<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 当前登录ip地址
     *
     * @mbggenerated
     */
    private String adminIp;

    /**
     * 字段: admin_operate_log.request_type<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 请求类型，0：GET请求，1：POST
     *
     * @mbggenerated
     */
    private String requestType;

    /**
     * 字段: admin_operate_log.url<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作对应的url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * 字段: admin_operate_log.module<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 模块名字
     *
     * @mbggenerated
     */
    private String module;

    /**
     * 字段: admin_operate_log.methods<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 方法名
     *
     * @mbggenerated
     */
    private String methods;

    /**
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: admin_operate_log.response_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 19<br/>
     * 说明: 响应时间（毫秒）
     *
     * @mbggenerated
     */
    private Long responseTime;

    /**
     * 说明: 响应结果，0：执行成功，1：执行失败
     */
    private String responseResult;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminIp() {
        return adminIp;
    }

    public void setAdminIp(String adminIp) {
        this.adminIp = adminIp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }
}
