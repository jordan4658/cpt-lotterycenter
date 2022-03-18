package com.caipiao.live.common.model.dto.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryPlay;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;

import java.util.List;

public class LhcPlayInfoDTO extends LotteryPlay {

    private LotteryPlaySetting setting; // 玩法配置信息
    private List<LotteryPlayOddsDTO> oddsList; // 具体赔率信息

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
