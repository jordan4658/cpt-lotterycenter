package com.caipiao.core.library.model.aop;

/**
 * @Author: admin
 * @Description: aop记录日志model
 * @Version: 1.0.0
 * @Date; 2018/5/26 026 18:30
 */
public class OperateLogModel {

    /**
     * 字段: operate_log.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明:
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: operate_log.member_id<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 字段: operate_log.member_account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 用户帐号
     *
     * @mbggenerated
     */
    private String memberAccount;

    /**
     * 字段: operate_log.member_ip<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 当前登录ip地址
     *
     * @mbggenerated
     */
    private String memberIp;

    /**
     * 字段: operate_log.request_type<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 请求类型，GET，POST
     *
     * @mbggenerated
     */
    private String requestType;

    /**
     * 字段: operate_log.url<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作对应的url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * 字段: operate_log.module<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 模块名字
     *
     * @mbggenerated
     */
    private String module;

    /**
     * 字段: operate_log.methods<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 方法名
     *
     * @mbggenerated
     */
    private String methods;

    /**
     * 字段: operate_log.param<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 6000<br/>
     * 说明: 参数
     *
     * @mbggenerated
     */
    private String param;

    /**
     * 字段: operate_log.create_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 19<br/>
     * 说明: 创建时间（秒）
     *
     * @mbggenerated
     */
    private Long createTime;

    /**
     * 字段: operate_log.response_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 19<br/>
     * 说明: 响应时间（毫秒）
     *
     * @mbggenerated
     */
    private Long responseTime;

    /**
     * 字段: operate_log.response_result<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 响应结果，0：执行成功，1：执行失败
     *
     * @mbggenerated
     */
    private Integer responseResult;

    /**
     * 字段: operate_log.log_type<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 类型，0：manager，1：app
     *
     * @mbggenerated
     */
    private Integer logType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getMemberIp() {
        return memberIp;
    }

    public void setMemberIp(String memberIp) {
        this.memberIp = memberIp;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(Integer responseResult) {
        this.responseResult = responseResult;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }
}
