package com.moran.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.moran.conf.bean.ResponseBean;
import com.moran.controller.system.menu.MenuReqVO;
import com.moran.controller.system.menu.MenuRespVO;
import com.moran.convert.MenuConvert;
import com.moran.model.SysMenu;
import com.moran.model.vo.TreeRespVO;
import com.moran.service.SysMenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moran.conf.bean.ResponseBean.ok;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/system/menu")
@AllArgsConstructor
public class MenuController {
    private final SysMenuService sysMenuService;

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @SaCheckPermission("system:menu:delete")
    public ResponseBean<Boolean> delete(@RequestParam("id") @Valid @NotNull(message = "请选择菜单") Long id) {
        sysMenuService.deleteMenu(id);
        return ok(true);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SaCheckPermission("system:menu:update")
    public ResponseBean<Boolean> update(@RequestBody @Valid MenuReqVO vo) {
        sysMenuService.updateMenu(vo);
        return ok(true);
    }

    /**
     * 创建菜单
     */
    @PostMapping("/create")
    @SaCheckPermission("system:menu:create")
    public ResponseBean<Boolean> create(@RequestBody @Valid MenuReqVO vo) {
        sysMenuService.createMenu(vo);
        return ok(true);
    }

    /**
     * 菜单列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:menu:query")
    public ResponseBean<List<MenuRespVO>> list() {
        List<SysMenu> list = sysMenuService.selectAll();
        return ok(MenuConvert.INSTANCE.convertList(list));
    }

    /**
     * 菜单字典
     */
    @GetMapping("/simple-list")
    @SaCheckLogin
    public ResponseBean<List<TreeRespVO>> simpleList() {
        List<SysMenu> menus = sysMenuService.selectAll();
        return ok(MenuConvert.INSTANCE.convertTree(menus));
    }
}
