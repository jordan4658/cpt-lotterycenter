package com.caipiao.live.common.mybatis.mapperext.sg;

import com.caipiao.live.common.mybatis.entity.DzxyftLotterySg;

import java.util.Map;

/**
 * @Date:Created in 16:522019/12/12
 * @Descriotion
 * @Author
 **/
public interface DzxyftLotterySgMapperExt {
    int updateByIssue(DzxyftLotterySg record);

    int openCountByExample(Map<String,Object> map);
}
