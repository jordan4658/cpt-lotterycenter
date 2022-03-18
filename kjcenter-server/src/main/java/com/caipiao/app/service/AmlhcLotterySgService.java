package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;

import java.util.Map;


/**
 * @ClassName: SslhcLotterySgService
 * @Description: 时时六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午3:31:36
 */
public interface AmlhcLotterySgService {

    /**
     * @Title: getOnelhcNewestSgInfo
     * @Description: 获取时时六合彩开奖
     * @author HANS
     * @date 2019年5月15日下午1:51:21
     */
    ResultInfo<Map<String, Object>> getSslhcNewestSgInfo();

    /**
     * 澳门六合彩：历史开奖
     @param starteDate
     @param endDate
      * @param pageNum
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(String type, int num, String starteDate, String endDate, Integer pageNum, Integer pageSize);


}
