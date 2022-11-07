package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Tag;
import com.taro.domain.vo.PageVo;
import com.taro.domain.dto.TagListDto;
import com.taro.domain.vo.TagVo;
import com.taro.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("@ps.hasPermission('content:article:query')")
    @GetMapping("/list")
    public ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PreAuthorize("@ps.hasPermission('content:article:query')")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

    @PreAuthorize("@ps.hasPermission('content:article:update')")
    @PostMapping
    public ResponseResult saveTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@ps.hasPermission('content:article:update')")
    @DeleteMapping( "/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@ps.hasPermission('content:article:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult<TagVo> getTagById(@PathVariable(value = "id") Long id) {
        return tagService.getTagById(id);
    }

    @PreAuthorize("@ps.hasPermission('content:article:update')")
    @PutMapping
    public ResponseResult update(@RequestBody Tag tag) {
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

}
