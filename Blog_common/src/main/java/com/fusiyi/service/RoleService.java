package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.RoleDto;
import com.fusiyi.domain.dto.RoleStatusDto;
import com.fusiyi.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-11-24 14:39:53
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(Role role);

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long id);

    ResponseResult getAllRole();
}
