package com.taro.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName LinkVo
 * Author taro
 * Date 2022/10/14 11:01
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    @TableId
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

}
