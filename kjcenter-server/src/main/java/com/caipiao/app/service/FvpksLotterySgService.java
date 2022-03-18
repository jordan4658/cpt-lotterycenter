package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;
import java.util.Map;

public interface FvpksLotterySgService {

    /**
     * 最近一期的开奖结果
     * @return
     */
    ResultInfo<Map<String,Object>> getNewestSgInfo();

}
