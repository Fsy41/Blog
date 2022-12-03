package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.domain.entity.RoleMenu;
import com.fusiyi.service.RoleMenuService;
import com.fusiyi.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Fusiyi
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-11-25 15:08:48
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




