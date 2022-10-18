package com.taro.service.impl;

import com.taro.domain.ResponseResult;
import com.taro.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName UploadServiceImpl
 * Author taro
 * Date 2022/10/18 17:16
 * Version 1.0
 */

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        return null;
    }
}
