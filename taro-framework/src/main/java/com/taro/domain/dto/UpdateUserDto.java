package com.taro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ClassName UpdateUserDto
 * Author taro
 * Date 2022/11/9 16:50
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateUserDto {

    private Long id;

    private String userName;

    private String nickName;

    private String email;

    private String phonenumber;

    private String sex;

    private String status;

    private List<Long> roleIds;
}
