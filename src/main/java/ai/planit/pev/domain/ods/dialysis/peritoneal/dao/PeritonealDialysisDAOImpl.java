package ai.planit.pev.domain.ods.dialysis.peritoneal.dao;

import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisInfo;
import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisObservation;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PeritonealDialysisDAOImpl implements PeritonealDialysisDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public PeritonealDialysisInfo getNrPeritonealDialysisInfo(String keyId) {
        return sqlSessionTemplate.selectOne("getNrPeritonealDialysisInfo", keyId);
    }

    @Override
    public List<PeritonealDialysisObservation> getNrPeritonealDialysisObservationList(String keyId) {
        return sqlSessionTemplate.selectList("getNrPeritonealDialysisObservationList", keyId);
    }
}
