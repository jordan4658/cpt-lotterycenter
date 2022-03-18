package com.caipiao.task.server.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.caipiao.task.server.config.AliOSSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云文件上传工具类
 */
public class AliOSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(AliOSSUtil.class);

    /**
     * 上传单个文件
     *
     * @return
     */
    public static String upload(InputStream is, String prefix, String type, String folder) {
        // 获取文件名

        logger.debug("=========> OSS文件上传开始,源文件名：" + is.toString());

        // 获取配置参数
        String endpoint = AliOSSProperties.ENDPOINT;
        String accessKeyId = AliOSSProperties.ACCESS_KEY_ID;
        String accessKeySecret = AliOSSProperties.ACCESS_KEY_SECRET;
        String bucketName = AliOSSProperties.BUCKET_NAME;
        String fileHost = AliOSSProperties.FILE_HOST;

        // 获取年-月-日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 容器不存在，就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            // 获取文件后缀

            // 创建文件路径
            String filePath = type + "/" + folder + "/" + (dateStr + "/" + UUID.randomUUID().toString() + prefix);
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, filePath, is));
            // 设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            // 上传文件结果
            if (null != result) {
//                String url = fileHost.replace("bucket-name", bucketName) + filePath;
                String url = fileHost + filePath;
                logger.debug("=========> OSS文件上传成功,图片地址：" + url);
                return url;
            }
        } catch (Exception oe) {
            logger.error(oe.getMessage());
        } finally {
            // 关闭 OSS 服务
            ossClient.shutdown();
        }
        return "";
    }

}
