package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * ClassName ArticleTagDetailVo
 * Author taro
 * Date 2022/11/5 19:47
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagDetailVo {

    private Long id;

    private Long categoryId;

    private String content;

    private Long createBy;

    private Date createTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    //是否置顶（0否，1是）
    private String isTop;

    //是否允许评论 1是，0否
    private String isComment;

    //状态（0已发布，1草稿）
    private String status;

    //文章摘要
    private String summary;

    //缩略图
    private String thumbnail;

    //标题
    private String title;

    private Long updateBy;

    private Date updateTime;

    //访问量
    private Long viewCount;

    private List<Long> tags;
}
