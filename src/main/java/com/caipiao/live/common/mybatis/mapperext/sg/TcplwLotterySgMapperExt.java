package com.caipiao.live.common.mybatis.mapperext.sg;


import com.caipiao.live.common.mybatis.entity.TcplwLotterySg;

import java.util.Map;

/**
 * @Date:Created in 16:142019/12/7
 * @Descriotion
 * @Author
 **/
public interface TcplwLotterySgMapperExt {
    int openCountByExample(Map<String, Object> map);

    int updateByIssue(TcplwLotterySg updateSg);
}
