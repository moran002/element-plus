package com.moran.controller.system.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuTypeEnum {
    MENU(1),
    BUTTON(2);

    private final int type;
}
