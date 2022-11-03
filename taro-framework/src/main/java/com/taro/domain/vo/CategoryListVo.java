package com.taro.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName CategoryListVo
 * Author taro
 * Date 2022/11/3 14:01
 * Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListVo {

    @TableId
    private Long id;
    //分类名
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

}
