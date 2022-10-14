package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName LinkController
 * Author taro
 * Date 2022/10/14 10:54
 * Version 1.0
 */

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLinks() {
        return linkService.getAllLinks();
    }
}
