package com.taro.service;

import com.taro.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName UploadService
 * Author taro
 * Date 2022/10/18 17:15
 * Version 1.0
 */

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
