package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;

import java.util.Map;

public interface DzksLotterySgServiceReadSg {

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    Map<String, Object> getNewestSgInfo();

    /**
     * 历史开奖
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize);

}
