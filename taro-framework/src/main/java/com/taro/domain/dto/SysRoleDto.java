package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName SysRoleDto
 * Author taro
 * Date 2022/11/7 17:37
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleDto {

    private String roleName;

    private String status;
}
