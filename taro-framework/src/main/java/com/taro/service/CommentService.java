package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-15 20:01:24
 */
public interface CommentService extends IService<Comment> {


    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
