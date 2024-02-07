package ai.planit.pev.domain.ods.dialysis.peritoneal.dao;

import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisInfo;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PeritonealDialysisDAOImpl implements PeritonealDialysisDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public PeritonealDialysisInfo getNrPeritonealDialysisInfo(String keyId) {
        return sqlSessionTemplate.selectOne("getNrPeritonealDialysisInfo", keyId);
    }
}
