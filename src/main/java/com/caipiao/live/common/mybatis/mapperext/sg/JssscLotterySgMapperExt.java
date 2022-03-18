package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.JssscLotterySg;

import java.util.List;
import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface JssscLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(JssscLotterySg updateSg);

    int insertBatch(List<JssscLotterySg> list);
}
