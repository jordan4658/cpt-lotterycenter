package com.caipiao.task.server.service.impl;

import com.caipiao.core.library.enums.LotteryTableNameEnum;
import com.caipiao.task.server.config.LotterySGRecordConfig;
import com.caipiao.task.server.mapper.LotteryResultBeanMapper;
import com.caipiao.task.server.pojo.LotterySGListVO;
import com.caipiao.task.server.pojo.LotterySGReocrd;
import com.caipiao.task.server.req.LotterySGRecordReq;
import com.caipiao.task.server.service.LotterySGRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 开奖记录
 */
@Service
public class LotterySGRecordServiceImpl implements LotterySGRecordService {

    @Resource
    private LotteryResultBeanMapper lotteryResultBeanMapper;

    @Override
    public PageInfo<LotterySGReocrd> getSgRecordList(LotterySGRecordReq req) {
        return PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> lotteryResultBeanMapper.getSgRecordList(req));
    }

    @Override
    public List<LotterySGListVO> getSgLotteryList() {
        List<LotteryTableNameEnum> lotteryList = LotterySGRecordConfig.getLotteryEnumList();
        List<LotterySGListVO> list = new LinkedList<>();
        lotteryList.stream().forEach(en->list.add(new LotterySGListVO(en.getTableName(),en.getLotteryId())));
        return list;
    }
}
