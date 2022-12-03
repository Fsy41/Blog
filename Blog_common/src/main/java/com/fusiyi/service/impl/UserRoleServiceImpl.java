package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.domain.entity.UserRole;
import com.fusiyi.service.UserRoleService;
import com.fusiyi.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Fusiyi
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-11-25 15:58:04
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




