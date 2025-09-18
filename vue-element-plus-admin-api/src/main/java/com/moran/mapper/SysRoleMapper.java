package com.moran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.mybatis.LambdaQueryWrapperX;
import com.moran.conf.mybatis.MyBatisUtils;
import com.moran.model.SysRole;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    default Page<SysRole> pageList(String name) {
        return selectPage(MyBatisUtils.buildPage(),new LambdaQueryWrapperX<SysRole>()
                .orderByDesc(SysRole::getCreateTime)
                .likeIfPresent(SysRole::getName, name));
    }

    default List<SysRole> listByIds(List<Long> roleIds) {
        return selectList(new LambdaQueryWrapperX<SysRole>().in(SysRole::getId, roleIds));
    }

    default SysRole selectByRoleName(String name) {
        return selectOne(new LambdaQueryWrapperX<SysRole>().eq(SysRole::getName, name));
    }
}
