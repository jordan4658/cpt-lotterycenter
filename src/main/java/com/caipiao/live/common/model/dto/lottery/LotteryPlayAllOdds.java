package com.caipiao.live.common.model.dto.lottery;

public class LotteryPlayAllOdds {

    private Integer id;

    /** 说明: 玩法id */
    private Integer settingId;

    /** 说明: 名称 */
    private String name;

    private String odds;

    public LotteryPlayAllOdds() {
    }

    public LotteryPlayAllOdds(Integer id, Integer settingId, String name, String odds) {
        this.id = id;
        this.settingId = settingId;
        this.name = name;
        this.odds = odds;
    }

    public static LotteryPlayAllOdds newInstance(Integer id, Integer settingId, String name, String odds) {
        return new LotteryPlayAllOdds(id, settingId, name, odds);
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

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

    @Override
    public String toString() {
        return "LotteryPlayAllOdds{" +
                "id=" + id +
                ", settingId=" + settingId +
                ", name='" + name + '\'' +
                ", odds='" + odds + '\'' +
                '}';
    }
}
