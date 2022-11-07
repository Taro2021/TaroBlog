package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.TagListDto;
import com.taro.domain.entity.ArticleTag;
import com.taro.domain.entity.Tag;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.TagVo;
import com.taro.mapper.TagMapper;
import com.taro.service.ArticleTagService;
import com.taro.service.TagService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-28 16:33:41
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag :: getName, tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag :: getRemark, tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<TagVo> getTagById(Long id) {
        Tag tag = super.getById(id);
        TagVo tagVo = BeanCopyUtil.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = super.list();
        List<TagVo> tagVos = BeanCopyUtil.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    /**
     * 删除标签，并删除文章标签的映射
     * @param id
     * @return
     */
    // @Override
    // public ResponseResult deleteTagById(Long id) {
    //     LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
    //     queryWrapper.eq(ArticleTag :: getTagId, id);
    //     articleTagService.remove(queryWrapper);
    //     return ResponseResult.okResult();
    // }
}

