package ai.planit.pev.domain.ods.dialysis.blood.dao;

import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisInfo;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BloodDialysisDAOImpl implements BloodDialysisDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public BloodDialysisInfo getNrBloodDialysisInfo(String keyId) {
        return sqlSessionTemplate.selectOne("getNrBloodDialysisInfo", keyId);
    }
}
