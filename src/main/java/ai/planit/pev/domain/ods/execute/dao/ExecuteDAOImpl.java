package ai.planit.pev.domain.ods.execute.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExecuteDAOImpl implements ExecuteDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<ChartElement> getNrExecuteAttributes(String keyId) {
        return sqlSessionTemplate.selectList("getNrExecuteAttributes", keyId);
    }

    @Override
    public List<ChartElement> getNrExecuteValues(String keyId) {
        return sqlSessionTemplate.selectList("getNrExecuteValues", keyId);
    }
}
