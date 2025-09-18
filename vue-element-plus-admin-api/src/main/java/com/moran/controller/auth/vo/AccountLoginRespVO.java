package com.moran.controller.auth.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * 账号登录
 */
@Data
public class AccountLoginRespVO {
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
