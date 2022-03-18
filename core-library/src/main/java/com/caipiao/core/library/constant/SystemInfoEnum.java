package com.caipiao.core.library.constant;

public enum SystemInfoEnum {

    /**
     * 限制ip登录状态
     */
    ASTRICT_IP_LOGIN_STATUS(1, "限制ip登录状态"),

    /**
     * 限制的ip
     */
    ASTRICT_IP_GROUP(1, "限制的ip"),

    /**
     * 提示刷新时间
     */
    HINT_REFRESH_TIME(1, "提示刷新时间"),

    /**
     * 提示声音开关
     */
    HINT_VOICE_STATUS(1, "提示声音开关"),

    FREEZE_ACCOUNT_STATUS(2, "冻结账号"),

    SHENG_XIAO(2, "当前年生肖"),

    ODDS_SET(2, "赔率设置"),

    REGISTER_MEMBER_ODDS(2, "注册用户赔率"),

    REGISTER_MEMBER_BACKWATER(2, "注册用户返水"),

    DEMO_ACCOUNT_REGISTER_TIMES(2, "试玩账号注册次数"),

    REVOKE_SECOND(2, "开放撤单多少秒后生效"),

    BIND_BANK_CARD(2, "充值必须绑卡"),

    BIND_PHONE(2, "开启手机绑定"),

    BIND_ALIPAY(2, "支付宝绑定显示"),

    BIND_BANK_CARD_AMOUNT(2, "每人可绑银行卡张数"),

    WITHDRAWAL_AMOUNT(2, "提现额度设置"),

    MAX_WITHDRAW_DEPOSIT_TIMES(2, "每天最大提现次数"),

    GENERAL_WITHDRAW_DEPOSIT_MAX(2, "普通会员单次提现最大额度"),

    GENERAL_WITHDRAW_DEPOSIT_MIN(2, "普通会员单次提现最小额度"),

    TOPUP_WITHDRAW_DEPOSIT_MAX(2, "充值会员单次提现最大额度"),

    TOPUP_WITHDRAW_DEPOSIT_MIN(2, "充值会员单次提现最小额度"),

    NO_TOP_UP_START_TIME(2, "不可提现开始时间"),

    NO_TOP_UP_END_TIME(2, "不可提现结束时间"),

    WITHDRAW_DEPOSIT_EXPLAIN(2, "提现说明"),

    IOS_FIRST_REGISTER_GIVE_MONEY(3, "每台IOS手机第一次注册赠送金额"),

    ANDROID_FIRST_REGISTER_GIVE_MONEY(3, "每台ANDROID手机第一次注册赠送金额"),

    BACKWATER_VIP_SCALE(3, "VIP返水比例"),

    BACKWATER_PLATFORM_SCALE(3, "后台返水比例"),

    BACKWATER_PATTERN(3, "返水模式"),

    BACKWATER_WITHDRAW(3, "返水可提"),

    BE_SHARER_BET_MIN_MONEY(3, "被分享者最低投注金额"),

    SHARER_GIVE_MONEY(3, "分享者获得赠送金额"),

    SHARER_GIVE_MONEY_WAY(3, "分享者获得赠送金额方式"),

    BE_SHARER_IOS_REGISTER_MONEY(3, "被分享者IOS成功注册获得金额"),

    BE_SHARER_ANDROID_REGISTER_MONEY(3, "被分享者ANDROID成功注册获得金额"),

    BE_SHARER_GIVE_MONEY_WAY(3, "被分享者获得赠送金额方式"),

    SHARE_BACKWATER_QUOTA(3, "分享返水限额类型"),

    SHARE_BACKWATER_SCALE(3, "分享返水比例(‰)"),

    SHARE_BACKWATER_SCALE_ONE(3, "分享返水比例1层(‰)"),

    SHARE_BACKWATER_SCALE_TWO(3, "分享返水比例2层(‰)"),

    WEEK_FIRST_TOP_UP_STATUS(3, "周周首充活动状态"),

    WEEK_FIRST_TOP_UP_SCALE(3, "周周首充活动赠送比例(%)"),

    WEEK_FIRST_TOP_UP_MAX_MONEY(3, "周周首充活动礼金上限"),

    ACTIVITY_FIRST_TOP_UP_STATUS(3, "活动首充活动状态"),

    ACTIVITY_FIRST_TOP_UP_SCALE(3, "活动首充活动赠送比例(%)"),

    ACTIVITY_FIRST_TOP_UP_MAX_MONEY(3, "活动首充活动礼金上限"),

    ACTIVITY_FIRST_TOP_UP_START_TIME(3, "活动首充活动开启时间"),

    ACTIVITY_FIRST_TOP_UP_END_TIME(3, "活动首充活动结束时间"),

    ACTIVITY_FIRST_TOP_UP_BACK_SCALE(3, "活动首充活动返还比例");

    private int type;

    private String remark;

    SystemInfoEnum(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
