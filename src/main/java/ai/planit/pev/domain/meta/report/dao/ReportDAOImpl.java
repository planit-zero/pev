package ai.planit.pev.domain.meta.report.dao;

import ai.planit.pev.domain.meta.report.dto.*;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportDAOImpl implements ReportDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertReport(ChartReport report) {
        sqlSessionTemplate.insert("insertReport", report);
    }

    @Override
    public void insertReportDetail(ChartReport report) {
        sqlSessionTemplate.insert("insertReportDetail", report);
    }

    @Override
    public void insertChartError(ChartError chartError) {
        sqlSessionTemplate.insert("insertChartError", chartError);
    }

    @Override
    public List<Report> getReportList(ReportRequest request) {
        return sqlSessionTemplate.selectList("getReportList", request);
    }

    @Override
    public List<ReportDetail> getReportDetailList(int reportId) {
        return sqlSessionTemplate.selectList("getReportDetailList", reportId);
    }

    @Override
    public void updateProcess(ReportDetailUpdate reportDetailUpdate) {
        sqlSessionTemplate.update("updateProcess", reportDetailUpdate);
    }

    @Override
    public List<ChartError> getChartErrorList(ChartErrorRequest request) {
        return sqlSessionTemplate.selectList("getChartErrorList", request);
    }

    @Override
    public void updateChartErrorProcess(int errId) {
        sqlSessionTemplate.update("updateChartErrorProcess", errId);
    }
}
