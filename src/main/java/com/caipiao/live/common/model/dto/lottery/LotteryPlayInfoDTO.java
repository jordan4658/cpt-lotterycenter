package com.caipiao.live.common.model.dto.lottery;


import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;

public class LotteryPlayInfoDTO {
    private Integer id; // ID
    private Integer settingId; // 配置id
    private String name; // 名称
    private String section; // 取值范围
    private String odds; // 赔率
    private LotteryPlaySetting setting; // 配置信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public LotteryPlaySetting getSetting() {
        return setting;
    }

    public void setSetting(LotteryPlaySetting setting) {
        this.setting = setting;
    }
}
