package com.moran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.mybatis.LambdaQueryWrapperX;
import com.moran.conf.mybatis.MyBatisUtils;
import com.moran.model.SysCodegenTable;

/**
 * <p>
 * 代码生成表定义 Mapper 接口
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-12-03
 */
public interface SysCodegenTableMapper extends BaseMapper<SysCodegenTable> {

    default Page<SysCodegenTable> page(String tableName) {
        return selectPage(MyBatisUtils.buildPage(),new LambdaQueryWrapperX<SysCodegenTable>()
                .orderByDesc(SysCodegenTable::getUpdateTime)
                .likeIfPresent(SysCodegenTable::getTableName,tableName));
    }
}
