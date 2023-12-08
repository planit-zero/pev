package ai.planit.pev.domain.meta.report.service;

import ai.planit.pev.domain.meta.report.dao.ReportDAO;
import ai.planit.pev.domain.meta.report.dto.ChartReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    @Override
    public void insertReport(ChartReport report) {
        reportDAO.insertReport(report);
        reportDAO.insertReportDetail(report);
    }
}
