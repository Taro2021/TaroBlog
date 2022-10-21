package com.taro.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.taro.config.AliyunOSSConfig;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.service.UploadService;
import com.taro.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * ClassName UploadServiceImpl
 * Author taro
 * Date 2022/10/18 17:16
 * Version 1.0
 */

@Service
public class UploadServiceImpl implements UploadService {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        //头像大小校验
        if(Objects.isNull(img) || img.getSize() > SystemConstants.AVATAR_SIZE){
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        };

        //头像文件格式校验
        String originalFilename = img.getOriginalFilename();//原始文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        boolean tag = false;
        for(String type : IMAGE_TYPE) {
            if(type.equals(suffix)) {
                tag = true;
                break;
            }
        }
        //错误的文件格式抛出异常
        if(!tag) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    public String uploadOss(MultipartFile img, String filePath){
        OSS ossClient = aliyunOSSConfig.ossClient();
        String key = filePath;

        try {
            InputStream inputStream = null;
            try {
                inputStream = img.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PutObjectResult putObjectResult = ossClient.putObject(aliyunOSSConfig.getBucketName(), key, inputStream);
            return aliyunOSSConfig.getUrlPrefix() + "/" + key;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "www";
    }
}
