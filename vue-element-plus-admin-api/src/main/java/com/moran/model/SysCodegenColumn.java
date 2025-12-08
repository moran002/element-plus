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
 * 代码生成表字段定义
 * </p>
 *
 * @author MyBatis-Plus Generator
 * @since 2025-12-03
 */
@Getter
@Setter
@ToString
@TableName("sys_codegen_column")
public class SysCodegenColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 表编号
     */
    private Long tableId;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段描述
     */
    private String columnComment;

    /**
     * 是否允许为空
     */
    private Boolean nullable;

    /**
     * 是否主键
     */
    private Boolean primaryKey;

    /**
     * 排序
     */
    private Integer ordinalPosition;

    /**
     * Java 属性类型
     */
    private String javaType;

    /**
     * Java 属性名
     */
    private String javaField;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 数据示例
     */
    private String example;

    /**
     * 是否为 Create 创建操作的字段
     */
    private Boolean createOperation;

    /**
     * 是否为 Update 更新操作的字段
     */
    private Boolean updateOperation;

    /**
     * 是否为 List 查询操作的字段
     */
    private Boolean listOperation;

    /**
     * List 查询操作的条件类型
     */
    private String listOperationCondition;

    /**
     * 是否为 List 查询操作的返回字段
     */
    private Boolean listOperationResult;

    /**
     * 显示类型
     */
    private String htmlType;

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
