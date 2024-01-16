package ai.planit.pev.domain.ods.observation.dao;

import ai.planit.pev.strategy.chart.object.observation.ObservationContent;
import ai.planit.pev.strategy.chart.object.observation.ObservationRequest;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ObservationDAOImpl implements ObservationDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<ObservationContent> getObservationContents(ObservationRequest request) {
        return sqlSessionTemplate.selectList("getObservationContents");
    }
}
