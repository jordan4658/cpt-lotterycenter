package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.TensscLotterySg;

import java.util.List;
import java.util.Map;

public interface TensscLotterySgService {

    /**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getTensscNewestSgInfo();

    /**
     * @Title: getJssscNewestSgInfo
     * @Description: 获取10分时时彩开奖 统计信息
     * @author HANS
     * @date 2019年5月15日下午2:00:17
     */
    Map<String, Object> getTensscStasticSgInfo(String date);

	
}
