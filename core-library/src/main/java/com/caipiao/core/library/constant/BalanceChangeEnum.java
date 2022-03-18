package com.caipiao.core.library.constant;

/**
 * member_balance_change表的type字段对应的值
 */
public enum BalanceChangeEnum {

    RECHARGE(1, "充值转入"),

    CASH_ADVANCE(2, "提现转出"),

    ADVANCE_FAIL(3, "提现失败"),

    BET_ORDER(4, "投注"),

    APPEND_BET_BACK(5, "停追退款"),

    BET_ORDER_BAK(6, "主动撤单"),

    BET_ORDER_BAK_SYSTEM(7, "系统撤单"),

    BET_ORDER_BACK(8, "VIP返点"),

    VIP_UPGRADE_AWARDS (9, "VIP升级奖励"),

    BET_ACCOUNT(10, "中奖彩金"),

    REVOKE_AWARD (11, "撤销开奖"),

    BET_BALANCE(12, "打和退还"),

    ACTIVITY_ACCOUNT(13, "活动礼金"),

    ORDER_PUSH_BONUS(14, "推单分红"),

    ORDER_FOLLOW_BONUS(15, "跟单分红"),

    XGKTED(16, "修改可提额度"),

    REGISTER_AWARD(17, "注册赠送"),

    SHARE_AWARD(18, "分享赠送"),

    SHARE_BACK(19, "分享返水"),
    
    AG_IN(20, "IN"),
    
    AG_OUT(21, "OUT");


    private Integer value;
    private String name;

    BalanceChangeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(int value) {
        switch (value) {
            case 1:
                return "充值转入";
            case 2:
                return "提现转出";
            case 3:
                return "提现失败";
            case 4:
                return "投注";
            case 5:
                return "停追退款";
            case 6:
                return "主动撤单";
            case 7:
                return "系统撤单";
            case 8:
                return "VIP返点";
            case 9:
                return "升级奖励";
            case 10:
                return "中奖彩金";
            case 11:
                return "撤销开奖";
            case 12:
                return "打和退还";
            case 13:
                return "活动礼金";
            case 14:
                return "推单分红";
            case 15:
                return "跟单分红";
            case 16:
                return "修改可提";
            case 17:
                return "注册赠送";
            case 18:
                return "分享赠送";
            case 19:
                return "分享返水";
            default:
                return "";
        }
    }
}
