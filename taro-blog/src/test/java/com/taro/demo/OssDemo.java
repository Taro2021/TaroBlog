package com.taro.demo;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.taro.config.AliyunOSSConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ClassName OssDemo
 * Author taro
 * Date 2022/10/18 15:41
 * Version 1.0
 */

@SpringBootTest
public class OssDemo {

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    @Test
    public void test() {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        // String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        // String accessKeyId
        // String accessKeySecret
        // // 填写Bucket名称，例如examplebucket。
        // String bucketName = "taro-blog";
        //
        // // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.jpg";

        // 创建OSSClient实例。
        OSS ossClient = aliyunOSSConfig.ossClient();

        try {
            // String content = "Hello OSS";
            InputStream inputStream = new FileInputStream("C:\\Users\\lzy\\Desktop\\0_0\\img01.jpg");
            PutObjectResult putObjectResult = ossClient.putObject(aliyunOSSConfig.getBucketName(), objectName, inputStream);
            System.out.println("----------------------------------------------------\n\n\n\n" + putObjectResult);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | FileNotFoundException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }

}
