package com.caipiao.live.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 系统代码值
 * @time: 2020/10/1 14:25
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    SUCCESSCODE(200, "操作成功"),
    UNLEGAL(400, "请求不合法"),
    UNLOGINCODE(401, "用户未登录或已过期"),
    SYSTEM_ERROR(999, "网络繁忙，请稍后重试"),
    BUSSINESS_ERROR(550, "操作失败"),
    PARAM_ERROR(551, "参数有误，请检查!"),
    IP_ERROR(552, "请联系管理员添加服务白名单"),

    ////////////////////业务参数 以1000开始///////////////////////
    REGISTER_BLANK_AREACODE(1000,"请选择区号"),
    REGISTER_BLANK_PHONE(1001,"请填写手机号"),
    REGISTER_BLANK_PASSWORD(1002,"请输入密码"),
    REGISTER_BLANK_CONFIRMPASSWORD(1003,"请输入确认密码"),
    REGISTER_BLANK_SMSCODE(1004,"请填写短信验证码"),
    REGISTER_BLANK_IMGCODE(1005,"请填写图片验证码"),
    REGISTER_WRONG_IMG(1006,"图片验证失败"),
    REGISTER_DIFF_PASSWORD(1007, "两次密码填写不一样!"),
    REGISTER_EXISTS_PHONE(1008,"该手机号已注册"),
    REGISTER_EXISTS_ANCHOR(100801,"该主播账号已注册"),
    REGISTER_ANCHOR_LOSE(100802,"主播信息缺失"),


    LOGIN_PARAM_ERROR(1009,"登录参数有误"),
    LOGIN_NOEXISTS_ACCOUNT(1010, "账号不存在"),
    LOGIN_WRONG_PASSWORD(1011, "密码错误"),
    LOGIN_FROZEN(1012,"账号已被冻结"),
    LOGIN_LOCK_PASSWORD(101201, "账号已被锁定，1小时后解封"),


    RESET_EMPTY_PASSWORD(1013, "请填写密码！"),
    RESET_PARAM_ERROR(1014,"重置密码参数有误"),
    RESET_NO_USER_ERROR(1015,"用户不存在"),


    ANCHOR_ID_ISNULL(1050, "主播userId为空"),
    LIVE_TITLE_ISNULL(1051, "直播间标题为空"),


    PHONE_VALID(1052, "无效的手机号码"),

//    ACCLOGIN_BLANK_ERROR(1000, "请填写账号!"),
//    ACCLOGIN_REGEX_ERROR(1001, "账号不合法"),
//    PASSWORD_BLANK_ERROR(1002, "请输入密码"),
//    PASSWORD_DIFF_ERROR(1003, "两次输入的密码不正确"),
//    ACCLOGIN_EXISTS(1004, "账户已存在"),
//    LOGIN_PARAM_ERROR(1005, "请输入账号或者密码"),
//    ACCLOGIN_NOT_EXISTS(1006, "账户不存在"),
//    ACCLOGIN_ACCOUNT_OR_PASSWORD(1022, "账号或密码错误"),
//    ACCLOGIN_PASSWORD_WRONG(1007, "密码错误"),
//    ACCLOGIN_FREEZE(1008, "账号已被冻结，请联系客服"),
//    ACCLOGIN_DELETE(1009, "账号异常，请联系客服"),
//    FORGET_NOBIND_PHONE(1010, "未绑定手机号码"),
//    FORGET_DIMATCH(1011, "账号和手机号不匹配"),
//    RESET_EMPTY_PASSWORD(1012, "请填写密码！"),
//    RESET_EMPTY_CONFIRM_PASSWORD(1013, "请填写确认密码!"),
//    RESET_PASSWORD_DIMATCH(1014, "两次密码填写不一样!"),
//    BINDPHONE_ONLY(1015, "手机号只能绑定一次!"),
//    PHONE_EMPTY(1016, "手机号不能为空"),
//    PHONECODE_EMPTY(1017, "手机验证码不能为空"),
//    CHANGE_OLD_PASSWORD(1018, "原密码不能为空!"),
//    CHANGE_NEW_PASSWORD(1019, "新密码不能为空!"),
//    CHANGE_CONFIRM_PASSWORD(1020, "确认密码不能为空!"),
//    CHANGE_PASSWORD_DIMATCH(1021, "两次密码填写不一样!"),
//    GOLDCHANGE_ERROR(1022, "账变异常"),
//    CHANGE_PASSWORD_SAME(1023, "原密码与新密码相同！"),
//    UPDATE_BET_ORDER_ERROR(1024, "更新注单状态异常"),
    //上传
    UPLOAD_ERROR_2000(2000, "上传失败"),
    UPLOAD_ERROR_2001(2001, "上次文件不能为空！"),
    UPLOAD_ERROR_2002(2002, "请检查文件格式！"),
    UPLOAD_ERROR_2003(2003, "图片大小不能超过2M"),
    UPLOAD_ERROR_2004(2004, "最多9张图片"),

    //短信
    SMS_CODE_ERROR(2005, "验证码错误"),
    SMS_CODE_FAILED(2006, "验证码已失效"),
    SMS_PHONE_FAILED(2007, "无效的手机号码"),
    SMS_PHONE_LIMIT(2008, "今天短信条数超过限制"),
    SMS_PHONE_EMPTY(2009, "请输入手机号"),

    //图片验证码
    IMG_CODE_ERROR(3001,"图片验证失败"),
    NO_DATA(4041, "没有相关数据"),
    //账变,
    CHANGE_BALANCE_LACK(2009, "钱包余额不足");




    private Integer code;
    private String msg;

}
