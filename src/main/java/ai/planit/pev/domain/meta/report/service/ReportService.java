package ai.planit.pev.domain.meta.report.service;

import ai.planit.pev.domain.meta.report.dto.ChartError;
import ai.planit.pev.domain.meta.report.dto.ChartReport;

import javax.servlet.http.HttpSession;

public interface ReportService {
    void insertReport(ChartReport report);

    void insertChartError(HttpSession session, ChartError chartError);
}
