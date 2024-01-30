package ai.planit.pev.domain.ods.discharge.dao;

import ai.planit.pev.strategy.chart.object.discharge.DischargeContent;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DischargeDAOImpl implements DischargeDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<DischargeContent> getNrDischargeContents(String keyId) {
        return sqlSessionTemplate.selectList("getNrDischargeContents", keyId);
    }

    @Override
    public String getNrDischargeWriterText(String keyId) {
        return sqlSessionTemplate.selectOne("getNrDischargeWriterText", keyId);
    }
}
