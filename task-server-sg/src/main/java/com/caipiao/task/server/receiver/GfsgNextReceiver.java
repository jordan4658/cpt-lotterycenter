package com.caipiao.task.server.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.util.CommonService;
import com.mapper.*;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class GfsgNextReceiver {
    private static final Logger logger = LoggerFactory.getLogger(GfsgNextReceiver.class);
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private CommonService commonService;

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_SSC_CQ+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppCqssc(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.CQSSC.getTagType());
        String issue = object.getString("issue");
        CqsscLotterySg lastCqsscLotterySg = commonService.cqsscQueryLastSgByIssue(issue,false);
        object.put("issue", lastCqsscLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastCqsscLotterySg.getCpkNumber()) ? lastCqsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastCqsscLotterySg.getKcwNumber()) ? lastCqsscLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.CQSSC.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_SSC_CQ发送消息：" + jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_CQ,jsonString);
    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_PCEGG+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppPcegg(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.PCDAND.getTagType());
        String issue = object.getString("issue");
        PceggLotterySg lastPceggLotterySg = commonService.pceggQueryLastSgByIssue(issue,false);
        object.put("issue", lastPceggLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastPceggLotterySg.getCpkNumber()) ? lastPceggLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastPceggLotterySg.getKcwNumber()) ? lastPceggLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.PCDAND.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_PCEGG发送消息：" + jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_PCEGG,jsonString);

    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_SSC_XJ+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppXjssc(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.XJSSC.getTagType());
        String issue = object.getString("issue");
        XjsscLotterySg lastXjsscLotterySg = commonService.xjsscQueryLastSgByIssue(issue,false);
        object.put("issue", lastXjsscLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastXjsscLotterySg.getCpkNumber()) ? lastXjsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastXjsscLotterySg.getKcwNumber()) ? lastXjsscLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.XJSSC.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_SSC_XJ发送消息：" + jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_XJ,jsonString);

    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_SSC_TJ+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppTjssc(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.TJSSC.getTagType());
        String issue = object.getString("issue");
        TjsscLotterySg lastTjsscLotterySg = commonService.tjsscQueryLastSgByIssue(issue,false);
        object.put("issue", lastTjsscLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastTjsscLotterySg.getCpkNumber()) ? lastTjsscLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastTjsscLotterySg.getKcwNumber()) ? lastTjsscLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.TJSSC.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_SSC_TJ发送消息：" + jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_TJ,jsonString);

    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_BJPKS+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppBjpks(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.BJPKS.getTagType());
        String issue = object.getString("issue");
        BjpksLotterySg lastBjpksLotterySg = commonService.bjpksQueryLastSgByIssue(issue,false);
        object.put("issue", lastBjpksLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastBjpksLotterySg.getCpkNumber()) ? lastBjpksLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastBjpksLotterySg.getKcwNumber()) ? lastBjpksLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.BJPKS.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_BJPKS发送消息：{}",jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_BJPKS,jsonString);

    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_XYFT+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppXyft(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.XYFEIT.getTagType());
        String issue = object.getString("issue");
        XyftLotterySg lastXyftLotterySg = commonService.xyftQueryLastSgByIssue(issue,false);
        object.put("issue", lastXyftLotterySg.getIssue());
        String number = StringUtils.isNotBlank(lastXyftLotterySg.getCpkNumber()) ? lastXyftLotterySg.getCpkNumber() : StringUtils.isNotBlank(lastXyftLotterySg.getKcwNumber()) ? lastXyftLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.XYFEIT.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_XYFT发送消息：{}",jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_XYFT,jsonString);

    }

    @JmsListener(destination= ActiveMQConfig.TOPIC_APP_XYFT_FT+"byself",containerFactory="jmsListenerContainerQueue")
    public void sendAppXyftFt(String message) {
        JSONObject objectAll = JSON.parseObject(message);
        JSONObject lottery = JSON.parseObject(objectAll.get("data").toString());

        JSONObject object = lottery.getJSONObject(CaipiaoTypeEnum.XYFTFT.getTagType());
        String issue = object.getString("issue");
        FtxyftLotterySg ftlastXyftLotterySg = commonService.ftxyftQueryLastSgByIssue(issue,false);
        object.put("issue", ftlastXyftLotterySg.getIssue());
        String number = StringUtils.isNotBlank(ftlastXyftLotterySg.getCpkNumber()) ? ftlastXyftLotterySg.getCpkNumber() : StringUtils.isNotBlank(ftlastXyftLotterySg.getKcwNumber()) ? ftlastXyftLotterySg.getKcwNumber() : "";
        object.put("number",number);

        lottery.put(CaipiaoTypeEnum.XYFTFT.getTagType(),object);
        objectAll.put("data",lottery);
        objectAll.put("time",new Date().getTime()/1000);
        String jsonString = objectAll.toJSONString();
        logger.info("TOPIC_APP_XYFT_FT发送消息：{}",jsonString);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_XYFT_FT,jsonString);

    }
}
