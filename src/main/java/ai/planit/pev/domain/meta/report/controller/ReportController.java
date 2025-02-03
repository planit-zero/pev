package ai.planit.pev.domain.meta.report.controller;

import ai.planit.pev.domain.meta.report.dto.*;
import ai.planit.pev.domain.meta.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/meta")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("report/list")
    ResponseEntity<?> getReportList(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getReportList(session));
    }

    @GetMapping("report/detail/list")
    ResponseEntity<?> getReportDetailList(@RequestParam int reportId) {
        return ResponseEntity.ok().body(reportService.getReportDetailList(reportId));
    }

    @PostMapping("report/detail")
    ResponseEntity<?> updateProcess(@RequestBody ReportDetailUpdate reportDetailUpdate) {
        reportService.updateProcess(reportDetailUpdate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("report")
    ResponseEntity<?> insertReport(HttpSession session, @RequestBody ChartReport report) {
        reportService.insertReport(session, report);
        return ResponseEntity.ok().build();
    }

    @PostMapping("report/chart")
    ResponseEntity<?> insertChartError(HttpSession session, @RequestBody ChartError chartError) {
        reportService.insertChartError(session, chartError);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report/chart/list")
    ResponseEntity<?> getChartErrorList(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getChartErrorList(session));
    }

    @PutMapping("report/chart")
    ResponseEntity<?> updateChartErrorProcess(@RequestParam int errId) {
        reportService.updateChartErrorProcess(errId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report/log/user")
    ResponseEntity<?> getLogUser(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getLogUser(session));
    }

    @GetMapping("report/log/event")
    ResponseEntity<?> getLogEvent(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getLogEvent(session));
    }

    @GetMapping("report/statistics/user/month")
    ResponseEntity<?> getStatisticsUserMonth(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getStatisticsUserMonth(session));
    }

    @GetMapping("report/statistics/user/day")
    ResponseEntity<?> getStatisticsUserDay(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getStatisticsUserDay(session));
    }

    @GetMapping("report/statistics/user/hour")
    ResponseEntity<?> getStatisticsUserHour(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getStatisticsUserHour(session));
    }

    @GetMapping("report/statistics/event/search-targets-distribution")
    ResponseEntity<?> getEventSearchTargetDistribution(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getEventSearchTargetDistribution(session));
    }

    @GetMapping("report/statistics/user/dept")
    ResponseEntity<?> getStatisticsUserDept(HttpSession session) {
        return ResponseEntity.ok().body(reportService.getStatisticsUserDept(session));
    }
}
