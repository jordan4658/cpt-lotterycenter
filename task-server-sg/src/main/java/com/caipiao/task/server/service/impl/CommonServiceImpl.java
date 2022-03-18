package com.caipiao.task.server.service.impl;

import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.task.server.util.GetHttpsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 幸运飞艇 - 定时任务实现类
 */
@Service
public class CommonServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
    private static int coreSize = Runtime.getRuntime().availableProcessors() * 20;
    private static int maxSize = coreSize;
    private static int maxWorkQueueSize = 1024000;
    private static int keepAliveTime = 1;
    private static BlockingQueue workQueue = new LinkedBlockingQueue(maxWorkQueueSize);
    protected static ExecutorService threadPool = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, TimeUnit.MINUTES, workQueue);

    /**
     * 获取赛果
     *
     * @param cpk
     * @param kcw
     * @param byw
     * @return
     */
    public LotterySgResult obtainSgResult(Future<List<LotterySgModel>> cpk, Future<List<LotterySgModel>> kcw, Future<List<LotterySgModel>> byw) {
        LotterySgResult result = new LotterySgResult();
        try {
            result.setCpk(toObtainSgMapResult(cpk.get()));
        } catch (InterruptedException e) {
            logger.error("获取 cpk 赛果出错", e);
        } catch (ExecutionException e) {
            logger.error("获取 cpk 赛果出错", e);
        }

        try {
            result.setKcw(toObtainSgMapResult(kcw.get()));
        } catch (InterruptedException e) {
            logger.error("获取 kcw 赛果出错", e);
        } catch (ExecutionException e) {
            logger.error("获取 kcw 赛果出错", e);
        }

        try {
            result.setByw(toObtainSgMapResult(byw.get()));
        } catch (InterruptedException e) {
            logger.error("获取 byw 赛果出错", e);
        } catch (ExecutionException e) {
            logger.error("获取 byw 赛果出错", e);
        }

        return result.adjustResult();
    }

    private Map<String, LotterySgModel> toObtainSgMapResult(List<LotterySgModel> sgModels) {
        Map<String, LotterySgModel> mapResult = new HashMap<>();
        if (!CollectionUtils.isEmpty(sgModels)) {
            for (LotterySgModel model : sgModels) {
                mapResult.put(model.getIssue(), model);
            }
        }
        return mapResult;
    }

    /**
     * 获取实际的开奖结果
     *
     * @param result
     * @return
     */
    public String obtainSgNumber(String issue, LotterySgResult result) {
        if (null == result) {
            return null;
        }

        LotterySgModel cpkModel = result.getCpk().get(issue);
        LotterySgModel kcwModel = result.getKcw().get(issue);
        LotterySgModel bywModel = result.getByw().get(issue);
        String cpkNumber = null == cpkModel ? null : cpkModel.getSg();
        String kcwNumber = null == kcwModel ? null : kcwModel.getSg();
        String bywNumber = null == bywModel ? null : cpkModel.getSg();

        return TaskUtil.getTrueSg(cpkNumber, kcwNumber, bywNumber);
    }

    public List<LotterySgModel> getCrawlCpk(String code, Integer num) {
        // 调用【彩票控】接口
        List<LotterySgModel> cpkResults = null;
        try {
            cpkResults = GetHttpsgUtil.getCpkSg(code, num);
        } catch (Exception e) {
            logger.error("【彩票控】获取赛果失败:{}", code, e);
        }
        return cpkResults;
    }

    public List<LotterySgModel> getCrawlKcw(String code, Integer num) {
        // 调用【开彩网】接口
        List<LotterySgModel> kcwResults = null;
        try {
            kcwResults = GetHttpsgUtil.getKcwSg(code, num);
        } catch (Exception e) {
            logger.error("【开彩网】获取赛果失败:{}", code, e);
        }
        return kcwResults;
    }

    public List<LotterySgModel> getCrawlByw(String code, Integer num) {
        // 调用【博易网】接口
        List<LotterySgModel> bywResults = null;
        try {
            bywResults = GetHttpsgUtil.getBywSg(code, num);
        } catch (Exception e) {
            logger.error("【博易网】获取赛果失败:{}", code, e);
        }
        return bywResults;
    }

    //任务线程类
    class CrawTask<T> implements Callable<T> {

        private Object object;
        private Object[] args;
        private String methodName;

        public CrawTask(Object object, String methodName, Object[] args) {
            this.object = object;
            this.args = args;
            this.methodName = methodName;
        }

        @Override
        public T call() throws Exception {
            Method method = object.getClass().getMethod(methodName, String.class, Integer.class);   //此处应用反射机制，String.class是根据实际方法参数设置的
            return (T) method.invoke(object, args);
        }
    }

}
