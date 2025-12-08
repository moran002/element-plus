package com.moran.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.mapper.SysCodegenTableMapper;
import com.moran.model.SysCodegenTable;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * 代码生成表定义 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-12-03
 */
@Service
public class SysCodegenTableService {
    @Resource
    private SysCodegenTableMapper sysCodegenTableMapper;

    public Page<SysCodegenTable> page(String tableName) {
        return sysCodegenTableMapper.page(tableName);
    }
}
