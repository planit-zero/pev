package ai.planit.pev.domain.ods.function.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.function.FunctionContent;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FunctionDAOImpl implements FunctionDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Record.Response> getFunctionRecordList(String keyId) {
        return sqlSessionTemplate.selectList("getFunctionRecordList", keyId);
    }

    @Override
    public List<FunctionContent> getFunctionContentList(String keyId) {
        return sqlSessionTemplate.selectList("getFunctionContentList", keyId);
    }
}
