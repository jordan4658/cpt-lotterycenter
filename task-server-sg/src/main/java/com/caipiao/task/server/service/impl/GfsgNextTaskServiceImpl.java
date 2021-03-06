package com.caipiao.task.server.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.GfSgNextTaskService;
import com.caipiao.task.server.util.BasicRedisClient;
import com.caipiao.task.server.util.CommonService;
import com.mapper.*;
import com.mapper.domain.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.jms.*;
import javax.jms.IllegalStateException;
import java.util.*;

@Service
public class GfsgNextTaskServiceImpl implements GfSgNextTaskService, InitializingBean{

	private static final Logger logger = LoggerFactory.getLogger(GfsgNextTaskServiceImpl.class);
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BasicRedisClient basicRedisClient;




    @Override
    public void gfSgCqsscNextMessage() {
        try{
            CqsscLotterySgExample cqsscLotterySgExample = new CqsscLotterySgExample();
            CqsscLotterySgExample.Criteria criteria = cqsscLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<CqsscLotterySg> sgList = cqsscLotterySgMapper.selectByExample(cqsscLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(CqsscLotterySg sg:sgList){
                    String key = "cq"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    CqsscLotterySg lastCqsscLotterySg = commonService.cqsscQueryLastSgByIssue(issue,true);
                    CqsscLotterySg nextSg = commonService.cqsscQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastCqsscLotterySg.getCpkNumber()) ? lastCqsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastCqsscLotterySg.getKcwNumber()) ? lastCqsscLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    int openCount = Integer.valueOf(issue.substring(issue.length()-2,issue.length()));
                    int noOpenCount = CaipiaoSumCountEnum.CQSSC.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.CQSSC.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_SSC_CQ+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("?????????????????????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 6*3600l); //????????????6????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("?????????????????????????????????????????????",e);
        }
    }

    @Override
    public void gfSgXjsscNextMessage() {
        try{
            XjsscLotterySgExample xjsscLotterySgExample = new XjsscLotterySgExample();
            XjsscLotterySgExample.Criteria criteria = xjsscLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<XjsscLotterySg> sgList = xjsscLotterySgMapper.selectByExample(xjsscLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(XjsscLotterySg sg:sgList){
                    String key = "xj"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    XjsscLotterySg lastXjsscLotterySg = commonService.xjsscQueryLastSgByIssue(issue,true);
                    XjsscLotterySg nextSg = commonService.xjsscQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastXjsscLotterySg.getCpkNumber()) ? lastXjsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastXjsscLotterySg.getKcwNumber()) ? lastXjsscLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    int openCount = Integer.valueOf(issue.substring(issue.length()-2,issue.length()));
                    int noOpenCount = CaipiaoSumCountEnum.XJSSC.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.XJSSC.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_SSC_XJ+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("?????????????????????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 10*3600l); //????????????10????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("?????????????????????????????????????????????",e);
        }
    }

    @Override
    public void gfSgTjsscNextMessage() {
        try{
            TjsscLotterySgExample tjsscLotterySgExample = new TjsscLotterySgExample();
            TjsscLotterySgExample.Criteria criteria = tjsscLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<TjsscLotterySg> sgList = tjsscLotterySgMapper.selectByExample(tjsscLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(TjsscLotterySg sg:sgList){
                    String key = "tj"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    TjsscLotterySg lastTjsscLotterySg = commonService.tjsscQueryLastSgByIssue(issue,true);
                    TjsscLotterySg nextSg = commonService.tjsscQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastTjsscLotterySg.getCpkNumber()) ? lastTjsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastTjsscLotterySg.getKcwNumber()) ? lastTjsscLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    int openCount = Integer.valueOf(issue.substring(issue.length()-2,issue.length()));
                    int noOpenCount = CaipiaoSumCountEnum.TJSSC.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.TJSSC.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_SSC_TJ+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("?????????????????????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 12*3600l); //????????????12????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("?????????????????????????????????????????????",e);
        }
    }

    @Override
    public void gfSgBjpk10NextMessage() {
        try{
            BjpksLotterySgExample bjpksLotterySgExample = new BjpksLotterySgExample();
            BjpksLotterySgExample.Criteria criteria = bjpksLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<BjpksLotterySg> sgList = bjpksLotterySgMapper.selectByExample(bjpksLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(BjpksLotterySg sg:sgList){
                    String key = "bjpks"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    BjpksLotterySg lastBjpksLotterySg = commonService.bjpksQueryLastSgByIssue(issue,true);
                    BjpksLotterySg nextSg = commonService.bjpksQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastBjpksLotterySg.getCpkNumber()) ? lastBjpksLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastBjpksLotterySg.getKcwNumber()) ? lastBjpksLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());


                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
                    startCal.set(Calendar.HOUR_OF_DAY,9);
                    startCal.set(Calendar.MINUTE,30);
                    startCal.set(Calendar.SECOND,0);

                    long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
                    int openCount = (int)(jiange/1200) + 1;
                    int noOpenCount = CaipiaoSumCountEnum.BJPKS.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.BJPKS.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_BJPKS+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("??????pk10??????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 12*3600l); //????????????12????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("??????pk10??????????????????????????????",e);
        }
    }

    @Override
    public void gfSgXyftNextMessage() {
        try{
            XyftLotterySgExample xyftLotterySgExample = new XyftLotterySgExample();
            XyftLotterySgExample.Criteria criteria = xyftLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<XyftLotterySg> sgList = xyftLotterySgMapper.selectByExample(xyftLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(XyftLotterySg sg:sgList){
                    String key = "xyft"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    XyftLotterySg lastXyftLotterySg = commonService.xyftQueryLastSgByIssue(issue,true);
                    XyftLotterySg nextSg = commonService.xyftQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastXyftLotterySg.getCpkNumber()) ? lastXyftLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastXyftLotterySg.getKcwNumber()) ? lastXyftLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    int openCount = Integer.valueOf(issue.substring(8));
                    int noOpenCount = CaipiaoSumCountEnum.XYFEIT.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.XYFEIT.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_XYFT+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("??????????????????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 12*3600l); //????????????12????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("??????????????????????????????????????????",e);
        }
    }

    @Override
    public void gfSgXyftFtNextMessage() {
        try{
            FtxyftLotterySgExample ftxyftLotterySgExample = new FtxyftLotterySgExample();
            FtxyftLotterySgExample.Criteria criteria = ftxyftLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            List<FtxyftLotterySg> sgList = ftxyftLotterySgMapper.selectByExample(ftxyftLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(FtxyftLotterySg sg:sgList){
                    String key = "ftxyft"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    XyftLotterySg lastXyftLotterySg = commonService.xyftQueryLastSgByIssue(issue,true);
                    FtxyftLotterySg nextSg = commonService.ftxyftQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastXyftLotterySg.getCpkNumber()) ? lastXyftLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastXyftLotterySg.getKcwNumber()) ? lastXyftLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    int openCount = Integer.valueOf(issue.substring(8));
                    int noOpenCount = CaipiaoSumCountEnum.XYFEIT.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.XYFTFT.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_XYFT_FT+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("????????????????????????????????????:{},{}",delayTime,jsonString);
                    basicRedisClient.set(key, "1", 12*3600l); //????????????12????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("????????????????????????????????????????????????",e);
        }
    }

    @Override
    public void gfSgPcddNextMessage() {
//        try{
//            TextMessage message = session.createTextMessage("123456");
//
//            //??????????????????????????????????????????????????????
////        long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
//            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 20*1000);
//            Topic destination = session.createTopic("123456");
//            MessageProducer producer = session.createProducer(destination);
//            producer.send(message);
//        }catch (Exception e){
//            logger.info("1234");
//        }
//        System.out.println(12345);



        try{
            PceggLotterySgExample pceggLotterySgExample = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria criteria = pceggLotterySgExample.createCriteria();
            Calendar nextCalendar = Calendar.getInstance();
//            nextCalendar.add(Calendar.MINUTE,2);

            Calendar nextNextCalendar = Calendar.getInstance();
            nextNextCalendar.add(Calendar.HOUR_OF_DAY,1);

            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(nextCalendar.getTime(),DateUtils.fullDatePattern));
            criteria.andIdealTimeLessThan(DateUtils.formatDate(nextNextCalendar.getTime(),DateUtils.fullDatePattern));
            pceggLotterySgExample.setOrderByClause("ideal_time asc");
            List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(pceggLotterySgExample);
            if(sgList.size() > 0){
                int i = 0;
                for(PceggLotterySg sg:sgList){
                    String key = "pcegg"+ sg.getIssue();
                    if (basicRedisClient.get(key) != null) {
                        continue;
                    }
                    if(i == 0){
                        i++;
                        //???????????????????????????
                        continue;
                    }
                    String issue = sg.getIssue();
                    JSONObject object = new JSONObject();
                    JSONObject lottery = new JSONObject();
//                    object.put("issue",issue);
                    //????????????????????????????????????????????????????????????
//                    PceggLotterySg lastPceggLotterySg = commonService.pceggQueryLastSgByIssue(issue,true);
                    PceggLotterySg nextSg = commonService.pceggQueryNextSgByIssue(sg.getIssue());
                    object.put("issue", nextSg.getIssue());
//                    String number = StringUtils.isNotBlank(lastPceggLotterySg.getCpkNumber()) ? lastPceggLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastPceggLotterySg.getKcwNumber()) ? lastPceggLotterySg.getKcwNumber() : "";
//                    object.put("number",number);

                    object.put("nextTime",   DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                    object.put("nextIssue",nextSg.getIssue());

                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
                    startCal.set(Calendar.HOUR_OF_DAY,9);
                    startCal.set(Calendar.MINUTE,5);
                    startCal.set(Calendar.SECOND,0);

                    long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
                    int openCount = (int)(jiange/300) + 1;
                    int noOpenCount = CaipiaoSumCountEnum.PCDAND.getSumCount() - openCount;

                    object.put("openCount",openCount);
                    object.put("noOpenCount",noOpenCount);
                    JSONObject objectAll = new JSONObject();
                    lottery.put(CaipiaoTypeEnum.PCDAND.getTagType(),object);

                    objectAll.put("data",lottery);
                    objectAll.put("status",1);
                    objectAll.put("time",new Date().getTime()/1000);
                    objectAll.put("info","??????");
                    String jsonString = objectAll.toJSONString();

                    TextMessage message = null;
                    try{
                        message = session.createTextMessage(jsonString);
                    }catch (Exception e){
                        //2. ??????Connection
                        queueConnection = poolFactory.createQueueConnection();
                        //3. ????????????
                        queueConnection.start();
                        //4. ????????????
                        logger.info("????????????mq");
                        session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        message = session.createTextMessage(jsonString);
                    }

                    //??????????????????????????????????????????????????????
                    long delayTime = DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern).getTime()-new Date().getTime();
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    Destination destination = session.createQueue(ActiveMQConfig.TOPIC_APP_PCEGG+"byself");
                    MessageProducer producer = session.createProducer(destination);
                    producer.send(message);
                    logger.info("pce????????????????????????:{},{},{},{}",delayTime,sg.getIssue(),sg.getIdealTime(),jsonString);
                    basicRedisClient.set(key, "1", 12*3600l); //????????????12????????????????????????????????????????????????
                    i ++;
                }
            }
        }catch (Exception e){
            logger.error("pce????????????????????????????????????",e);
        }
    }



    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;


    public static String USER;
    public static String PASSWORD;
    public static String BROKERURL;

    public static QueueSession session;
    public static  QueueConnection queueConnection;
    public static PooledConnectionFactory poolFactory;
    public static ActiveMQConnectionFactory factory;

    @Override
    public void afterPropertiesSet() {
        USER = this.user;
        PASSWORD = this.password;
        BROKERURL = this.brokerUrl;
        createConnect();
    }

    public void createConnect() {
        //1. ??????ConnectionFactory
        //ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username,password,url);
        factory = new ActiveMQConnectionFactory(USER, PASSWORD, BROKERURL);
        poolFactory = new PooledConnectionFactory(factory);


        // ??????SSL??????????????????
//        ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory();
//        // ????????????????????????SSL?????????????????????
//        sslConnectionFactory.setBrokerURL(BROKERURL);
//        sslConnectionFactory.setUserName(USER);
//        sslConnectionFactory.setPassword(PASSWORD);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKERURL);
        connectionFactory.setUserName(USER);
        connectionFactory.setPassword(PASSWORD);

//        QueueConnection queueConnection = null;
//        QueueSession queueSession = null;
        MessageConsumer consumer = null;
//        ConnectionConsumer connectionConsumer = null;
        try {
            //2. ??????Connection
            queueConnection = poolFactory.createQueueConnection();
//            connection = poolFactory.createQueueConnection();

            //3. ????????????
            queueConnection.start();
            //4. ????????????
//            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        } catch (Exception e) {
            logger.error("?????????????????????" , e);
        } finally {


        }
    }
}
