package com.caipiao.task.server.service.impl;

import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 幸运飞艇 - 定时任务实现类
 */
@Service
public class TaskUtil {
    private static final Logger logger = LoggerFactory.getLogger(TaskUtil.class);

    //赛果对比，3个数据源，任何2个相同，则对比成功，否则失败
    public static String getTrueSg(String cpkNumber, String kcwNumber, String bywNumber) {
//        String sg = null;
//        if((StringUtils.isNotEmpty(cpkNumber) && StringUtils.isNotEmpty(kcwNumber) && cpkNumber.equals(kcwNumber)) ||
//                (StringUtils.isNotEmpty(cpkNumber) && StringUtils.isNotEmpty(bywNumber) && cpkNumber.equals(bywNumber))){
//            sg = cpkNumber;
//        }else if((StringUtils.isNotEmpty(kcwNumber) && StringUtils.isNotEmpty(bywNumber) && kcwNumber.equals(bywNumber))){
//            sg = kcwNumber;
//        }
//        return sg;

        String sg = null;
        if (StringUtils.isNotEmpty(cpkNumber)) {
            sg = cpkNumber;
        } else if (StringUtils.isNotEmpty(kcwNumber)) {
            sg = kcwNumber;
        } else if (StringUtils.isNotEmpty(bywNumber)) {
            sg = bywNumber;
        }
        return sg;
    }

    //判断赛果实体不为空的个数
    public static int getNotNulSgModel(LotterySgModel model1, LotterySgModel model2, LotterySgModel model3) {
        int thisSgDataSize = 0;
        if (model1 != null) {
            thisSgDataSize++;
        }
        if (model2 != null) {
            thisSgDataSize++;
        }
        if (model3 != null) {
            thisSgDataSize++;
        }
        return thisSgDataSize;
    }

    //记录抓取到数据源的个数，
    public static int getCrawlResults(List<LotterySgModel> cpkResults, List<LotterySgModel> kcwResults, List<LotterySgModel> bywResults) {
        int hasDataSize = 0;
        if (!CollectionUtils.isEmpty(cpkResults)) {
            hasDataSize++;
        }
        if (!CollectionUtils.isEmpty(kcwResults)) {
            hasDataSize++;
        }
        if (!CollectionUtils.isEmpty(bywResults)) {
            hasDataSize++;
        }
        return hasDataSize;
    }

}
