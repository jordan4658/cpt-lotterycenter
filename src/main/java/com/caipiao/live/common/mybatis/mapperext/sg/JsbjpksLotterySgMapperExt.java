package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.JsbjpksLotterySg;

import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface JsbjpksLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(JsbjpksLotterySg updateSg);
}
