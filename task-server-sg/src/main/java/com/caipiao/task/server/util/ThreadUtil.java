package com.caipiao.task.server.util;//package com.caipiao.task.server.util;
//
//import java.util.concurrent.*;
//
///**
// * 线程工具类
// */
//public class ThreadUtil {
//    //maximumPoolSize设置为50 ，拒绝策略为AbortPolic策略，直接抛出异常
//    public static ExecutorService threadPool = new ThreadPoolExecutor(5, 50, 5,
//            TimeUnit.MINUTES,
//            new LinkedBlockingQueue<>(),
//            Executors.defaultThreadFactory(),
//            new ThreadPoolExecutor.AbortPolicy());
//
//    public static ExecutorService getExecutorService() {
//        return threadPool;
//    }
//
//}
