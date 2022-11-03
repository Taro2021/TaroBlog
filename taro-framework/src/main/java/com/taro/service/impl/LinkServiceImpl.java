package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.LinkListDto;
import com.taro.domain.entity.Link;
import com.taro.domain.vo.LinkVo;
import com.taro.domain.vo.PageVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.LinkMapper;
import com.taro.service.LinkService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public ResponseResult<PageVo> pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkListDto.getName()), Link :: getName, linkListDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()), Link :: getStatus, linkListDto.getStatus());

        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<LinkVo> linkVoList = page.getRecords()
                .stream()
                .map(link -> BeanCopyUtil.copyBean(link, LinkVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(linkVoList, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = super.getById(id);
        LinkVo linkVo = BeanCopyUtil.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult changeLinkStatus(LinkListDto linkDto) {
        Long id = linkDto.getId();
        if(Objects.isNull(id)) throw new SystemException(AppHttpCodeEnum.LINK_ID_NOT_NULL);

        LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link :: getId, linkDto.getId());
        updateWrapper.set(StringUtils.hasText(linkDto.getStatus()), Link :: getStatus, linkDto.getStatus());

        super.update(updateWrapper);
        return ResponseResult.okResult();
    }
}

