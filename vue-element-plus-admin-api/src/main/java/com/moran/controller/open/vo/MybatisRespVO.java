package com.moran.controller.open.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MybatisRespVO {

    @NotNull(message = "请输入表名")
    private List<String> tableNames;
}
