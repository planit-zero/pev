package ai.planit.pev.domain.ods.fall.dao;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FallDAOImpl implements FallDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<String> getFallDetailTextList(String keyId) {
        return sqlSessionTemplate.selectList("getFallDetailTextList", keyId);
    }

    @Override
    public String getFallTotalText(String keyId) {
        return sqlSessionTemplate.selectOne("getFallTotalText", keyId);
    }

    @Override
    public String getFallWriterNm(String keyId) {
        return sqlSessionTemplate.selectOne("getFallWriterNm", keyId);
    }
}
