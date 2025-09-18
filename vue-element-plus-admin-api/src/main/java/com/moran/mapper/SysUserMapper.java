package com.moran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.mybatis.LambdaQueryWrapperX;
import com.moran.conf.mybatis.MyBatisUtils;
import com.moran.model.SysUser;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    default SysUser getUserByAccount(String username) {
        return selectOne(new LambdaQueryWrapperX<SysUser>()
                .eq(SysUser::getAccount, username));
    }

    default Page<SysUser> pageList(String account, String nickName, Long roleId, String mobile) {
        LambdaQueryWrapperX<SysUser> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.orderByDesc(SysUser::getCreateTime);
        wrapperX.likeIfPresent(SysUser::getAccount, account);
        wrapperX.likeIfPresent(SysUser::getNickName, nickName);
        wrapperX.likeIfPresent(SysUser::getMobile, mobile);
        if (roleId != null) {
            wrapperX.apply("json_contains(role_ids, json_array({0}))", roleId);
        }
        return selectPage(MyBatisUtils.buildPage(),wrapperX);
    }
}
