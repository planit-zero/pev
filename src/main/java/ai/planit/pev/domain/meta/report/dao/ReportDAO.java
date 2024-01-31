package ai.planit.pev.domain.meta.report.dao;

import ai.planit.pev.domain.meta.report.dto.ChartError;
import ai.planit.pev.domain.meta.report.dto.ChartReport;

public interface ReportDAO {
    void insertReport(ChartReport report);

    void insertReportDetail(ChartReport report);

    void insertChartError(ChartError chartError);
}
