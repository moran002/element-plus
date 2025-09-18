package com.moran.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 树
 */
@Data
public class TreeRespVO {
    /**
     * 属性唯一标识
     */
    private Long value;
    /**
     * 属性值
     */
    private String label;
    /**
     * 子集
     */
    private List<TreeRespVO> children;
}
