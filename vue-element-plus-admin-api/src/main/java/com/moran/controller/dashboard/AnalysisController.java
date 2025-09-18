package com.moran.controller.dashboard;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.dashboard.analysis.AnalysisTotalRespVO;
import com.moran.controller.dashboard.analysis.MonthlySalesRespVO;
import com.moran.controller.dashboard.analysis.UserAccessSourceRespVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.moran.conf.bean.ResponseBean.ok;

@RestController
@RequestMapping("/dashboard/analysis")
@AllArgsConstructor
public class AnalysisController {

    @GetMapping("/total")
    public ResponseBean<AnalysisTotalRespVO> workplaceTotal() {
        return ok(new AnalysisTotalRespVO());
    }

    @GetMapping("/userAccessSource")
    public ResponseBean<UserAccessSourceRespVO> userAccessSource() {
        return ok(new UserAccessSourceRespVO());
    }

    @GetMapping("/weeklyUserActivity")
    public ResponseBean<UserAccessSourceRespVO> weeklyUserActivity() {
        return ok(new UserAccessSourceRespVO());
    }

    @GetMapping("/monthlySales")
    public ResponseBean<MonthlySalesRespVO> monthlySales() {
        return ok(new MonthlySalesRespVO());
    }
}
