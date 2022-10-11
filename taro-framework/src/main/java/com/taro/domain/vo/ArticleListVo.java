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

    private Long viewCount;

    private Date createTime;


}
