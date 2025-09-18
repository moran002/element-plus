package com.moran.controller.dashboard;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.dashboard.workplace.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.moran.conf.bean.ResponseBean.ok;

@RestController
@RequestMapping("/dashboard/workplace")
public class WorkplaceController {

    @GetMapping("/total")
    public ResponseBean<WorkplaceTotalRespVO> total() {
        return ok(new WorkplaceTotalRespVO());
    }

    @GetMapping("/project")
    public ResponseBean<ProjectRespVO> project() {
        return ok(new ProjectRespVO());
    }

    @GetMapping("/dynamic")
    public ResponseBean<List<DynamicRespVO>> dynamic() {
        return ok(List.of(new DynamicRespVO()));
    }

    @GetMapping("/team")
    public ResponseBean<List<TeamRespVO>> team() {
        return ok(List.of(new TeamRespVO()));
    }

    @GetMapping("/radar")
    public ResponseBean<List<RadarDataRespVO>> radar() {
        return ok(List.of(new RadarDataRespVO()));
    }
}
