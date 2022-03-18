package com.caipiao.live.common.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚举
 */
public enum WebSectionEnum {
    /**
     * 大厅
     */
    HALL("1", "大厅"),
    /**
     * 精华
     */
    ESSENCE("2", "精华"),
    /**
     * 热门
     */
    HOT("3", "热门"),
    /**
     * 今日
     */
    TODAY("4", "今日"),
    /**
     * 所有
     */
    ALL("5", "所有");

    private String number;
    private String name;

    private WebSectionEnum(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
