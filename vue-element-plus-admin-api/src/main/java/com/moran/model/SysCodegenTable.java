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
 * 代码生成表定义
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-12-03
 */
@Getter
@Setter
@ToString
@TableName("sys_codegen_table")
public class SysCodegenTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据源配置的编号
     */
    private Long dataSourceConfigId;

    /**
     * 生成场景
     */
    private Boolean scene;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 备注
     */
    private String remark;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名
     */
    private String businessName;

    /**
     * 类名称
     */
    private String className;

    /**
     * 类描述
     */
    private String classComment;

    /**
     * 作者
     */
    private String author;

    /**
     * 模板类型
     */
    private Boolean templateType;

    /**
     * 前端类型
     */
    private Boolean frontType;

    /**
     * 父菜单编号
     */
    private Long parentMenuId;

    /**
     * 主表的编号
     */
    private Long masterTableId;

    /**
     * 子表关联主表的字段编号
     */
    private Long subJoinColumnId;

    /**
     * 主表与子表是否一对多
     */
    private Boolean subJoinMany;

    /**
     * 树表的父字段编号
     */
    private Long treeParentColumnId;

    /**
     * 树表的名字字段编号
     */
    private Long treeNameColumnId;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
