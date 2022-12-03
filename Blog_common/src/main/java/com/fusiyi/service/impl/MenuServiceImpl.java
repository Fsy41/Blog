package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.constants.SystemConstants;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.MenuDto;
import com.fusiyi.domain.entity.Menu;
import com.fusiyi.domain.entity.RoleMenu;
import com.fusiyi.domain.vo.MenuTreeByIdVo;
import com.fusiyi.domain.vo.MenuTreeVo;
import com.fusiyi.enums.AppHttpCodeEnum;
import com.fusiyi.exception.SystemException;
import com.fusiyi.service.MenuService;
import com.fusiyi.mapper.MenuMapper;
import com.fusiyi.service.RoleMenuService;
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
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-11-24 14:39:48
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //如果是管理员返回所有的权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MEUN,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList(MenuDto menuDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //菜单名和状态查询
        queryWrapper.eq(StringUtils.hasText(menuDto.getStatus()),Menu::getStatus,menuDto.getStatus());
        queryWrapper.like(StringUtils.hasText(menuDto.getMenuName()),Menu::getMenuName,menuDto.getMenuName());
        //父id和orderNum排序
        queryWrapper.orderByAsc(Menu::getOrderNum,Menu::getParentId);
        //不需要分页
        List<Menu> menus = list(queryWrapper);
        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        //判断传过来的数据的父菜单是否为自己
        if (menu.getId().equals(menu.getParentId())){
            //是自己，提示错误
            throw new SystemException(500,"修改菜单"+"'"+menu.getMenuName()+"'"+"失败，上级菜单不能选择自己");
        }
        //修改
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,menu.getId());
        update(menu,queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        //判断该菜单是否有子菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        int count = count(queryWrapper);
        if (count>0){
            //有子菜单不能删除
            throw new SystemException(500,"存在子菜单不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuTree() {
        List<MenuTreeVo> menus = getBaseMapper().getMenuTree();
        List<MenuTreeVo> menuTreeVos = builderMenuTree1(menus, 0L);
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult getMenuTreeById(Long id) {
        MenuTreeByIdVo menuTreeByIdVo = new MenuTreeByIdVo();
        //查询出所有的menu
        List<MenuTreeVo> menus = getBaseMapper().getMenuTree();
        List<MenuTreeVo> menuTreeVos = builderMenuTree1(menus, 0L);
        menuTreeByIdVo.setMenus(menuTreeVos);
        //查询出被选中的menu
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<Long> checkedKeys = new ArrayList<>();
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        for (RoleMenu roleMenu : roleMenus) {
            checkedKeys.add(roleMenu.getMenuId());
        }
        menuTreeByIdVo.setCheckedKeys(checkedKeys);
        return ResponseResult.okResult(menuTreeByIdVo);
    }

    private List<MenuTreeVo> builderMenuTree1(List<MenuTreeVo> menuTreeVos,Long parentId){
        List<MenuTreeVo> menuTreeVos1 = menuTreeVos.stream()
                .filter(menuTree -> menuTree.getParentId().equals(parentId))
                .map(menuTree -> menuTree.setChildren(getChildren1(menuTree, menuTreeVos)))
                .collect(Collectors.toList());
        return menuTreeVos1;
    }

    private List<MenuTreeVo> getChildren1(MenuTreeVo menuTreeVo, List<MenuTreeVo> menuTreeVos) {
        List<MenuTreeVo> childrenList = menuTreeVos.stream()
                .filter(m -> m.getParentId().equals(menuTreeVo.getId()))
                .map(m->m.setChildren(getChildren1(m,menuTreeVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




