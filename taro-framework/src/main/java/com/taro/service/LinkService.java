package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-14 10:51:42
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLinks();
}
