package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName AddRoleDto
 * Author taro
 * Date 2022/11/8 18:23
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;

    private String remark;

    private List<Long> menuIds;
}
