package com.moran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moran.conf.mybatis.LambdaQueryWrapperX;
import com.moran.model.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    default List<SysMenu> listByIds(List<Long> menuIds) {
        return selectList(new LambdaQueryWrapperX<SysMenu>().in(SysMenu::getId, menuIds));
    }

    default List<SysMenu> selectAll() {
        return selectList(new LambdaQueryWrapperX<SysMenu>().orderByAsc(SysMenu::getSort));
    }
}
