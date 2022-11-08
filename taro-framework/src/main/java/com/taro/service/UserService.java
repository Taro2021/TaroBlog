package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.UserDto;
import com.taro.domain.dto.UserListDto;
import com.taro.domain.entity.User;
import com.taro.domain.vo.PageVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-14 19:41:05
 */
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult registerUser(User user);

    ResponseResult<PageVo> userPageList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult saveUser(UserDto userDto);

}
