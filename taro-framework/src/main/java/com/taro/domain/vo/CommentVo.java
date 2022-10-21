package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * ClassName ConmmentVo
 * Author taro
 * Date 2022/10/15 20:47
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;

    private String toCommentUserName;

    //回复目标评论id
    private Long toCommentId;

    //头像
    private String avatar;

    private Long createBy;

    private Date createTime;

    private String username;

    //子评论
    private List<CommentVo> children;
}
