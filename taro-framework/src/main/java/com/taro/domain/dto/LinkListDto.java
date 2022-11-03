package com.taro.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName LinkListDto
 * Author taro
 * Date 2022/11/3 14:25
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkListDto {

    private Long id;

    private String name;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
}
