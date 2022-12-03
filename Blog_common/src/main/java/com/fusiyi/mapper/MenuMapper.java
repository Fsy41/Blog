package com.fusiyi.mapper;

import com.fusiyi.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fusiyi.domain.vo.MenuTreeVo;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-11-24 14:39:48
* @Entity com.fusiyi.domain.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<MenuTreeVo> getMenuTree();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




