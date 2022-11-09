package com.taro.domain.vo;

import com.taro.domain.entity.SysRole;
import com.taro.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ClassName UserRoleVo
 * Author taro
 * Date 2022/11/9 16:05
 * Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRolesVo {

    private List<Long> roleIds;

    private List<SysRole> roles;

    private User user;
}
