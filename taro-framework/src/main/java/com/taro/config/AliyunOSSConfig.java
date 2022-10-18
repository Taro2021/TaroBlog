package com.taro.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName AliyunOSSConfig
 * Author taro
 * Date 2022/10/18 17:03
 * Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OSS ossClient(){
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}
