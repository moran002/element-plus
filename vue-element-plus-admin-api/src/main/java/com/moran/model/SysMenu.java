package com.moran.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
@Getter
@Setter
@ToString
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 目录
     */
    private String path;

    /**
     * 路由地址
     */
    private String component;

    /**
     * 当设置 noredirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    private String name;

    /**
     * 当设置 true 的时候该路由不会再侧边栏出现 如404，login等页面(默认 false)
     */
    private Boolean hidden;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式，	                              只有一个时，会将那个子路由当做根路由显示在侧边栏，	                              若你想不管路由下面的 children 声明的个数都显示你的根路由，	                              你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，	                              一直显示根路由(默认 false)
     */
    private Boolean alwaysShow;

    /**
     * 设置该路由的图标
     */
    private String icon;

    /**
     * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
     */
    private Boolean noCache;

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
     */
    private Boolean breadcrumb;

    /**
     * 如果设置为true，则会一直固定在tag项中(默认 false)
     */
    private Boolean affix;

    /**
     * 如果设置为true，则不会出现在tag中(默认 false)
     */
    private Boolean noTagsView;

    /**
     * 显示高亮的路由路径
     */
    private String activeMenu;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型:1:菜单,2:按钮
     */
    private Integer type;

    /**
     * 权限
     */
    private String permission;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
