package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.constants.SystemConstants;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.UserDto;
import com.fusiyi.domain.dto.UserStatusDto;
import com.fusiyi.domain.entity.Link;
import com.fusiyi.domain.entity.Role;
import com.fusiyi.domain.entity.User;
import com.fusiyi.domain.entity.UserRole;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.domain.vo.UserInfoVo;
import com.fusiyi.domain.vo.UserVo;
import com.fusiyi.enums.AppHttpCodeEnum;
import com.fusiyi.exception.SystemException;
import com.fusiyi.mapper.UserMapper;
import com.fusiyi.service.RoleService;
import com.fusiyi.service.UserRoleService;
import com.fusiyi.service.UserService;
import com.fusiyi.utils.BeanCopyUtils;
import com.fusiyi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
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
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    /**
     * 分页查询用户信息
     * @param pageNum
     * @param pageSize
     * @param userDto
     * @return
     */
    @Override
    public ResponseResult<PageVo> getUserList(Integer pageNum, Integer pageSize, UserDto userDto) {
        //构造查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userDto.getUserName()),User::getUserName,userDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userDto.getPhonenumber()),User::getPhonenumber,userDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userDto.getStatus()),User::getStatus,userDto.getStatus());
        //构造分页
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(User user) {
        //新增用户,新增用户前对传来的信息判断并且对密码加密
        //对数据进行非空判断
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
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(phoneNumberExist(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        //新增用户关联的角色
        List<UserRole> userRoles = user.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult(200,"操作成功");
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        //删除用户前先删除用户所拥有的角色信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        userRoleService.remove(queryWrapper);
        //删除用户
        removeById(id);
        return ResponseResult.okResult(200,"操作成功");
    }

    @Override
    public ResponseResult getUserListById(Long id) {
        //获取所有状态正常的角色
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = roleService.list(lambdaQueryWrapper);
        //根据id获取对应角色id
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        //获取用户信息
        User user = getById(id);
        return ResponseResult.okResult(new UserVo(roleIds,roles,user));
    }

    @Override
    public ResponseResult updateUser(User user) {
        //更新用户信息
        updateById(user);
        //删除用户对应角色信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);
        //新增用户对应角色信息
        List<UserRole> userRoles = user.getRoleIds().stream().map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult(200,"操作成功");
    }

    @Override
    public ResponseResult changeStatus(UserStatusDto userStatusDto) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userStatusDto.getUserId());
        updateWrapper.set("status",userStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult(200,"操作成功");
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }

    private boolean phoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phoneNumber);
        return count(queryWrapper)>0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
}
