package com.caipiao.app.controller;

import com.alibaba.fastjson.JSON;
import com.caipiao.app.service.LotterySgService;
import com.caipiao.core.library.dto.result.LotterySgDTO;
import com.caipiao.core.library.model.RequestInfo;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzy
 * @create 2018-09-07 18:25
 **/
@RestController
@RequestMapping(value = "/sg", method = RequestMethod.POST)
public class SgController {
    private static final Logger logger = LoggerFactory.getLogger(SgController.class);


    @Autowired
    private LotterySgService lotterySgService;

    /**
     * 开奖中心获取最新赛果信息
     *
     * @return
     */
//    @PostMapping("/getNewSgInfoById")
    @RequestMapping(value = "getNewSgInfoById.json")
    public ResultInfo<Map<String, Object>> getNewSgInfoById(@RequestBody RequestInfo<LotterySgDTO> requestInfo) {
        logger.info("getNewSgInfoById.json参数：" + JSON.toJSONString(requestInfo));
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        //检查参数
        if (BaseUtil.checkRequestInfo(requestInfo, resultInfo)) {
            return resultInfo;
        }
        LotterySgDTO requestData = requestInfo.getData();
        resultInfo = lotterySgService.getNewSgInfoById(String.valueOf(requestData.getId()));
        return resultInfo;
    }

    /**
     * 开奖中心获取历史赛果信息
     *
     * @return
     */
    @RequestMapping(value = "getLsSgInfoByIdAndDay.json")
    public ResultInfo<Map<String, Object>> getLsSgInfoByIdAndDay(@RequestBody RequestInfo<LotterySgDTO> requestInfo) {
        logger.info("getLsSgInfoByIdAndDay.json参数：{}", JSON.toJSONString(requestInfo));
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        try {
            //检查参数
            if (BaseUtil.checkRequestInfo(requestInfo, resultInfo)) {
                return resultInfo;
            }
            LotterySgDTO requestData = requestInfo.getData();
            resultInfo = lotterySgService.getLsSgInfoByIdAndDay(String.valueOf(requestData.getId()), String.valueOf(requestData.getDate()),
                    Integer.valueOf(requestData.getPageNum()), Integer.valueOf(requestData.getPageSize()));
        } catch (Exception e) {
            logger.info("getLsSgInfoByIdAndDay出错：", e);
        }

        return resultInfo;
    }

    /**
     * 开奖中心获取历史赛果信息(澳门六合彩)
     *
     * @return
     */
    @RequestMapping(value = "getLsSgInfoByIdAndType.json")
    public ResultInfo<Map<String, Object>> getLsSgInfoByIdAndType(@RequestBody RequestInfo<LotterySgDTO> requestInfo) {
        logger.info("getLsSgInfoByIdAndDay.json参数：{}", JSON.toJSONString(requestInfo));
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        try {
            //检查参数
            if (BaseUtil.checkRequestInfo(requestInfo, resultInfo)) {
                return resultInfo;
            }
            LotterySgDTO requestData = requestInfo.getData();
            String id = requestData.getId() == null ? null : String.valueOf(requestData.getId());
            String type = requestData.getType() == null ? null : String.valueOf(requestData.getType());
            Integer number = requestData.getNumber() == null ? 0 : Integer.valueOf(requestData.getNumber());
            String startDate = requestData.getStartDate() == null ? "" : String.valueOf(requestData.getStartDate());
            String endDate = requestData.getEndDate() == null ? "" : String.valueOf(requestData.getEndDate());
            Integer pageNumber = requestData.getPageNum() == null ? null : Integer.valueOf(requestData.getPageNum());
            Integer pageSize = requestData.getPageSize() == null ? null : Integer.valueOf(requestData.getPageSize());

            resultInfo = lotterySgService.getLsSgInfoByIdAndType(id, type, number,
                    startDate, endDate, pageNumber, pageSize);
        } catch (Exception e) {
            logger.info("getLsSgInfoByIdAndDay出错：", e);
        }

        return resultInfo;
    }

    /**
     * 开奖中心获取历史幸运飞艇私彩
     *
     * @return
     */
    @RequestMapping(value = "getLsSgInfoByDate.json")
    public ResultInfo<Map<String, Object>> getLsSgInfoByDate(@RequestBody RequestInfo<LotterySgDTO> requestInfo) {
        logger.info("getLsSgInfoByDate.json参数：{}", JSON.toJSONString(requestInfo));
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        try {
            //检查参数
            if (BaseUtil.checkRequestInfo(requestInfo, resultInfo)) {
                return resultInfo;
            }
            LotterySgDTO requestData = requestInfo.getData();
            String issue = requestData.getIssue() == null ? null : String.valueOf(requestData.getIssue());
            String date = requestData.getDate() == null ? "" : String.valueOf(requestData.getDate());
            Integer pageNumber = requestData.getPageNum() == null ? null : Integer.valueOf(requestData.getPageNum());
            Integer pageSize = requestData.getPageSize() == null ? null : Integer.valueOf(requestData.getPageSize());

            resultInfo = lotterySgService.getLsSgInfoByDate(issue,
                    date, pageNumber, pageSize);
        } catch (Exception e) {
            logger.info("getLsSgInfoByIdAndDay出错：", e);
        }

        return resultInfo;
    }

    /**
     * 开奖中心获取统计赛果信息
     *
     * @return
     */
    @RequestMapping(value = "getStatisticSgInfoById.json")
    public ResultInfo<Map<String, Object>> getStatisticSgInfoById(@RequestBody RequestInfo<LotterySgDTO> requestInfo) {
        logger.info("getStatisticSgInfoById.json参数：{}", JSON.toJSONString(requestInfo));
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        //检查参数
        if (BaseUtil.checkRequestInfo(requestInfo, resultInfo)) {
            return resultInfo;
        }
        LotterySgDTO requestData = requestInfo.getData();
        resultInfo = lotterySgService.getStatisticSgInfoById(String.valueOf(requestData.getId()), String.valueOf(requestData.getDate()));
        return resultInfo;
    }


}
