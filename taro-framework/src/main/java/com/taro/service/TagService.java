package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Tag;
import com.taro.domain.dto.TagListDto;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-28 16:33:40
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult<TagVo> getTagById(Long id);
}
