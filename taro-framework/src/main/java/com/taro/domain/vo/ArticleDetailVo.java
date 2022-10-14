package com.taro.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName ArticleDetailVo
 * Author taro
 * Date 2022/10/14 10:42
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {

    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    //分类名，用于文章简介的显示，不写入数据库
    @TableField(exist = false)
    private String categoryName;

    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;

    private Long viewCount;

}
