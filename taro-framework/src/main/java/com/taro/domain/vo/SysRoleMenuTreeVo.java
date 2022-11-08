package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName SysRoleMenuVo
 * Author taro
 * Date 2022/11/8 19:27
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenuTreeVo {

    private List<SysMenuTreeVo> menus;

    private List<Long> checkedKeys;
}
