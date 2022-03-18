package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;

import java.util.Map;

public interface FtjspksLotterySgService {
	
	/**
     * 最近一期的开奖结果
     * @return
     */
    Map<String, Object> getNewestSgInfo();


}
