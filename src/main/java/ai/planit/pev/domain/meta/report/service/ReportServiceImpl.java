package ai.planit.pev.domain.meta.report.service;

import ai.planit.pev.domain.meta.report.dao.ReportDAO;
import ai.planit.pev.domain.meta.report.dto.ChartError;
import ai.planit.pev.domain.meta.report.dto.ChartReport;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    @Override
    public void insertReport(ChartReport report) {
        reportDAO.insertReport(report);
        reportDAO.insertReportDetail(report);
    }

    @Override
    public void insertChartError(HttpSession session, ChartError chartError) {
        String pid = (String) session.getAttribute("pev-pid");

        if (PevStringUtil.isStringEmpty(pid)) {
            chartError.setPid("UNKNOWN");
        } else {
            chartError.setPid(pid);
        }

        reportDAO.insertChartError(chartError);
    }
}
