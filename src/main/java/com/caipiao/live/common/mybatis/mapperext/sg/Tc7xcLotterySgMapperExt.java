package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.Tc7xcLotterySg;

import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface Tc7xcLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(Tc7xcLotterySg updateSg);
}
