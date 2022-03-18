package com.caipiao.core.library.enums;

/**
 * 状态码
 * @author Qiang
 * @date 2017年7月27日 上午11:16:58
 *
 */
public enum SystemTipEnum {

	/** web接口
	 * 注意：业务代码返回统一为中文，由aop切换为英文提示
	 * code码成功返回 1开头的为中文提示，3开头的为英文提示
	 * 错误返回：-1
	 * */

	SUCCESS("1", "Success"),
	FAIL("-1", "Fail"),
	
	NOPASS("100007","nopass"),
	
	LOGIN_ERROR("-10001", "无用户"),
	LOGIN_ERROR_EN("-10001", "No user"),

	UNKNOW_ERROR("-1", "未知错误"),
	UNKNOW_ERROR_EN("-3", "Unknown error"),

	NO_DATA("4041", "没有相关数据"),
	NO_DATA_EN("4141", "no data"),

	SIGN_FAILED("1000", "签名不正确"),
	SIGN_FAILED_EN("3000", "signature error"),

	PARAM_ERROR("1001", "参数不正确"),
	PARAM_ERROR_EN("3001", "param error"),
	
	TITLE_EMPTY("100005", "标题不能为空"),
	TITLE_EMPTY_EN("100005", "title empty"),
	
	CONTENT_EMPTY("100006", "内容不能为空"),
	CONTENT_EMPTY_EN("100006", "content empty"),

	SENSITIVE_ERROR("1111", "包含敏感词汇"),
	SENSITIVE_ERROR_EN("3111", "param contains sensitive words"),
	
	PLEASE_WAIT("100001", "请稍候操作"),
	PLEASE_WAIT_EN("110001", "Please wait"),
	
	INVALID_RECOMMENDED("100002", "无效操作"),
	INVALID_RECOMMENDED_EN("110002", "nvalid recommended"),
	
	VOTE_FAILED("100003", "亲，你的投票失败啦"),
	VOTE_FAILED_EN("110003", "Sorry, your vote failed"),
	
	OVER_LIMIT("100004", "对不起，你的投票次数超过限制啦"),
	OVER_LIMIT_EN("110004", "Sorry, you are over the limit"),
	
	XXX_XXX_XXX("9999", "XXX_XXX_XXX");

	private final String code;;
	private final String value;

	SystemTipEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static SystemTipEnum getStatusCode(String code) {
		for (SystemTipEnum statusCode : values()) {
			if (code.equals(statusCode.getCode())) {
				return statusCode;
			}
		}
		return null;
	}

}
