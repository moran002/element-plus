package com.moran.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.bean.PageResponseBean;
import com.moran.model.SysCodegenTable;
import com.moran.service.SysCodegenTableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理/代码生成
 */
@RestController
@RequestMapping("/system/codegen")
@AllArgsConstructor
public class CodegenController {
    private final SysCodegenTableService sysCodegenTableService;



    /**
     * 列表
     */
    @GetMapping("/page")
    // @SaCheckPermission("system:codegen:query")
    public PageResponseBean<SysCodegenTable> page(String tableName) {
        Page<SysCodegenTable> page = sysCodegenTableService.page(tableName);
        if (page.getTotal() > 0) {

        }
        return null;
    }
}
