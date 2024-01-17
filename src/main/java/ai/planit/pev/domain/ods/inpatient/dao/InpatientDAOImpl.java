package ai.planit.pev.domain.ods.inpatient.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InpatientDAOImpl implements InpatientDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<ChartElement> getNrInpatientEntities(String keyId) {
        return sqlSessionTemplate.selectList("getNrInpatientEntities", keyId);
    }

    @Override
    public List<ChartElement> getNrInpatientAttributes(String keyId) {
        return sqlSessionTemplate.selectList("getNrInpatientAttributes", keyId);
    }

    @Override
    public List<ChartElement> getNrInpatientValues(String keyId) {
        return sqlSessionTemplate.selectList("getNrInpatientValues", keyId);
    }
}
