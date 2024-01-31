package ai.planit.pev.domain.meta.report.dao;

import ai.planit.pev.domain.meta.report.dto.ChartError;
import ai.planit.pev.domain.meta.report.dto.ChartReport;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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
}
