package com.moran.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.v7.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.bean.PageResponseBean;
import com.moran.controller.system.log.LogRespVO;
import com.moran.enums.LoginLogTypeEnum;
import com.moran.model.SysLoginLog;
import com.moran.service.SysLoginLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理
 */
@RestController
@RequestMapping("/system/log")
@AllArgsConstructor
public class LogController {
    private final SysLoginLogService sysLoginLogService;

    /**
     * 日志列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:log:query")
    public PageResponseBean<LogRespVO> list(Integer type, String nickName) {
        Page<SysLoginLog> page = sysLoginLogService.page(type, nickName);
        if (page.getTotal() >0) {
            return PageResponseBean.ok(page.getTotal(), page.getRecords().stream()
                    .map(log -> {
                        LogRespVO vo = BeanUtil.toBean(log, LogRespVO.class);
                        vo.setTypeName(LoginLogTypeEnum.getTypeName(log.getType()));
                        return vo;
                    }).toList());
        }
        return PageResponseBean.ok(page.getTotal(), null);
    }
}
