package com.taro.entity.vo;

import com.taro.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName RoutersVo
 * Author taro
 * Date 2022/11/2 15:44
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<SysMenu> menus;
}
