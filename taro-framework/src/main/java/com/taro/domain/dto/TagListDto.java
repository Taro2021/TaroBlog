package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName TagListDto
 * Author taro
 * Date 2022/11/3 12:30
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {

    private String name;

    private String remark;
}
