package com.moran.service;

import com.moran.mapper.SysOperateLogMapper;
import com.moran.model.SysOperateLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * 操作日志记录 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-17
 */
@Service
public class SysOperateLogService {
    @Resource
    private SysOperateLogMapper sysOperateLogMapper;


    public void createLog(SysOperateLog log) {
        sysOperateLogMapper.insert(log);
    }
}
