package com.moran.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.bean.UserInfo;
import com.moran.conf.constant.CommonConstant;
import com.moran.mapper.SysLoginLogMapper;
import com.moran.model.SysLoginLog;
import com.moran.utils.ServletUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 登录日志 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-17
 */
@Service
public class SysLoginLogService {
    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    public void createLog(Integer type) {
        SysLoginLog log = new SysLoginLog();
        UserInfo userInfo = ServletUtil.getUserInfo();
        log.setUserId(userInfo.getId());
        log.setNickName(userInfo.getUsername());
        log.setResult(CommonConstant.SUCCESS);
        log.setType(type);
        log.setUserAgent(ServletUtil.getUserAgent());
        log.setUserIp(ServletUtil.getClientIP());
        sysLoginLogMapper.insert(log);
    }

    public Page<SysLoginLog> page(Integer type, String nickName) {
        return sysLoginLogMapper.page(type, nickName);
    }
}
