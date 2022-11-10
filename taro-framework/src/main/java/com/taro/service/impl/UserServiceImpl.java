package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.UpdateUserDto;
import com.taro.domain.dto.UserDto;
import com.taro.domain.dto.UserListDto;
import com.taro.domain.entity.*;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.UserInfoVo;
import com.taro.domain.vo.UserRolesVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.UserMapper;
import com.taro.service.SysRoleMenuService;
import com.taro.service.SysRoleService;
import com.taro.service.SysUserRoleService;
import com.taro.service.UserService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 19:41:05
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    public void checkFormat(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
    }

    @Override
    public ResponseResult getUserInfo() {
        //根据 token 获取 userId
        Long userId = SecurityUtils.getUserId();
        //根据 id 查询信息
        User user = super.getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        super.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult registerUser(User user) {
        //数据非空判断
        checkFormat(user);
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //用户是否已存在校验
        if(userNameExit(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        if(nickNameExit(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //存储密码密文
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        super.save(user);
        return ResponseResult.okResult();
    }

    /**
     * 分页查询 user 信息
     * @param pageNum
     * @param pageSize
     * @param userListDto
     * @return
     */
    @Override
    public ResponseResult<PageVo> userPageList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Strings.hasText(userListDto.getUserName()), User :: getUserName,  userListDto.getUserName());
        queryWrapper.like(Strings.hasText(userListDto.getPhonenumber()), User :: getPhonenumber, userListDto.getPhonenumber());
        queryWrapper.eq(Strings.hasText(userListDto.getStatus()), User :: getStatus, userListDto.getStatus());

        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult saveUser(UserDto userDto) {
        User user = BeanCopyUtil.copyBean(userDto, User.class);
        registerUser(user);
        //保存新增用户的角色信息
        List<SysUserRole> sysUserRoles = userDto.getRoleIds().stream()
                .map(roleId -> new SysUserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        sysUserRoleService.saveBatch(sysUserRoles);

        return ResponseResult.okResult();
    }

    /**
     * 后台获取用户信息，同时获取其对应的角色信息
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public ResponseResult getUserInfoById(Long userId) {
        User user = getById(userId);
        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserRole :: getUserId, userId);
        List<SysUserRole> userRoles = sysUserRoleService.list(lambdaQueryWrapper);


        List<Long> roleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        List<SysRole> roles = roleIds.stream()
                .map(roleId -> sysRoleService.getById(roleId))
                .collect(Collectors.toList());

        return ResponseResult.okResult(new UserRolesVo(roleIds, roles, user));
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        User user = BeanCopyUtil.copyBean(updateUserDto, User.class);
        checkFormat(user);
        Long userId = user.getId();
        if(Objects.isNull(userId)) {
            System.out.println("1---------------------------------------------------------");
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        User userOld = getById(userId);
        if(Objects.isNull(userOld)) {
            System.out.println("2---------------------------------------------------------");
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //用户名不能更改
        if(!user.getUserName().equals(userOld.getUserName())) {
            System.out.println("3---------------------------------------------------------");
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //昵称更改了的话不能和别人重复
        if(!user.getNickName().equals(userOld.getNickName()) && nickNameExit(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        updateById(user);

        //更新用户角色信息
        LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(userRoleLambdaQueryWrapper);

        List<SysUserRole> userRoleList = updateUserDto.getRoleIds().stream()
                .map(roleId -> new SysUserRole(userId, roleId))
                .collect(Collectors.toList());

        sysUserRoleService.saveBatch(userRoleList);

        return ResponseResult.okResult();
    }

    public boolean userNameExit(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = super.getOne(queryWrapper);
        return !Objects.isNull(user);
    }

    public boolean nickNameExit(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, nickName);
        User user = super.getOne(queryWrapper);
        return !Objects.isNull(user);
    }
}

