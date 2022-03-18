package com.caipiao.task.server.callable;

import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.task.server.util.GetHttpsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class ThreadTaskKcw implements Callable<List<LotterySgModel>> {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTaskKcw.class);
    
    private String code;
    private Integer num;

    public ThreadTaskKcw(String code, Integer num) {
        this.code = code;
        this.num = num;
    }

    @Override
    public List<LotterySgModel> call() throws Exception {
        // 调用【开彩网】接口
        List<LotterySgModel> kcwResults = null;
        try {
            kcwResults = GetHttpsgUtil.getKcwSg(code, num);
        } catch (Exception e) {
            logger.error("【开彩网】获取赛果失败:{}", code, e);
        }
        return kcwResults;
    }
}