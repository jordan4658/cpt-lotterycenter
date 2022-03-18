package com.caipiao.task.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.TaskStatusEnum;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.pojo.DragonLongTip;
import com.caipiao.task.server.service.PushSeriesDragonToClientService;
import com.caipiao.task.server.service.TaskSgService;
import com.mapper.domain.TaskSg;

@Service
public class PushSeriesDragonToClientServiceImpl implements PushSeriesDragonToClientService {

    private static final Logger logger = LoggerFactory.getLogger(PushSeriesDragonToClientServiceImpl.class);

    @Autowired
    private TaskSgService taskSgService;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public void pushDragonAuact() {
        try {
            String caiPiaoType = CaipiaoTypeEnum.AUSACT.getTagCnName();
            // 查询出要执行的任务
            List<TaskSg> ausactTask = taskSgService.getInit(caiPiaoType, Constants.DEFAULT_TWO);

            if (CollectionUtils.isEmpty(ausactTask)) {
                logger.error("推送澳洲系列长龙没有长龙数据");
                return;
            }
            // 发送
            List<DragonLongTip> dragonList = new ArrayList<DragonLongTip>();
            for (TaskSg taskSg : ausactTask) {
                // 数据同步
                StringBuffer message = new StringBuffer();
                message.append("彩种：");
                message.append(caiPiaoType);
                message.append("玩法：");
                message.append(taskSg.getPlayType());
                message.append(",连开：");
                message.append(taskSg.getDragonName());
                message.append(taskSg.getDragonNum());
                message.append("期");
                // 更新
                taskSgService.updateStatus(taskSg.getId(), TaskStatusEnum.SUCCESS.getKeyValue());
                DragonLongTip tip = new DragonLongTip();
                tip.setDragonNum(taskSg.getDragonNum());
                tip.setDragonTip(message.toString());
                dragonList.add(tip);
            }
            // 发送
            if (!CollectionUtils.isEmpty(dragonList)) {
                String jsonObject = JSON.toJSONString(dragonList);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_DRAGONLONG_DATA, jsonObject);
            }
        } catch (Exception e) {
            logger.error("推送澳洲系列长龙异常：", e);
        }
    }

    @Override
    public void pushDragonPcegg() {
        try {
            String caiPiaoType = CaipiaoTypeEnum.PCDAND.getTagCnName();
            // 查询出要执行的任务
            List<TaskSg> ausactTask = taskSgService.getInit(caiPiaoType, Constants.DEFAULT_TWO);

            if (CollectionUtils.isEmpty(ausactTask)) {
                logger.error("推送PC蛋蛋系列长龙没有长龙数据");
                return;
            }
            // 发送
            List<DragonLongTip> dragonList = new ArrayList<DragonLongTip>();
            for (TaskSg taskSg : ausactTask) {
                // 数据同步
                StringBuffer message = new StringBuffer();
                message.append("彩种：");
                message.append(caiPiaoType);
                message.append("玩法：");
                message.append(taskSg.getPlayType());
                message.append(",连开：");
                message.append(taskSg.getDragonName());
                message.append(taskSg.getDragonNum());
                message.append("期");
                // 更新
                taskSgService.updateStatus(taskSg.getId(), TaskStatusEnum.SUCCESS.getKeyValue());
                DragonLongTip tip = new DragonLongTip();
                tip.setDragonNum(taskSg.getDragonNum());
                tip.setDragonTip(message.toString());
                dragonList.add(tip);
            }
            // 发送
            if (!CollectionUtils.isEmpty(dragonList)) {
                String jsonObject = JSON.toJSONString(dragonList);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_DRAGONLONG_DATA, jsonObject);
            }
        } catch (Exception e) {
            logger.error("推送PC蛋蛋系列长龙异常：", e);
        }
    }

    @Override
    public void pushDragonSscAll() {
        try {
            List<DragonLongTip> sscDragonList = new ArrayList<DragonLongTip>();
            // 德州时时彩
            String jssscType = CaipiaoTypeEnum.JSSSC.getTagCnName();
            List<DragonLongTip> jssscDragonList = this.pkTenDragon(jssscType);
            sscDragonList.addAll(jssscDragonList);
            // 5分时时彩
            String fivesscType = CaipiaoTypeEnum.FIVESSC.getTagCnName();
            List<DragonLongTip> fivesscDragonList = this.pkTenDragon(fivesscType);
            sscDragonList.addAll(fivesscDragonList);
            // 10分时时彩
            String tensscType = CaipiaoTypeEnum.TENSSC.getTagCnName();
            List<DragonLongTip> tensscDragonList = this.pkTenDragon(tensscType);
            sscDragonList.addAll(tensscDragonList);
            // 天津时时彩
            String tjsscType = CaipiaoTypeEnum.TJSSC.getTagCnName();
            List<DragonLongTip> tjsscDragonList = this.pkTenDragon(tjsscType);
            sscDragonList.addAll(tjsscDragonList);
            // 新疆时时彩
            String xjsscType = CaipiaoTypeEnum.XJSSC.getTagCnName();
            List<DragonLongTip> xjsscDragonList = this.pkTenDragon(xjsscType);
            sscDragonList.addAll(xjsscDragonList);
            // 重庆时时彩
            String cqsscType = CaipiaoTypeEnum.CQSSC.getTagCnName();
            List<DragonLongTip> cqsscDragonList = this.pkTenDragon(cqsscType);
            sscDragonList.addAll(cqsscDragonList);
            // 腾讯分分彩
            String txffcType = CaipiaoTypeEnum.TXFFC.getTagCnName();
            List<DragonLongTip> txffcDragonList = this.pkTenDragon(txffcType);
            sscDragonList.addAll(txffcDragonList);
            // 发送
            if (!CollectionUtils.isEmpty(sscDragonList)) {
                String jsonObject = JSON.toJSONString(sscDragonList);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_DRAGONLONG_DATA, jsonObject);
            }
        } catch (Exception e) {
            logger.error("推送时时彩系列长龙异常：", e);
        }
    }

    @Override
    public void pushDragonLhcAll() {
        try {
            List<DragonLongTip> lhcDragonList = new ArrayList<DragonLongTip>();
            // 澳门六合彩
            String ssLhcType = CaipiaoTypeEnum.AMLHC.getTagCnName();
            List<DragonLongTip> ssLhcDragonList = this.pkTenDragon(ssLhcType);
            lhcDragonList.addAll(ssLhcDragonList);
            // 5分六合彩
            String fiveLhcType = CaipiaoTypeEnum.FIVELHC.getTagCnName();
            List<DragonLongTip> fiveLhcDragonList = this.pkTenDragon(fiveLhcType);
            lhcDragonList.addAll(fiveLhcDragonList);
            // 德州六合彩
            String oneLhcType = CaipiaoTypeEnum.ONELHC.getTagCnName();
            List<DragonLongTip> oneLhcDragonList = this.pkTenDragon(oneLhcType);
            lhcDragonList.addAll(oneLhcDragonList);
            // 发送
            if (!CollectionUtils.isEmpty(lhcDragonList)) {
                String jsonObject = JSON.toJSONString(lhcDragonList);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_LHC_DRAGONLONG_DATA, jsonObject);
            }
        } catch (Exception e) {
            logger.error("推送六合彩系列长龙异常：", e);
        }
    }

    @Override
    public void pushDragonPkTenAll() {
        try {
            List<DragonLongTip> pkTenDragonList = new ArrayList<DragonLongTip>();
            // 德州PK10
            String jspksType = CaipiaoTypeEnum.JSPKS.getTagCnName();
            List<DragonLongTip> jspksdragonList = this.pkTenDragon(jspksType);
            pkTenDragonList.addAll(jspksdragonList);
            // 5分PK10
            String fivepksType = CaipiaoTypeEnum.FIVEPKS.getTagCnName();
            List<DragonLongTip> fivepksdragonList = this.pkTenDragon(fivepksType);
            pkTenDragonList.addAll(fivepksdragonList);
            // 10分PK10
            String tenpksType = CaipiaoTypeEnum.TENPKS.getTagCnName();
            List<DragonLongTip> tenpksdragonList = this.pkTenDragon(tenpksType);
            pkTenDragonList.addAll(tenpksdragonList);
            // 北京PK10
            String bjpksType = CaipiaoTypeEnum.BJPKS.getTagCnName();
            List<DragonLongTip> bjPkTendragonList = this.pkTenDragon(bjpksType);
            pkTenDragonList.addAll(bjPkTendragonList);
            // 幸运飞艇
            String xyftType = CaipiaoTypeEnum.XYFEIT.getTagCnName();
            List<DragonLongTip> XyftdragonList = this.pkTenDragon(xyftType);
            pkTenDragonList.addAll(XyftdragonList);
            // 发送
            if (!CollectionUtils.isEmpty(pkTenDragonList)) {
                String jsonObject = JSON.toJSONString(pkTenDragonList);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PKTEN_DRAGONLONG_DATA, jsonObject);
            }
        } catch (Exception e) {
            logger.error("推送PK10系列长龙异常：", e);
        }
    }


    /**
     * @return
     */
    private List<DragonLongTip> pkTenDragon(String caiPiaoType) {
        List<DragonLongTip> pkTenDragonList = new ArrayList<DragonLongTip>();
        try {
            // 查询出要执行的任务
            List<TaskSg> xyftTask = taskSgService.getInit(caiPiaoType, Constants.DEFAULT_TWENTY);

            if (CollectionUtils.isEmpty(xyftTask)) {
                logger.error("获取PK10系列长龙没有长龙数据");
                return pkTenDragonList;
            }
            pkTenDragonList = this.getDragonInfo(xyftTask, caiPiaoType);
        } catch (Exception e) {
            logger.error("获取PK10系列长龙异常：", e);
        }
        return pkTenDragonList;
    }

    /**
     * @param taskList
     * @param caiPiaoType
     * @return
     */
    private List<DragonLongTip> getDragonInfo(List<TaskSg> taskList, String caiPiaoType) {
        List<DragonLongTip> dragonList = new ArrayList<DragonLongTip>();
        try {
            for (TaskSg taskSg : taskList) {
                // 数据同步
                StringBuffer message = new StringBuffer();
                message.append("彩种：");
                message.append(caiPiaoType);
                message.append("玩法：");
                message.append(taskSg.getPlayType());
                message.append(",连开：");
                message.append(taskSg.getDragonName());
                message.append(taskSg.getDragonNum());
                message.append("期");
                // 更新
                taskSgService.updateStatus(taskSg.getId(), TaskStatusEnum.SUCCESS.getKeyValue());
                DragonLongTip tip = new DragonLongTip();
                tip.setDragonNum(taskSg.getDragonNum());
                tip.setDragonTip(message.toString());
                dragonList.add(tip);
            }
        } catch (Exception e) {
            logger.error("获取长龙信息异常：", e);
        }
        return dragonList;
    }


}
