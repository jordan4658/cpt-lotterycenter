package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.CqsscLotterySg;

import java.util.List;
import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface CqsscLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(CqsscLotterySg updateSg);

    int insertBatch(List<CqsscLotterySg> list);
}
