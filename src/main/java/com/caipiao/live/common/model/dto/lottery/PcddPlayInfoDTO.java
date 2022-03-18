package com.caipiao.live.common.model.dto.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;

import java.util.List;

public class PcddPlayInfoDTO {
    private Integer id; // ID
    private Integer settingId; // 配置id
    private String name; // 名称
    private String section; // 取值范围
    private LotteryPlaySetting setting; // 配置信息
    private List<LotteryPlayOddsDTO> oddsList; // 具体赔率信息

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

    public LotteryPlaySetting getSetting() {
        return setting;
    }

    public void setSetting(LotteryPlaySetting setting) {
        this.setting = setting;
    }

    public List<LotteryPlayOddsDTO> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<LotteryPlayOddsDTO> oddsList) {
        this.oddsList = oddsList;
    }
}
