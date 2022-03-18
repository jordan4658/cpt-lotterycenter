package com.caipiao.core.library.vo.lotterymanage.lottery;

import java.util.List;

public class PlaySettingAndOddsInfo{

    private LotteryPlaySettingDTO setting; // 玩法配置信息
    private List<LotteryPlayAllOdds> oddsList; // 具体赔率信息

    public LotteryPlaySettingDTO getSetting() {
        return setting;
    }

    public void setSetting(LotteryPlaySettingDTO setting) {
        this.setting = setting;
    }

    public List<LotteryPlayAllOdds> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<LotteryPlayAllOdds> oddsList) {
        this.oddsList = oddsList;
    }
}
