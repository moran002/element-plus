package com.moran.controller.system.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserReqVO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String account;

    /**
     * 用户昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色ID集合
     */
    private List<Long> roleIds;

    /**
     * 状态
     */
    private Boolean status;
}
