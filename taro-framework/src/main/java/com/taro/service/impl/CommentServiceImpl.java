package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Comment;
import com.taro.domain.entity.User;
import com.taro.domain.vo.CommentVo;
import com.taro.domain.vo.PageVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.CommentMapper;
import com.taro.service.CommentService;
import com.taro.service.UserService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-15 20:01:24
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //根据文章id查询根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(articleId != null, Comment :: getArticleId, articleId);
        queryWrapper.eq(Comment :: getRootId, SystemConstants.ROOT_COMMENT);
        queryWrapper.eq(Comment::getType,commentType); //根据不同评论类型查找

        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //获取子评论
        for(CommentVo commentVo : commentVoList){
            commentVo.setChildren(getChildren(commentVo.getId()));
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    //添加评论
    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        super.save(comment);
        return ResponseResult.okResult();
    }

    //根评论
    private List<CommentVo> toCommentVoList(List<Comment> comments) {

        List<CommentVo> commentVos = BeanCopyUtil.copyBeanList(comments, CommentVo.class);
        //载入评论的用户名和回复的用户名
        for(CommentVo commentVo : commentVos) {
            User userById = userService.getById(commentVo.getCreateBy());
            if(userById != null) {
                commentVo.setUsername(userById.getNickName());
            }

            Long toCommentUserId = commentVo.getToCommentUserId();
            if(toCommentUserId != -1) {
                commentVo.setToCommentUserName(userService.getById(toCommentUserId).getNickName());
            }
        }

        return commentVos;
    }

    /**
     * 根据根评论查找子评论
     * @param rootId
     * @return
     */
    private List<CommentVo> getChildren(Long rootId){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, rootId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = super.list(queryWrapper);

        return toCommentVoList(comments);
    }

}

