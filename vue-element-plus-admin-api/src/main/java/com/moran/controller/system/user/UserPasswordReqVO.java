package com.moran.controller.system.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPasswordReqVO {
    /**
     * 用户ID
     */
    @NotNull(message = "请选择用户")
    private Long id;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
