package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.constants.SystemConstants;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.RoleDto;
import com.fusiyi.domain.dto.RoleStatusDto;
import com.fusiyi.domain.entity.Role;
import com.fusiyi.domain.entity.RoleMenu;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.domain.vo.RoleVo;
import com.fusiyi.service.RoleMenuService;
import com.fusiyi.service.RoleService;
import com.fusiyi.mapper.RoleMapper;
import com.fusiyi.utils.BeanCopyUtils;
import com.fusiyi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Fusiyi
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-11-24 14:39:53
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        //如果是管理员返回admin
        if (SecurityUtils.isAdmin()){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则返回对应信息
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }


    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        //封装查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleDto.getStatus()),Role::getStatus,roleDto.getStatus());
        queryWrapper.like(StringUtils.hasText(roleDto.getRoleName()),Role::getRoleName,roleDto.getRoleName());
        queryWrapper.orderByAsc(Role::getRoleSort);
        //分页查询
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Role> roles = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        PageVo pageVo = new PageVo(roleVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        //根据id修改状态
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",roleStatusDto.getRoleId());
        updateWrapper.set("status",roleStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(Role role) {
        save(role);
        List<RoleMenu> roleMenus = role.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateRole(Role role) {
        //先修改角色信息
        updateById(role);
        //删除角色关联的菜单信息
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(queryWrapper);
        //添加菜单信息
        List<RoleMenu> roleMenus = role.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        //删除角色前先删除关联的菜单
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);
        //删除角色
        removeById(id);
        return ResponseResult.okResult(200,"操作成功");
    }

    @Override
    public ResponseResult getAllRole() {
        //获取所有状态正常的角色
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(queryWrapper);
        return ResponseResult.okResult(roles);
    }
}




