package ai.planit.pev.domain.meta.report.dao;

import ai.planit.pev.domain.meta.report.dto.ChartReport;

public interface ReportDAO {
    void insertReport(ChartReport report);

    void insertReportDetail(ChartReport report);
}
