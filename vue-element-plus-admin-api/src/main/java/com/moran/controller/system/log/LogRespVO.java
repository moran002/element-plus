package com.moran.controller.system.log;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogRespVO {
    private Long id;

    /**
     * 类型
     */
    private Integer type;
    private String typeName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 浏览器
     */
    private String userAgent;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 登录结果
     */
    private String result;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
