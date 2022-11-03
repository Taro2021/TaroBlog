package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName ArticleListDto
 * Author taro
 * Date 2022/11/3 15:24
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {

    private Long id;

    private String title;

    private String summary;
}
