package com.caipiao.core.library.constant;

/**
 * 状态码
 * @author Qiang
 * @date 2017年7月27日 上午11:16:58
 *
 */
public enum StatusCode {

	/** app接口
	 * 注意：业务代码返回统一为中文，由aop切换为英文提示
	 * code码成功返回 1开头的为中文提示，3开头的为英文提示
	 * 错误返回：-1
	 * */

	OK("1", "成功"),
	FAIL("-1", "失败"),
	OK_EN("3", "success"),
	NOPASS("99", "nopass"),
	
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

	SENSITIVE_ERROR("1111", "包含敏感词汇"),
	SENSITIVE_ERROR_EN("3111", "param contains sensitive words"),

    MENU_STATUS_ERROR("1002", "上一级菜单状态为禁用，子菜单不能设置可用"),

    PHONE_ERROR("1004", "手机号码有误，请重新输入！"),
    PHONE_ERROR_EN("3004", "The phone number is wrong,Please reenter it!"),

    NAME_ERROR("1005", "名称有误，请重新输入！"),
    NAME_ERROR_EN("3005", "The name is wrong,Please reenter it!"),

    ACCOUNT_ERROR("1006", "帐号有误，请重新输入！"),
    ACCOUNT_ERROR_EN("3006", "Account number is wrong,Please reenter it!"),

    ACCOUNT_REPEAT("1007", "该帐号已存在！"),
    ACCOUNT_REPEAT_EN("3007", "Account has already existed!"),

	ACCOUNT_FREEZE("1111", "账号冻结！"),

    PASSWORD_ERROR("1008", "密码有误，请重新输入！"),
    PASSWORD_ERROR_EN("3008", "The password is wrong,Please reenter it!"),

    ROLE_EMPTY("1009", "该角色不存在！"),

	INVALID_TOKEN("1010", "登录已失效！"),
	INVALID_TOKEN_EN("3010", "TOKEN Checkout failure"),

	OVERDUE_TOKEN("1011", "登录已过期，请重新登录！"),
	OVERDUE_TOKEN_EN("3011", "TOKEN expiration，Please log in again"),

	EMAIL_FORM_ERROR("1012", "Email格式不正确！"),
	EMAIL_FORM_ERROR_EN("3012", "Incorrect mailbox format!"),

	PHONE_FORM_ERROR("1013", "手机号码格式不正确！"),
	PHONE_FORM_ERROR_EN("3013", "The format of the phone number is not correct!"),

	CHECK_IDENTIFYING_CODE_ERROR("1014", "验证码校验失败！"),
	CHECK_IDENTIFYING_CODE_ERROR_EN("3014", "Verification code verification failure!"),

	ACCOUNT_EMPTY("1015", "该账号未注册！"),
	ACCOUNT_EMPTY_EN("3015", "Account number does not exist!"),

	FAILED("1016", "失败"),
	FAILED_EN("3016", "failed"),

	UPDATA_PASSWORD_SUCCESS("1017", "修改密码成功！"),
	UPDATA_PASSWORD_SUCCESS_EN("3017", "Revise the password successfully!"),

	AREAMARK_ERROR("1018", "AreaMark is not number!"),

	PASSWORD_FORM_ERROR("1019", "密码格式不正确！"),
	PASSWORD_FORM_ERROR_EN("3019", "The format of the password is not correct!"),

	CAPTCHA_EXCEED_MAXNUM_DAY("1020", "验证码发送超过了当天允许发送的最大次数!"),

	CAPTCHA_SEND_REPEAT("1021", "请勿频繁发送验证码!"),

	CAPTCHA_SEND_ERROR("1022", "验证码发送失败!"),

	BANNER_SOLD_OUT("1023", "亲，该活动已下架！"),

	NO_UPLOAD_FILE("1024", "请选择上传文件！"),
	NO_UPLOAD_FILE_EN("3024", "Please choose to upload a file!"),

	UPLOAD_IMG_FAILURE("1025", "上传失败，图片格式不正确，目前支持的图片格式为[jpg,png,gif,tif,bmp]"),
	UPLOAD_IMG_FAILURE_EN("3025", "Upload failure！"),

	PHONE_REPEAT("1026", "该手机号已存在！"),
	PHONE_REPEAT_EN("3026", "Phone has already existed!"),

	UPDATA_PAY_PASSWORD_SUCCESS("1027", "设置支付密码成功！"),
	UPDATA_PAY_PASSWORD_SUCCESS_EN("3027", "Revise the pay password successfully!"),

	PROMOTION_CODE_ERROR("1028", "邀请码错误！"),
	PROMOTION_CODE_ERROR_EN("3028", "Promotion code error!"),

	XXX_XXX_XXX("9999", "XXX_XXX_XXX");

	private final String code;;
	private final String value;

	StatusCode(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static StatusCode getStatusCode(String code) {
		for (StatusCode statusCode : values()) {
			if (code.equals(statusCode.getCode())) {
				return statusCode;
			}
		}
		return null;
	}

}
