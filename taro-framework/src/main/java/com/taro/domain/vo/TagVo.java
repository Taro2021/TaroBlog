package com.taro.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ClassName TagVo
 * Author taro
 * Date 2022/11/3 13:24
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TagVo {

    private Long id;

    private String name;

    private String remark;
}
