package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName RoleDto
 * Author taro
 * Date 2022/11/7 18:32
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long roleId;

    private String status;
}
