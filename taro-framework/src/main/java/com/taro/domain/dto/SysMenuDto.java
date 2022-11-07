package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName MenuDto
 * Author taro
 * Date 2022/11/7 17:02
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuDto {

    private String menuName;

    private String status;
}
