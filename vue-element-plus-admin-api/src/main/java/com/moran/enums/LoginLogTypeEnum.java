package com.moran.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginLogTypeEnum {
    LOGIN(1, "登入"),
    LOGOUT(2, "登出");

    private final int value;
    private final String desc;

    public static String getTypeName(Integer type) {
        for (LoginLogTypeEnum loginLogTypeEnum : LoginLogTypeEnum.values()) {
            if (loginLogTypeEnum.getValue() == type) {
                return loginLogTypeEnum.getDesc();
            }
        }
        return null;
    }
}
