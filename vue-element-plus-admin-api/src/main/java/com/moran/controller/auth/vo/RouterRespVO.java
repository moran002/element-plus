package com.moran.controller.auth.vo;

import lombok.Data;

import java.util.List;

@Data
public class RouterRespVO {

    private String name;

    private String path;

    private String component;

    private String redirect;

    private Meta meta;

    private List<RouterRespVO> children;

    @Data
    public static class Meta {

        private String title;

        private Boolean hidden;

        private Boolean alwaysShow;

        private String icon;

        private Boolean noCache;

        private Boolean breadcrumb;

        private Boolean affix;

        private Boolean noTagsView;

        private String activeMenu;

        private List<String> permission;
    }
}
