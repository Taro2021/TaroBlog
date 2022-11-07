package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName SysRoleVo
 * Author taro
 * Date 2022/11/7 17:44
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleListVo {

    //角色ID
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    private Date createTime;
}
