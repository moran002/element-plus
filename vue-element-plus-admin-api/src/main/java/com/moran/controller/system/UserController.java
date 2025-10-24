package com.moran.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.v7.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.bean.PageResponseBean;
import com.moran.conf.bean.ResponseBean;
import com.moran.controller.system.user.UserPasswordReqVO;
import com.moran.controller.system.user.UserReqVO;
import com.moran.controller.system.user.UserRespVO;
import com.moran.model.SysRole;
import com.moran.model.SysUser;
import com.moran.service.SysRoleService;
import com.moran.service.SysUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
public class UserController {
    private final SysUserService userService;
    private final SysRoleService roleService;

    /**
     * 设置密码
     */
    @PostMapping("/password")
    @SaCheckPermission("system:user:password")
    public ResponseBean<Boolean> password(@RequestBody @Valid UserPasswordReqVO vo) {
        userService.updatePassword(vo);
        return ResponseBean.ok(true);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @SaCheckPermission("system:user:delete")
    public ResponseBean<Boolean> delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return ResponseBean.ok(true);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SaCheckPermission("system:user:update")
    public ResponseBean<Boolean> update(@RequestBody @Valid UserReqVO vo) {
        userService.update(vo);
        return ResponseBean.ok(true);
    }

    /**
     * 新增
     */
    @PostMapping("/create")
    @SaCheckPermission("system:user:create")
    @SaCheckLogin
    public ResponseBean<Boolean> create(@RequestBody @Valid UserReqVO vo) {
        userService.save(vo);
        return ResponseBean.ok(true);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:user:query")
    public PageResponseBean<UserRespVO> list(String account, String nickName, Long roleIds, String mobile) {
        Page<SysUser> page = userService.pageList(account, nickName, roleIds, mobile);
        if (page.getTotal() > 0) {
            List<Long> roleIdList = page.getRecords().stream().map(SysUser::getRoleIds).flatMap(List::stream).toList();
            List<SysRole> roles = roleService.listByIds(roleIdList);
            List<UserRespVO> list = page.getRecords().stream().map(u -> {
                UserRespVO vo = BeanUtil.toBean(u, UserRespVO.class);
                vo.setRoleNames(roles.stream().filter(r -> u.getRoleIds().contains(r.getId()))
                        .map(SysRole::getName)
                        .collect(Collectors.joining(",")));
                return vo;
            }).toList();
            return PageResponseBean.ok(page.getTotal(), list);
        }
        return PageResponseBean.ok(page.getTotal(), null);
    }
}
