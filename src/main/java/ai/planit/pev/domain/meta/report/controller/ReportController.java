package ai.planit.pev.domain.meta.report.controller;

import ai.planit.pev.domain.meta.report.dto.ChartError;
import ai.planit.pev.domain.meta.report.dto.ChartReport;
import ai.planit.pev.domain.meta.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/meta")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("report")
    ResponseEntity<?> insertReport(@RequestBody ChartReport report) {
        reportService.insertReport(report);
        return ResponseEntity.ok().build();
    }

    @PostMapping("report/chart")
    ResponseEntity<?> insertChartError(HttpSession session, @RequestBody ChartError chartError) {
        reportService.insertChartError(session, chartError);
        return ResponseEntity.ok().build();
    }
}
