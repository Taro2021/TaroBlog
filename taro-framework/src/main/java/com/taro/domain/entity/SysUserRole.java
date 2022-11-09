package com.taro.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author makejava
 * @since 2022-11-09 15:47:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class SysUserRole  {
    //用户ID@TableId
    private Long userId;
    //角色ID@TableId
    private Long roleId;




}
