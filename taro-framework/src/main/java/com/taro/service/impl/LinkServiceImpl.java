package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Link;
import com.taro.domain.vo.LinkVo;
import com.taro.mapper.LinkMapper;
import com.taro.service.LinkService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 10:51:42
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLinks() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = super.list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtil.copyBeanList(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}

