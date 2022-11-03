package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.LinkListDto;
import com.taro.domain.entity.Link;
import com.taro.domain.vo.PageVo;
import com.taro.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName ContentLinkController
 * Author taro
 * Date 2022/11/3 14:21
 * Version 1.0
 */

@RestController
@RequestMapping("/content/link")
public class ContentLinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getContentLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        return linkService.pageLinkList(pageNum, pageSize, linkListDto);
    }

    @GetMapping("/getAllLink")
    public ResponseResult getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id) {
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult saveLink(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody LinkListDto linkDto) {
        return linkService.changeLinkStatus(linkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
