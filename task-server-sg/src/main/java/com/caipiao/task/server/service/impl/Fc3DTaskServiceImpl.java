package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.callable.ThreadTaskByw;
import com.caipiao.task.server.callable.ThreadTaskCpk;
import com.caipiao.task.server.callable.ThreadTaskKcw;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.Fc3DTaskService;
import com.caipiao.task.server.util.Lauar;
import com.mapper.Fc3dLotterySgMapper;
import com.mapper.domain.Fc3dLotterySg;
import com.mapper.domain.Fc3dLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class Fc3DTaskServiceImpl extends CommonServiceImpl implements Fc3DTaskService {
    private static final Logger logger = LoggerFactory.getLogger(Fc3DTaskServiceImpl.class);

    @Autowired
    private Fc3dLotterySgMapper fc3dLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Fc3DTaskService fc3DTaskService;

    @Override
    public void addFc3DPrevSg() {
        try {
            Fc3dLotterySgExample example = null;
            Fc3dLotterySgExample.Criteria criteria = null;
            Fc3dLotterySg fc3dLotterySg = null;
            // 创建一年的预期数据
            // 福彩3D开奖时间为每日开奖
            // 3D全国统一开奖，开奖节目通过中央人民广播电台一套在每晚21:30——21:35向全国直播。
            // 因为是人工开奖，所以开奖时间不确定，
            // 开奖时间设置为21 15开始请求三方接口获取开奖数据
            // 请求时间间隔设置成1一分钟一次

            // 以当前时间为基数创建当年所有的期数预期数据
            Date nowDate = new Date(System.currentTimeMillis());
            Date firstDayCurYear = DateUtils.getFirstDayCurYear(nowDate);
            String firstDayCurYearStr = DateUtils.getTimeString(firstDayCurYear, DateUtils.datePattern);
            // 加一年
            Date nextFirstDayCurYear = DateUtils.addOneYears(firstDayCurYear);
            String nextFirstDayCurYearStr = DateUtils.getTimeString(nextFirstDayCurYear, DateUtils.datePattern);

            // 获取当年数据
            example = new Fc3dLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(firstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            fc3dLotterySg = fc3dLotterySgMapper.selectOneByExample(example);
            // 如果没有查询到当年的数据就创建当年的预期数据
            if (null == fc3dLotterySg) {
                setOneYearsData(firstDayCurYear);
            }

            // 获取下一年数据
            example = new Fc3dLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(nextFirstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            fc3dLotterySg = fc3dLotterySgMapper.selectOneByExample(example);
            // 如果没有查询到下一年的数据就创建下一年的预期数据
            if (null == fc3dLotterySg) {
                setOneYearsData(nextFirstDayCurYear);
            }
        } catch (Exception e) {
            logger.error("福彩3d预期数据出错：{}", e);
        }
    }

    private void setOneYearsData(Date opearDate) {
        try {
            Fc3dLotterySg fc3dLotterySg;
            // 获取当年的天数总数
            int currntDays = DateUtils.getYearsDays(opearDate);

            // 记录期数
            int index = 1;
            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i < currntDays + 1; i++) {
                fc3dLotterySg = new Fc3dLotterySg();
                fc3dLotterySg.setOpenStatus("WAIT");
                Date currentDate = DateUtils.addDate("dd", i - 1, opearDate);

                // 设置时间
                String date = DateUtils.getTimeString(currentDate, DateUtils.datePattern);
                //获取年份
                String year = date.split("-")[0];
                //获取月份
                String month = date.split("-")[1];
                //获取日期
                String day = date.split("-")[2];

                //日期转换
                int yearN = Integer.parseInt(year);
                int monthN = Integer.parseInt(month);
                int dayN = Integer.parseInt(day);

                //判断是否是农历新年假期
                Lauar lauar = new Lauar(yearN, monthN, dayN);
                //农历日期（正月初二，正月初三，正月初四，正月初五，正月初六）
                String lunarDay = lauar.getLunarDay();
                //农历假期（除夕，春节）
                String lunarHoliday = lauar.getLunarHoliday();
                //是否新年假期
                if (Lauar.SpringHoliday.contains(lunarDay)) {
                    continue;
                }
                //是否新年假期
                if (Lauar.SpringHoliday.contains(lunarHoliday)) {
                    continue;
                }

                fc3dLotterySg.setDate(date);
                // 设置期数
                String numIssue = String.valueOf(index);
                String issue = "";
                switch (numIssue.length()) {
                    case 1:
                        issue = "00" + numIssue;
                        break;
                    case 2:
                        issue = "0" + numIssue;
                        break;
                    case 3:
                        issue = numIssue;
                        break;
                }
                issue = year + issue;
                fc3dLotterySg.setIssue(issue);
                fc3dLotterySg.setIdealTime(date + " " + "21:15:00");
                fc3dLotterySgMapper.insertSelective(fc3dLotterySg);
                index++;

                jsonArray.add(fc3dLotterySg);
            }

            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FC_3D_YUQI_DATA, "Fc3dLotterySg:" + jsonString);
        } catch (Exception e) {
            logger.error("福彩3d设置一年的数据出错：{}", e);
        }
    }

    @Override
    public void addFc3DSg() {
        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("sd", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("fc3d", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("fc3d", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // 获取本地近15期开奖结果
        Fc3dLotterySgExample sgExample = new Fc3dLotterySgExample();
        Fc3dLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<Fc3dLotterySg> sgList = fc3dLotterySgMapper.selectByExample(sgExample);

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        for (Fc3dLotterySg sg : sgList) {
            String issue = sg.getIssue();
            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue);
            LotterySgModel bywModel = bywMap.get(issue);

//			//这一期赛果有值 的个数，
//			int thisSgDataSize = TaskUtil.getNotNulSgModel(cpkModel,kcwModel,bywModel);
//			// 当这期赛果数据 个数 小于 2，不执行
//			if (thisSgDataSize < 2) {
//				continue;
//			}

            // 获取【彩票控】当前实际开奖期号与结果
            if (cpkModel != null) {
                cpkNumber = cpkModel.getSg();
            }
            // 获取【开彩网】当前实际开奖期号与结果
            if (kcwModel != null) {
                kcwNumber = kcwModel.getSg();
            }
            // 获取【博易网】当前实际开奖期号与结果
            if (bywModel != null) {
                bywNumber = bywModel.getSg();
            }

            // 获取最终开奖结果
            number = TaskUtil.getTrueSg(cpkNumber, kcwNumber, bywNumber);
            if (org.apache.commons.lang3.StringUtils.isEmpty(number)) { //两个赛果相同才入库
                continue;
            }

            // 是否需要修改
            boolean isPush = false;

            if (StringUtils.isBlank(sg.getNumber())) {
                sg.setNumber(number);
                sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
                sg.setOpenStatus("AUTO");
                isPush = true;
            }

            // 判断是否需要更新彩票控赛果
            if (StringUtils.isBlank(sg.getCpkNumber()) && StringUtils.isNotBlank(cpkNumber)) {
                sg.setCpkNumber(cpkNumber);
            }

            // 判断是否需要更新开彩网赛果
            if (StringUtils.isBlank(sg.getKcwNumber()) && StringUtils.isNotBlank(kcwNumber)) {
                sg.setKcwNumber(kcwNumber);
            }

            // 判断是否需要更新博易网赛果
            if (StringUtils.isBlank(sg.getBywNumber()) && StringUtils.isNotBlank(bywNumber)) {
                sg.setBywNumber(bywNumber);
            }

            int count = 0;
            String jsonFc3dLotterySg = null;
            if (isPush) {
                count = fc3dLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonFc3dLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkFc3dYuqiData();

                logger.info("【福彩3D】消息队列：{}", issue);
                // 将赛果推送到重庆时时彩相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_FC_3D, "FC3D:" + issue + ":" + number);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FC_3D, "FC3D:" + issue + ":" + number + ":" + jsonFc3dLotterySg);

                // 将赛果推送到WenSocket相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_RESULT_PUSH, "FC3D:" + issue + ":" + number);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_WEB_RESULT_PUSH,"FC3D:" + issue + ":" + number);

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();

                object.put("issue", issue);
                object.put("number", number);

                sgExample = new Fc3dLotterySgExample();
                criteria = sgExample.createCriteria();
                criteria.andIdealTimeGreaterThan(sg.getIdealTime());
                sgExample.setOrderByClause("`ideal_time` asc");
                Fc3dLotterySg nextLotterySg = fc3dLotterySgMapper.selectOneByExample(sgExample);
                if (nextLotterySg != null) {
                    object.put("nextTime", DateUtils.parseDate(nextLotterySg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
                    object.put("nextIssue", nextLotterySg.getIssue());
                } else {
                    object.put("nextTime", null);
                    object.put("nextIssue", null);
                }

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                int openCount = Integer.valueOf(sg.getIssue().substring(4, 7));
                int noOpenCount = CaipiaoSumCountEnum.FC3D.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.FC3D.getTagType(), object);
                String jsonString = ResultInfo.ok(lottery).toJSONString();
                //每次只发送最新一条
                if (i == 0) {
                    logger.info("TOPIC_APP_FC_3D发送消息：{}", jsonString);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_FC_3D, jsonString);
                    i++;
                }
            }
        }
    }

    //检查预期数据
    public void checkFc3dYuqiData() {
        //检查预期数据任务开始
        Fc3dLotterySgExample sgExample = new Fc3dLotterySgExample();
        Fc3dLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOrderByClause("`ideal_time` asc");
        int afterCount = fc3dLotterySgMapper.countByExample(sgExample);
        if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
            fc3DTaskService.addFc3DPrevSg();
        }
        //检查预期数据任务结束
    }

    @Override
    public void addFc3DRecommend() {

    }

    @Override
    public void addFc3DGssh() {

    }

}
