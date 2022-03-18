package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.dto.lottery.LotteryPlaySettingDTO;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;

import java.util.List;
import java.util.Map;

public interface LotteryPlaySettingServiceReadSg {

    LotteryPlaySettingDTO queryByPlayId(Integer playId);

    PageResult<List<LotteryPlaySettingDTO>> querySettingListByCateId(Integer cateId, Integer pageNo, Integer pageSize);

    LotteryPlaySetting queryLotteryPlaySettingForPlayId(Integer playId, Integer playTagId);

    /**
     * 查询指定玩法的设置信息
     *
     * @param playIdList
     * @return
     */
    List<LotteryPlaySetting> queryLotteryPlaySetting(List<Integer> playIdList);

    /**
     * 查询指定玩法的设置信息
     * @param playIdList
     * @return Map<playId,#LotteryPlaySetting>
     */
    Map<String, LotteryPlaySetting> queryLotteryPlaySettingMap(List<Integer> playIdList);

}
