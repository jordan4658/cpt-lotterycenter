package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.MapListVO;

import java.util.List;
import java.util.Map;

public interface TenpksLotterySgServiceReadSg {

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getNewestSgInfo();


    /**
     * 获取今日号码资讯
     *
     * @param type
     * @return
     */
    ResultInfo<List<MapListVO>> todayNumber(String type);

    /**
     * @Title: getFivePksSgLong
     * @Description: 10分PKS长龙数据
     * @author HANS
     * @date 2019年5月17日下午11:00:45
     */
    List<Map<String, Object>> getTenPksSgLong();
}
