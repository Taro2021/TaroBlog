package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName UpdateRoleDto
 * Author taro
 * Date 2022/11/8 19:39
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    private Long id;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;

    private String remark;

    private List<Long> menuIds;
}
