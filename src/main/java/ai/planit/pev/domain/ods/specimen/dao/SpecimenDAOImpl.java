package ai.planit.pev.domain.ods.specimen.dao;

import ai.planit.pev.strategy.chart.object.specimen.SpecimenInfo;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenRequest;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenResult;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpecimenDAOImpl implements SpecimenDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public SpecimenInfo getSpecimenInfo(SpecimenRequest request) {
        return sqlSessionTemplate.selectOne("getSpecimenInfo", request);
    }

    @Override
    public List<SpecimenResult> getSpecimenResults(SpecimenRequest request) {
        return sqlSessionTemplate.selectList("getSpecimenResults", request);
    }
}
