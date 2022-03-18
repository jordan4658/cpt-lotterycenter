package com.caipiao.live.common.mybatis.mapperext.sg;



import com.caipiao.live.common.mybatis.entity.XjplhcLotterySg;

import java.util.List;
import java.util.Map;

/**
 * @Date:Created in 16:312019/12/30
 * @Descriotion
 * @Author
 **/
public interface XjplhcLotterySgMapperExt {

    int updateByIssue(XjplhcLotterySg record);


    int openCountByExample(Map<String,Object> map);
//
    int insertBatch(List<XjplhcLotterySg> xjplhcLotterySgList);
}
