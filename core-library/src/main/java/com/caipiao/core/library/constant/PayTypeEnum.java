package com.caipiao.core.library.constant;

/**
 * member_balance_change表的type字段对应的值
 */
public enum PayTypeEnum {

    OFFLINE(1, "线下支付"),

    MANUAL(2, "人工入款"),

    ONLINE(3, "线上支付"),


    ;

    private Integer value;

    private String name;

    PayTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
