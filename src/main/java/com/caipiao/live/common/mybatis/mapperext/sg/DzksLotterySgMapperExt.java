package com.caipiao.live.common.mybatis.mapperext.sg;

import com.caipiao.live.common.mybatis.entity.DzksLotterySg;

import java.util.Map;

/**
 * @Date:Created in 19:352019/12/11
 * @Descriotion
 * @Author
 **/
public interface DzksLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(DzksLotterySg updateSg);
}
