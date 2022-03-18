package com.caipiao.task.server;

import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableAsync
@MapperScan({ "com.mapper", "com.caipiao.task.server.mapper" })
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.caipiao")
@SpringBootApplication
public class TaskServerApplication {
    public static void main(String[] args) {
        System.out.println("===============================================服务启动中========================================================================");
        ApplicationContext app = SpringApplication.run(TaskServerApplication.class, args);
        SpringUtil.setApplicationContext(app);

    }

}
