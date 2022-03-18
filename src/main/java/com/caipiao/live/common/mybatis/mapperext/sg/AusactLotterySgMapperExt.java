package com.caipiao.live.common.mybatis.mapperext.sg;

import com.caipiao.live.common.mybatis.entity.AusactLotterySg;

import java.util.Map;

public interface AusactLotterySgMapperExt {
    int updateByIssue(AusactLotterySg record);

    int openCountByExample(Map<String,Object> map);
}
