package com.fusiyi.mapper;

import com.fusiyi.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-11-24 14:39:53
* @Entity com.fusiyi.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}




