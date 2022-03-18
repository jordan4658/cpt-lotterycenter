package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.*;
import com.caipiao.task.server.callable.ThreadTaskByw;
import com.caipiao.task.server.callable.ThreadTaskCpk;
import com.caipiao.task.server.callable.ThreadTaskKcw;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.XyftTaskService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.*;
import com.mapper.domain.*;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 幸运飞艇 - 定时任务实现类
 */
@Service
public class XyftTaskServiceImpl extends CommonServiceImpl implements XyftTaskService {
    private static final Logger logger = LoggerFactory.getLogger(XyftTaskServiceImpl.class);

    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private XyftCountSgdxMapper xyftCountSgdxMapper;
    @Autowired
    private XyftCountSgdsMapper xyftCountSgdsMapper;
    @Autowired
    private XyftCountSglhMapper xyftCountSglhMapper;
    @Autowired
    private XyftRecommendMapper xyftRecommendMapper;
    @Autowired
    private XyftKillNumberMapper xyftKillNumberMapper;
    @Autowired
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private XyftTaskService xyftTaskService;
    @Autowired
    private CommonService commonService;

    @Override
    @Transactional
    public void addXyftPrevSg() {
        // 一天总期数
        int count = CaipiaoSumCountEnum.XYFEIT.getSumCount();
        // 获取当前赛果最后一期数据
        XyftLotterySgExample sgExample = new XyftLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        XyftLotterySg lastSg = xyftLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        String idealTime = lastSg.getIdealTime();
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
        String issue = lastSg.getIssue();

        JSONArray jsonArray = new JSONArray();
        JSONArray ftJsonArray = new JSONArray();
        XyftLotterySg sg;
        String time = DateUtils.formatDate(dateTime, "HH:mm:ss");
        if (!"04:04:00".equals(time)) {
            while (time.compareTo("13:09:00") >= 0 || time.compareTo("04:04:00") < 0) {
                sg = new XyftLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = this.nextIssueTime(dateTime);
                time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

                XyftLotterySg targetSg = new XyftLotterySg();
                BeanUtils.copyProperties(sg, targetSg);

                if (targetSg.getCpkNumber() == null) {
                    targetSg.setCpkNumber("");
                }
                if (targetSg.getKcwNumber() == null) {
                    targetSg.setKcwNumber("");
                }
                jsonArray.add(targetSg);
                xyftLotterySgMapper.insertSelective(sg);

                // 幸运飞艇番摊
                FtxyftLotterySg ftXyftSg = new FtxyftLotterySg();
                BeanUtils.copyProperties(sg, ftXyftSg);

                FtxyftLotterySg targetftSg = new FtxyftLotterySg();
                BeanUtils.copyProperties(ftXyftSg, targetftSg);

                if (targetSg.getCpkNumber() == null) {
                    targetSg.setCpkNumber("");
                }
                if (targetSg.getKcwNumber() == null) {
                    targetSg.setKcwNumber("");
                }
                ftJsonArray.add(targetftSg);
                ftxyftLotterySgMapper.insertSelective(ftXyftSg);

            }
        }

        String date = idealTime.substring(0, 10);
        if (date.compareTo(DateUtils.formatDate(DateUtils.getDayAfter(new Date(), 1L), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_YUQI_DATA, "XyftLotterySg:" + jsonString);
                String ftJsonString = JSONObject.toJSONString(ftJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FT_XYFT_YUQI_DATA, "FtxyftLotterySg:" + ftJsonString);
            }

            return;
        }

        for (int i = 0; i < count; i++) {
            if (i == 0) {
                dateTime = DateUtils.parseDate(DateUtils.formatDate(dateTime, DateUtils.datePattern) + " 13:04:00", DateUtils.fullDatePattern);
            }
            sg = new XyftLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

            XyftLotterySg targetSg = new XyftLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);
            xyftLotterySgMapper.insertSelective(sg);

            // 幸运飞艇番摊
            FtxyftLotterySg ftXyftSg = new FtxyftLotterySg();
            BeanUtils.copyProperties(sg, ftXyftSg);

            FtxyftLotterySg targetftSg = new FtxyftLotterySg();
            BeanUtils.copyProperties(ftXyftSg, targetftSg);
            ftJsonArray.add(targetftSg);
            ftxyftLotterySgMapper.insertSelective(ftXyftSg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_YUQI_DATA, "XyftLotterySg:" + jsonString);
        String ftJsonString = JSONObject.toJSONString(ftJsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FT_XYFT_YUQI_DATA, "FtxyftLotterySg:" + ftJsonString);
    }


    @Override
    public void addXyftSg() {
        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("xyft", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("mlaft", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("xyft", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // 获取本地近15期开奖结果
        XyftLotterySgExample sgExample = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<XyftLotterySg> sgList = xyftLotterySgMapper.selectByExample(sgExample);

        int i = 0;
        for (XyftLotterySg sg : sgList) {
            String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
            i++;
            String issue = sg.getIssue();
            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue);
            LotterySgModel bywModel = bywMap.get(issue);

//            //这一期赛果有值 的个数，
//            int thisSgDataSize = TaskUtil.getNotNulSgModel(cpkModel,kcwModel,bywModel);
//            // 当这期赛果数据 个数 小于 2，不执行
//            if (thisSgDataSize < 2) {
//                continue;
//            }

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
            if (StringUtils.isEmpty(number)) { //两个赛果相同才入库
                continue;
            }

            // 是否需要修改
            boolean isPush = false;

            // 判断是否需要修改赛果
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
            String jsonxyftLotterySg = null;
            String jsonftxyftLotterySg = null;
            if (isPush) {
                count = xyftLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonxyftLotterySg = JSON.toJSONString(sg).replace(":", "$");

                FtxyftLotterySg ftXyftSg = new FtxyftLotterySg();
                BeanUtils.copyProperties(sg, ftXyftSg);
                // 计算番摊值
                String modValue = FanTanCalculationUtils.ftjspksSaleResult(sg.getNumber());
                ftXyftSg.setFtNumber(modValue);

                ftXyftSg.setId(this.querypkssg(sg.getIssue()).getId());
                ftxyftLotterySgMapper.updateByPrimaryKeySelective(ftXyftSg);
                jsonftxyftLotterySg = JSON.toJSONString(ftXyftSg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkXyftYuqiData();

                logger.info("【幸运飞艇】消息：{},{}", issue, number);
                // 将赛果推送到幸运飞艇相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_XYFT, "XYFT:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_LM, "XYFT:" + issue + ":" + number + ":" + jsonxyftLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_CMC_CQJ, "XYFT:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_DS_CQJ, "XYFT:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_DWD, "XYFT:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_GYH, "XYFT:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_UPDATE_DATA, "XYFT:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_FT, "XYFT:" + issue + ":" + number + ":" + jsonftxyftLotterySg);
                } catch (Exception e) {
                    logger.error("幸运飞艇发送消息失败：{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", number);

                object.put("nextTime", DateUtils.parseDate(commonService.xyftQueryNextSg().getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
                object.put("nextIssue", commonService.xyftQueryNextSg().getIssue());

                int openCount = Integer.valueOf(issue.substring(8));
                int noOpenCount = CaipiaoSumCountEnum.XYFEIT.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.XYFEIT.getTagType(), object);
                String jsonString = ResultInfo.ok(lottery).toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_XYFT发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_XYFT, jsonString);

                        lottery.put(CaipiaoTypeEnum.XYFTFT.getTagType(), object);
                        lottery.put(CaipiaoTypeEnum.XYFEIT.getTagType(), null);
                        String sscJsonString = ResultInfo.ok(lottery).toJSONString();
                        logger.info("TOPIC_APP_XYFT_FT发送消息：{}", sscJsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_XYFT_FT, sscJsonString);
                    }
                } catch (Exception e) {
                    logger.error("幸运飞艇发送消息失败：{}", e);
                }
            }
        }
    }


    //检查预期数据
    public void checkXyftYuqiData() {
        try {
            //检查预期数据任务开始
            XyftLotterySgExample sgExample = new XyftLotterySgExample();
            XyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = xyftLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                xyftTaskService.addXyftPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("幸运飞艇发送消息失败：{}", e);
        }
    }

    private FtxyftLotterySg querypkssg(String issue) {
        FtxyftLotterySg ftXyftSg = new FtxyftLotterySg();
        FtxyftLotterySgExample ftxySgExample = new FtxyftLotterySgExample();
        FtxyftLotterySgExample.Criteria criteria1 = ftxySgExample.createCriteria();
        criteria1.andIssueEqualTo(issue);
        ftXyftSg = this.ftxyftLotterySgMapper.selectOneByExample(ftxySgExample);
        return ftXyftSg;
    }


    @Override
    @Transactional
    public void addXyftSgCount() {
        long yesterday = System.currentTimeMillis() - 86400 * 1000;
        String date = TimeHelper.date("yyyy-MM-dd", yesterday);

        // 获取昨天的赛果记录
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andTimeLike(date + "%");
        List<XyftLotterySg> xyftLotterySgs = this.xyftLotterySgMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(xyftLotterySgs)) {
            // 对赛果进行统计
            XyftCountSgdx countSgdx = XyftUtils.countSgDx(xyftLotterySgs);
            XyftCountSgds xyftCountSgds = XyftUtils.countSgDs(xyftLotterySgs);
            XyftCountSglh xyftCountSglh = XyftUtils.countSgLh(xyftLotterySgs);

            //根据日期判断是否已录入数据库
            //大小统计
            XyftCountSgdxExample example1 = new XyftCountSgdxExample();
            XyftCountSgdxExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andDateEqualTo(date);
            XyftCountSgdx countSgdx1 = this.xyftCountSgdxMapper.selectOneByExample(example1);
            if (countSgdx1 == null) {
                //没有就录入
                this.xyftCountSgdxMapper.insertSelective(countSgdx);
            } else {
                //已经录入就更新
                this.xyftCountSgdxMapper.updateByExample(countSgdx, example1);
            }
            String jsonObject = JSON.toJSONString(countSgdx);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_DX_TJ_DATA, "XyftCountSgdx:" + jsonObject);

            //单双统计
            XyftCountSgdsExample example2 = new XyftCountSgdsExample();
            XyftCountSgdsExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andDateEqualTo(date);
            XyftCountSgds countSgds2 = this.xyftCountSgdsMapper.selectOneByExample(example2);
            if (countSgds2 == null) {
                //没有就录入
                this.xyftCountSgdsMapper.insertSelective(xyftCountSgds);
            } else {
                //已经录入就更新
                this.xyftCountSgdsMapper.updateByExample(xyftCountSgds, example2);
            }
            jsonObject = JSON.toJSONString(xyftCountSgds);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_DS_TJ_DATA, "XyftCountSglh:" + jsonObject);

            //龙虎统计
            XyftCountSglhExample example3 = new XyftCountSglhExample();
            XyftCountSglhExample.Criteria criteria3 = example3.createCriteria();
            criteria3.andDateEqualTo(date);
            XyftCountSglh countSglh3 = this.xyftCountSglhMapper.selectOneByExample(example3);
            if (countSglh3 == null) {
                //没有就录入
                this.xyftCountSglhMapper.insertSelective(xyftCountSglh);
            } else {
                //已经录入就更新
                this.xyftCountSglhMapper.updateByExample(xyftCountSglh, example3);
            }
            jsonObject = JSON.toJSONString(xyftCountSglh);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_LH_TJ_DATA, "XyftCountSgds:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addXyftRecommend() {
        // 获取当前最后一期【免费推荐】数据
        XyftRecommendExample recommendExample = new XyftRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        XyftRecommend lastRecommend = xyftRecommendMapper.selectOneByExample(recommendExample);

        // 获取下一期期号信息
        XyftLotterySg nextSg = commonService.xyftQueryNextSg();
        // 查询遗漏数据
        List<XyftLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 循环生成【免费推荐】数据
        XyftRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new XyftRecommend();
            // 设置期号
            recommend.setIssue(sgList.get(i).getIssue());

            // 生成冠军号码推荐 5个
            StringBuilder builder1 = new StringBuilder();
            String numberStr1 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder1.append(numberStr1);
            // 根据号码生成大小|单双
            String[] str1 = numberStr1.split(",");
            builder1.append("|");
            builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder1.append("|");
            builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setFirst(builder1.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder2 = new StringBuilder();
            String numberStr2 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder2.append(numberStr2);
            // 根据号码生成大小|单双
            String[] str2 = numberStr2.split(",");
            builder2.append("|");
            builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder2.append("|");
            builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setSecond(builder2.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder3 = new StringBuilder();
            String numberStr3 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder3.append(numberStr3);
            // 根据号码生成大小|单双
            String[] str3 = numberStr3.split(",");
            builder3.append("|");
            builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder3.append("|");
            builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setThird(builder3.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder4 = new StringBuilder();
            String numberStr4 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder4.append(numberStr4);
            // 根据号码生成大小|单双
            String[] str4 = numberStr4.split(",");
            builder4.append("|");
            builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder4.append("|");
            builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setFourth(builder4.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder5 = new StringBuilder();
            String numberStr5 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder5.append(numberStr5);
            // 根据号码生成大小|单双
            String[] str5 = numberStr5.split(",");
            builder5.append("|");
            builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder5.append("|");
            builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setFifth(builder5.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder6 = new StringBuilder();
            String numberStr6 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder6.append(numberStr6);
            // 根据号码生成大小|单双
            String[] str6 = numberStr6.split(",");
            builder6.append("|");
            builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder6.append("|");
            builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setSixth(builder6.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder7 = new StringBuilder();
            String numberStr7 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder7.append(numberStr7);
            // 根据号码生成大小|单双
            String[] str7 = numberStr7.split(",");
            builder7.append("|");
            builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder7.append("|");
            builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setSeventh(builder7.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder8 = new StringBuilder();
            String numberStr8 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder8.append(numberStr8);
            // 根据号码生成大小|单双
            String[] str8 = numberStr8.split(",");
            builder8.append("|");
            builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder8.append("|");
            builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setEighth(builder8.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder9 = new StringBuilder();
            String numberStr9 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder9.append(numberStr9);
            // 根据号码生成大小|单双
            String[] str9 = numberStr9.split(",");
            builder9.append("|");
            builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder9.append("|");
            builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setNinth(builder9.toString());

            // 生成冠军号码推荐 5个
            StringBuilder builder10 = new StringBuilder();
            String numberStr10 = RandomUtil.getRandomStringNoSame(5, 1, 11);
            builder10.append(numberStr10);
            // 根据号码生成大小|单双
            String[] str10 = numberStr10.split(",");
            builder10.append("|");
            builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
            builder10.append("|");
            builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setTenth(builder10.toString());

            String numberStr11 = RandomUtil.getRandomStringNoSame(5, 3, 20);
            recommend.setFirstSecond(numberStr11);

            recommend.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            // 保存生成数据
            xyftRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_RECOMMEND_DATA, "XyftRecommend:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addXyftKillNumber() {
        // 查询当前最后一期【公式杀号】数据
        XyftKillNumberExample killNumberExample = new XyftKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        XyftKillNumber killNumber = xyftKillNumberMapper.selectOneByExample(killNumberExample);

        // 获取下一期期号信息
        XyftLotterySg nextSg = commonService.xyftQueryNextSg();

        // 查询遗漏数据
        List<XyftLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 生成杀号
        XyftKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            XyftLotterySg sg = sgList.get(i);
            // 生成下一期杀号信息
            nextKillNumber = XyftUtils.getKillNumber(sg.getIssue());
            xyftKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_KILL_DATA, "XyftKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //每一期开始时间发送一个消息
        //时间范围： 0 9/5 13-4 * * ?
        //新疆时时彩  采集时间 13:09-04:04 5分钟一期 延迟1-2分钟采集到数据
        // 在这个时间内就生效：   13:09-23:59    0:04-4:04
        Long time0 = null;  //代表现在时间
        Long time1 = null;  //代表13：09
        Long time2 = null;  //代表23：59，  多出30秒，以防延迟
        Long time3 = null;  //代表0：04
        Long time4 = null;  //代表4：04     多出30秒，以防延迟

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 9);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 30);
        time2 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 4);
        time3 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 4);
        cal.set(Calendar.SECOND, 30);
        time4 = cal.getTimeInMillis();

        boolean atTime = false;  //表示在这个时间段  0 9/5 13-4 * * ?
        if ((time0 >= time1 && time0 <= time2) || (time0 >= time3 && time0 <= time4)) {
            atTime = true;
        }
        if (!atTime) return;

        JSONObject object = new JSONObject();
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        XyftLotterySg nextSg = xyftLotterySgMapper.selectOneByExample(example);

        example = new XyftLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<XyftLotterySg> lastSgList = xyftLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime", DateUtils.parseDate(nextSg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
        object.put("nextIssue", nextSg.getIssue());

        XyftLotterySg lastSgTrue = null;
        for (XyftLotterySg lastSg : lastSgList) {
            String lastNumber = StringUtils.isNotBlank(lastSg.getCpkNumber()) ? lastSg.getCpkNumber() : StringUtils.isNotBlank(lastSg.getKcwNumber()) ? lastSg.getKcwNumber() : "";
            if (StringUtils.isNotBlank(lastNumber)) {
                object.put("issue", lastSg.getIssue());
                object.put("number", lastNumber);
                lastSgTrue = lastSg;
                break;
            }
            continue;
        }


        int openCount = Integer.valueOf(lastSgTrue.getIssue().substring(8));
        int noOpenCount = CaipiaoSumCountEnum.XYFEIT.getSumCount() - openCount;

        object.put("openCount", openCount);
        object.put("noOpenCount", noOpenCount);
        JSONObject objectAll = new JSONObject();
        lottery.put(CaipiaoTypeEnum.XYFEIT.getTagType(), object);

        objectAll.put("data", lottery);
        objectAll.put("status", 1);
        objectAll.put("time", new Date().getTime() / 1000);
        objectAll.put("info", "成功");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_XYFT, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_XYFT + "执行任务成功" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
    }

    /**
     * 获取下一期官方开奖时间
     *
     * @param dateTime 当前期官方开奖时间
     * @return
     */
    private Date nextIssueTime(Date dateTime) {
        return DateUtils.getMinuteAfter(dateTime, 5);
    }

    /**
     * 根据期号区间查询所有遗漏数据【近179期】
     *
     * @param startIssue 开始期号【不包括】
     * @param endIssue   结束期号【包括】
     * @return
     */
    private List<XyftLotterySg> queryOmittedData(String startIssue, String endIssue) {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(180);
        return xyftLotterySgMapper.selectByExample(example);
    }

    /**
     * 根据上期期号生成下期期号
     *
     * @param issue 上期期号
     * @return
     */
    private String createNextIssue(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("180".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }

}
