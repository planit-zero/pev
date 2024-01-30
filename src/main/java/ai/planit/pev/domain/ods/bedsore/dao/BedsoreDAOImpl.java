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

    @Override
    public List<String> getBedsoreEvaluationDetailTextList(String keyId) {
        return sqlSessionTemplate.selectList("getBedsoreEvaluationDetailTextList", keyId);
    }

    @Override
    public String getBedsoreEvaluationTotalText(String keyId) {
        return sqlSessionTemplate.selectOne("getBedsoreEvaluationTotalText", keyId);
    }

    @Override
    public List<String> getBedsoreEvaluationPreventTextList(String keyId) {
        return sqlSessionTemplate.selectList("getBedsoreEvaluationPreventTextList", keyId);
    }

    @Override
    public String getBedsoreEvaluationYnText(String keyId) {
        return sqlSessionTemplate.selectOne("getBedsoreEvaluationYnText", keyId);
    }

    @Override
    public String getBedsoreEvaluationWriterText(String keyId) {
        return sqlSessionTemplate.selectOne("getBedsoreEvaluationWriterText", keyId);
    }
}
