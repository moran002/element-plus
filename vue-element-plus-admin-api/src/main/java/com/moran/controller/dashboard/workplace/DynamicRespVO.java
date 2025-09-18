package com.moran.controller.dashboard.workplace;

import lombok.Data;

import java.util.List;

@Data
public class DynamicRespVO {
    private List<String> keys = List.of("key1", "key2", "key3", "key4", "key5", "key6");
    private String time = "time";

}
