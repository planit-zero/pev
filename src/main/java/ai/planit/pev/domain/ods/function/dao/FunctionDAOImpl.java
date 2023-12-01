package ai.planit.pev.domain.ods.function.dao;

import ai.planit.pev.domain.ods.function.dto.FunctionData;
import ai.planit.pev.domain.ods.function.dto.FunctionDecodeMaster;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FunctionDAOImpl implements FunctionDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<FunctionDecodeMaster> getFunctionDecodeMasterList(String examKey) {
        return sqlSessionTemplate.selectList("getFunctionDecodeMasterList", examKey);
    }

    @Override
    public List<FunctionData> getFunctionData(FunctionDecodeMaster master) {
        return sqlSessionTemplate.selectList("getFunctionData", master);
    }
}
