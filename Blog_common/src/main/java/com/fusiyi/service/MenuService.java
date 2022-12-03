package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.MenuDto;
import com.fusiyi.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-11-24 14:39:48
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(MenuDto menuDto);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult getMenuTree();

    ResponseResult getMenuTreeById(Long id);
}
