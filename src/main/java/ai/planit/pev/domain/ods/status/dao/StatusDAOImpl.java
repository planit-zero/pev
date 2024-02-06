package ai.planit.pev.domain.ods.status.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatusDAOImpl implements StatusDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<ChartElement> getNrStatusValueList(String keyId) {
        return sqlSessionTemplate.selectList("getNrStatusValueList", keyId);
    }
}
