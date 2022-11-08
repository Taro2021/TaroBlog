package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.UserDto;
import com.taro.domain.dto.UserListDto;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.UserInfoVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.UserMapper;
import com.taro.service.SysRoleService;
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
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
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
        //TODO 用户信息校验存储
        return null;
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

