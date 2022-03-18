package com.caipiao.task.server.service;

import com.caipiao.task.server.pojo.LotterySGListVO;
import com.caipiao.task.server.pojo.LotterySGReocrd;
import com.caipiao.task.server.req.LotterySGRecordReq;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 彩票开奖记录
 */
public interface LotterySGRecordService {

    /**
     * 查询开奖记录
     * @param req
     * @return
     */
    PageInfo<LotterySGReocrd> getSgRecordList(LotterySGRecordReq req);

    List<LotterySGListVO> getSgLotteryList();
}
