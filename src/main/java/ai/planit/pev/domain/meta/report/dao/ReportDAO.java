package ai.planit.pev.domain.meta.report.dao;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.meta.report.dto.*;

import java.util.List;

public interface ReportDAO {
    void insertReport(ChartReport report);

    void insertReportDetail(ChartReport report);

    void insertChartError(ChartError chartError);

    List<Report> getReportList(IdpLoginUser idpLoginUser);

    List<ReportDetail> getReportDetailList(int reportId);

    void updateProcess(ReportDetailUpdate reportDetailUpdate);

    List<ChartError> getChartErrorList(ChartErrorRequest request);

    void updateChartErrorProcess(int errId);
}
