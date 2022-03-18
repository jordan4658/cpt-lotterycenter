package com.caipiao.task.server.controller;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.task.server.pojo.LotterySGListVO;
import com.caipiao.task.server.pojo.LotterySGReocrd;
import com.caipiao.task.server.req.LotterySGRecordReq;
import com.caipiao.task.server.service.LotterySGRecordService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 赛果记录查询控制类
 *
 * @author lzy
 * @create 2018-08-27 14:10
 **/
@RestController
@RequestMapping("/sgrecord")
public class SgRecordController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LotterySGRecordService lotterySGRecordService;


    /**
     * APP开奖模块首页彩种ID获取信息（官彩）首页
     *
     * @return
     */
    @PostMapping("/getSgRecordList.json")
    public ResultInfo<PageInfo<LotterySGReocrd>> getSgRecordList(@RequestBody LotterySGRecordReq req) {
        logger.info("getSgRecordList req={}",req);

        PageInfo<LotterySGReocrd> info =  lotterySGRecordService.getSgRecordList(req);

        return ResultInfo.ok(info);
    }

    @PostMapping("/getSgLotteryList")
    public ResultInfo<List<LotterySGListVO>> getSgLotteryList() {
        System.out.println("getSgLotteryList===============");
        return ResultInfo.ok(lotterySGRecordService.getSgLotteryList());
    }

}
