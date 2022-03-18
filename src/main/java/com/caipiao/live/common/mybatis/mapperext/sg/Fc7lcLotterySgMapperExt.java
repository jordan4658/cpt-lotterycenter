package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.Fc7lcLotterySg;

import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface Fc7lcLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(Fc7lcLotterySg updateSg);
}
