package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ClassName SysMenuTreeVo
 * Author taro
 * Date 2022/11/8 17:53
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysMenuTreeVo {

    private Long id;

    private String label;

    private Long parentId;

    private List<SysMenuTreeVo> children;

}
