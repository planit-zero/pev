package ai.planit.pev.domain.ods.bedsore.dao;

import ai.planit.pev.strategy.chart.object.bedsore.BedsoreContent;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BedsoreDAOImpl implements BedsoreDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<BedsoreContent> getBedsoreContents(String keyId) {
        return sqlSessionTemplate.selectList("getBedsoreContents", keyId);
    }
}
