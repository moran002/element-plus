package com.moran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.mybatis.LambdaQueryWrapperX;
import com.moran.conf.mybatis.MyBatisUtils;
import com.moran.model.SysLoginLog;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-17
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    default Page<SysLoginLog> page(Integer type, String nickName) {
        return selectPage(MyBatisUtils.buildPage(),new LambdaQueryWrapperX<SysLoginLog>()
                .orderByDesc(SysLoginLog::getCreateTime)
                .eqIfPresent(SysLoginLog::getType, type)
                .likeIfPresent(SysLoginLog::getNickName, nickName));
    }
}
