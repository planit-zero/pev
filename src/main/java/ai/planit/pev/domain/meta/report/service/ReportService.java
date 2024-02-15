package ai.planit.pev.domain.meta.report.service;

import ai.planit.pev.domain.meta.report.dto.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ReportService {
    void insertReport(HttpSession session, ChartReport report);

    void insertChartError(HttpSession session, ChartError chartError);

    List<Report> getReportList(HttpSession session);

    List<ReportDetail> getReportDetailList(int reportId);

    void updateProcess(ReportDetailUpdate reportDetailUpdate);

    List<ChartError> getChartErrorList(HttpSession session);

    void updateChartErrorProcess(int errId);
}
