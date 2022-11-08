package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName UserDto
 * Author taro
 * Date 2022/11/8 20:52
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userName;

    private String nickName;

    private String password;

    private String phonenumber;

    private String email;

    private String sex;

    private String status;

    private List<Long> roleIds;
}
