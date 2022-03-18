package com.caipiao.task.server.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//@Component
@Configuration
public class AliOSSProperties implements InitializingBean {

    @Value("${oss.file.endpoint}")
    private String endpoint;
    @Value("${oss.file.key-id}")
    private String accessKeyId;
    @Value("${oss.file.key-secret}")
    private String accessKeySecret;
    @Value("${oss.file.bucket-name}")
    private String bucketName;
    @Value("${oss.file.file-host}")
    private String fileHost;
    @Value("${lhtk.proxyip}")
    private String proxyip;
    @Value("${lhtk.proxyport}")
    private String proxyport;

    public static String ENDPOINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String FILE_HOST;
    public static String PROXYIP;
    public static String PROXYPORT;

    @Override
    public void afterPropertiesSet() {
        ENDPOINT = this.endpoint;
        ACCESS_KEY_ID = this.accessKeyId;
        ACCESS_KEY_SECRET = this.accessKeySecret;
        BUCKET_NAME = this.bucketName;
        FILE_HOST = this.fileHost;
        PROXYIP=this.proxyip;
         PROXYPORT=this.proxyport;
    }

}
