package ai.planit.pev.domain.ods.dialysis.blood.dao;

import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisInfo;
import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisObservation;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BloodDialysisDAOImpl implements BloodDialysisDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public BloodDialysisInfo getNrBloodDialysisInfo(String keyId) {
        return sqlSessionTemplate.selectOne("getNrBloodDialysisInfo", keyId);
    }

    @Override
    public List<BloodDialysisObservation> getNrBloodDialysisObservationList(String keyId) {
        return sqlSessionTemplate.selectList("getNrBloodDialysisObservationList", keyId);
    }
}
