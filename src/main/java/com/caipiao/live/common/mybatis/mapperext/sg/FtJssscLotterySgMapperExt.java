package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.FtjssscLotterySg;

import java.util.List;
import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface FtJssscLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(FtjssscLotterySg updateSg);

    int insertBatch(List<FtjssscLotterySg> list);
}
