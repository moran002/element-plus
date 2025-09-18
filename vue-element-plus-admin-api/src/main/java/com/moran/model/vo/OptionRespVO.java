package com.moran.model.vo;

import lombok.Data;

@Data
public class OptionRespVO {
    /**
     * 名字
     */
    private String label;
    /**
     * 主键
     */
    private Long value;

    public static OptionRespVO convert(Long value, String label) {
        OptionRespVO vo = new OptionRespVO();
        vo.setLabel(label);
        vo.setValue(value);
        return vo;
    }
}
