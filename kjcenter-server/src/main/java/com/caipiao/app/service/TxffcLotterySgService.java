package com.caipiao.app.service;

import com.caipiao.core.library.dto.result.TxffcMissDTO;
import com.caipiao.core.library.dto.result.TxffcSizeMissDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.TxffcLotterySg;
import com.mapper.domain.TxffcRecommend;

import java.util.List;
import java.util.Map;

public interface TxffcLotterySgService {
    /**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getNewestSgInfo();

    /**
     * 腾讯分分彩：历史开奖
     @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(String date, Integer pageNum, Integer pageSize);

    /**
     * 腾讯分分彩：历史开奖 最近10期
      * @param pageNum
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSgLately(Integer pageNum, Integer pageSize);

    /**
     * @Title: getJssscNewestSgInfo
     * @Description: 获取比特币分分彩开奖 统计信息
     * @author HANS
     * @date 2019年5月15日下午2:00:17
     */
    Map<String, Object> getTxffcStasticSgInfo(String date);



}
