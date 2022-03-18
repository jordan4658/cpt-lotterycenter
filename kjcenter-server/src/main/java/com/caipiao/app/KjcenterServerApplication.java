package com.caipiao.app;

import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

//@MapperScan({"com.mapper","com.caipiao.app.controller", "com.caipiao.app","com.caipiao.app.service","com.caipiao.app.service.impl"})
@MapperScan({"com.mapper", "com.caipiao.app.mapper.lottery"})
@Configuration
@ServletComponentScan({"com.caipiao.core.library.filter"})
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = BaseRest.class)
@EnableFeignClients(basePackageClasses = BaseRest.class)
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class KjcenterServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(KjcenterServerApplication.class);

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(KjcenterServerApplication.class, args);
        SpringUtil.setApplicationContext(app);
//		memory();
    }

    public static void memory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        long INTHEAP = usage.getInit() / 1024 / 1024;
        long MAXHEAP = usage.getMax() / 1024 / 1024;
        long USEDHEAP = usage.getUsed() / 1024 / 1024;
        MemoryUsage nonusage = memoryMXBean.getNonHeapMemoryUsage();
        long NONINTNONHEAP = nonusage.getInit() / 1024 / 1024;
        long NONMAXNONHEAP = nonusage.getMax() / 1024 / 1024;
        long NONUSEDNONHEAP = nonusage.getUsed() / 1024 / 1024;
        logger.info("初始化堆内存INTHEAP:{}Mb,MAXHEAP:{}Mb,USEDHEAP:{}Mb", INTHEAP, MAXHEAP, USEDHEAP);
        logger.info("初始化非堆内存NONINTNONHEAP:{}Mb,NONMAXNONHEAP:{}Mb,NONUSEDNONHEAP:{}Mb", NONINTNONHEAP, NONMAXNONHEAP, NONUSEDNONHEAP);

        System.out.println("\nFull Information:");
        System.out.println("Heap Memory Usage:" + memoryMXBean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage:" + memoryMXBean.getNonHeapMemoryUsage());

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        logger.info("java options: [{}]", inputArguments);

        long m = Runtime.getRuntime().totalMemory() / 1024 / 1024;// Java 虚拟机中的内存总量，以字节为单位
        long n = Runtime.getRuntime().freeMemory() / 1024 / 1024;// Java 虚拟机中的空闲内存量
        long k = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        logger.info("总的内存量为:{}Mb,空闲内存量{}Mb,最大可用内存量{}Mb", m, n, k);
    }

}
