package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName ArticleListVo
 * Author taro
 * Date 2022/10/11 22:52
 * Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {

    private Long id;

    private String title;

    private String summary;

    private String thumbnail;

    private Long categoryId;

    private Long viewCount;

    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;

    private String categoryName;

}
