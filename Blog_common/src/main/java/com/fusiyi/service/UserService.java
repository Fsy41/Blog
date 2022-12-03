package com.fusiyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.UserDto;
import com.fusiyi.domain.dto.UserStatusDto;
import com.fusiyi.domain.entity.User;
import com.fusiyi.domain.vo.PageVo;

/**
* @author Fusiyi
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-11-22 20:06:11
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> getUserList(Integer pageNum, Integer pageSize, UserDto userDto);

    ResponseResult addUser(User user);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserListById(Long id);

    ResponseResult updateUser(User user);

    ResponseResult changeStatus(UserStatusDto userStatusDto);
}
