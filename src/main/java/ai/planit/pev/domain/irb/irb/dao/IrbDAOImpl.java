package ai.planit.pev.domain.irb.irb.dao;

import ai.planit.pev.domain.irb.irb.dto.Irb;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IrbDAOImpl implements IrbDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Irb> getIrbList(String stfNo) {
        return sqlSessionTemplate.selectList("getIrbList", stfNo);
    }
}
