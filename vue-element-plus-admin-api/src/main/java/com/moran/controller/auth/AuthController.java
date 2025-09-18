package com.moran.controller.auth;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.moran.conf.bean.ResponseBean;
import com.moran.conf.bean.UserInfo;
import com.moran.conf.constant.CommonConstant;
import com.moran.controller.auth.vo.AccountLoginRespVO;
import com.moran.controller.auth.vo.RouterRespVO;
import com.moran.convert.MenuConvert;
import com.moran.enums.LoginLogTypeEnum;
import com.moran.model.SysMenu;
import com.moran.model.SysRole;
import com.moran.model.SysUser;
import com.moran.service.SysLoginLogService;
import com.moran.service.SysMenuService;
import com.moran.service.SysRoleService;
import com.moran.service.SysUserService;
import com.moran.utils.ServletUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moran.conf.bean.ResponseBean.fail;
import static com.moran.conf.bean.ResponseBean.ok;

/**
 * 登录管理
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final SysLoginLogService sysLoginLogService;
    /**
     * 登出
     */
    @GetMapping("/logout")
    @SaCheckLogin
    public ResponseBean<Boolean> logout() {
        UserInfo userInfo = ServletUtil.getUserInfo();
        sysLoginLogService.createLog(LoginLogTypeEnum.LOGOUT.getValue());
        StpUtil.logout();
        return ok(true);
    }

    /**
     * 获取路由
     */
    @GetMapping("/routers")
    @SaCheckLogin
    public ResponseBean<List<RouterRespVO>> routers() {
        List<SysMenu> menus = ServletUtil.getUserInfo().getMenus();
        return ok(MenuConvert.INSTANCE.convertRouters(menus));
    }

    /**
     * 用户信息
     */
    @GetMapping("/userInfo")
    @SaCheckLogin
    public ResponseBean<UserInfo> userInfo() {
        UserInfo userInfo = ServletUtil.getUserInfo();
        userInfo.setMenus(null);
        return ok(userInfo);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @SaIgnore
    public ResponseBean<String> login(@RequestBody @Valid AccountLoginRespVO vo) {
        SysUser user = sysUserService.getUserByAccount(vo.getUsername());
        if (user == null) {
            return fail("账号或密码错误");
        }
        if (!sysUserService.checkPassword(vo.getPassword(), user.getPassword())) {
            return fail("账号或密码错误");
        }
        if (!user.getStatus()) {
            return fail("账号已禁用");
        }
        if (CollUtil.isEmpty(user.getRoleIds())) {
            return fail("暂无权限");
        }
        List<SysRole> roles = sysRoleService.listByIds(user.getRoleIds());
        if (CollUtil.isEmpty(roles)) {
            return fail("暂未初始化角色");
        }
        List<Long> menuIds = roles.stream().map(SysRole::getMenuIds).flatMap(List::stream).toList();
        if (CollUtil.isEmpty(menuIds)) {
            return fail("暂未初始化菜单");
        }
        List<SysMenu> menus = sysMenuService.listByIds(menuIds);
        if (CollUtil.isEmpty(menus)) {
            return fail("<暂无权限>");
        }
        StpUtil.login(user.getId());
        UserInfo userInfo = UserInfo.convert(user, roles, menus);
        StpUtil.getSession().set(CommonConstant.USER_INFO, userInfo);
        sysLoginLogService.createLog(LoginLogTypeEnum.LOGIN.getValue());
        return ok(StpUtil.getTokenValue());
    }
}
