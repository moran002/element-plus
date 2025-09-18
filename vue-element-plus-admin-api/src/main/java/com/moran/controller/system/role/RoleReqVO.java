package com.moran.controller.system.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleReqVO {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "请填写角色名称")
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单ID集合
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> menuIds;
}
