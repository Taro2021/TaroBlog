package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Tag;
import com.taro.domain.vo.PageVo;
import com.taro.domain.dto.TagListDto;
import com.taro.domain.vo.TagVo;
import com.taro.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName TagController
 * Author taro
 * Date 2022/10/28 16:36
 * Version 1.0
 */

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult saveTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult<TagVo> getTagById(@PathVariable("id") Long id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Tag tag) {
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }
}
