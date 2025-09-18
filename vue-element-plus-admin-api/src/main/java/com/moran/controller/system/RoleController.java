package com.moran.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.bean.PageResponseBean;
import com.moran.conf.bean.ResponseBean;
import com.moran.controller.system.role.RoleReqVO;
import com.moran.controller.system.role.RoleRespVO;
import com.moran.model.SysRole;
import com.moran.model.vo.OptionRespVO;
import com.moran.service.SysMenuService;
import com.moran.service.SysRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moran.conf.bean.ResponseBean.ok;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/system/role")
@AllArgsConstructor
public class RoleController {
    private final SysRoleService roleService;
    private final SysMenuService sysMenuService;

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @SaCheckPermission("system:role:delete")
    public ResponseBean<Boolean> delete(@RequestParam("id") @Valid @NotNull(message = "请选择角色") Long id) {
        roleService.deleteRole(id);
        return ok(true);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SaCheckPermission("system:role:update")
    public ResponseBean<Boolean> update(@RequestBody @Valid RoleReqVO vo) {
        roleService.updateRole(vo);
        return ok(true);
    }

    /**
     * 新增
     */
    @PostMapping("/create")
    @SaCheckPermission("system:role:create")
    public ResponseBean<Boolean> create(@RequestBody @Valid RoleReqVO vo) {
        roleService.createRole(vo);
        return ok(true);
    }

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:role:query")
    public PageResponseBean<RoleRespVO> list(String name) {
        Page<SysRole> page = roleService.pageList(name);
        List<Long> parentMenuIds = sysMenuService.getParentIsExistChildren();
        return PageResponseBean.ok(page.getTotal(),
                page.getRecords().stream().map(r -> {
                    RoleRespVO vo = BeanUtil.toBean(r, RoleRespVO.class);
                    vo.setMenuIds(vo.getMenuIds().stream().filter(m -> !parentMenuIds.contains(m)).toList());
                    return vo;
                }).toList());
    }


    /**
     * 角色字典
     */
    @GetMapping("/simple-list")
    @SaIgnore
    public ResponseBean<List<OptionRespVO>> simpleList() {
        List<SysRole> roles = roleService.selectAll();
        return ok(roles.stream().map(r -> OptionRespVO.convert(r.getId(), r.getName())).toList());
    }
}
