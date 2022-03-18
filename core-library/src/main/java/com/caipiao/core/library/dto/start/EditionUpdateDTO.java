package com.caipiao.core.library.dto.start;

public class EditionUpdateDTO {
    private Integer appId;      //1：安卓购彩版 2：ios购彩版 3：安卓资讯版 4：ios资讯版
    private String appEdittion;     //当前版本号

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppEdittion() {
        return appEdittion;
    }

    public void setAppEdittion(String appEdittion) {
        this.appEdittion = appEdittion;
    }
}
