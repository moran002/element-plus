package com.moran.conf.bean;

import cn.hutool.v7.core.text.StrUtil;
import com.moran.model.SysMenu;
import com.moran.model.SysRole;
import com.moran.model.SysUser;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 * @author : moran
 */
@Data
public class UserInfo {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 角色名称
     */
    private String roleNames;
    /**
     * 角色ID
     */
    private List<Long> roleIds;
    /**
     * 菜单
     */
    private List<SysMenu> menus;
    /**
     * api
     */
    private List<String> permissions;

    public static UserInfo convert(SysUser user, List<SysRole> roles, List<SysMenu> menus) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getNickName());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setRoleIds(user.getRoleIds());
        userInfo.setRoleNames(roles.stream().map(SysRole::getName).collect(Collectors.joining(",")));
        userInfo.setMenus(menus);
        userInfo.setPermissions(menus.stream().map(SysMenu::getPermission).filter(StrUtil::isNotBlank).toList());
        return userInfo;
    }
}
