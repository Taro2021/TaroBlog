package com.taro.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName BlogUserVo
 * Author taro
 * Date 2022/10/15 9:54
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private UserInfoVo userInfoVo;
}
