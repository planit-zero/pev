package ai.planit.pev.domain.specimen.dao;

import ai.planit.pev.domain.specimen.dto.SpecimenData;
import ai.planit.pev.domain.specimen.dto.SpecimenInfo;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpecimenDAOImpl implements SpecimenDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public SpecimenInfo.Response getSpecimenInfo(SpecimenInfo.Request request) {
        return sqlSessionTemplate.selectOne("getSpecimenInfo", request);
    }

    @Override
    public List<SpecimenData.Response> getSpecimenData(SpecimenData.Request request) {
        return sqlSessionTemplate.selectList("getSpecimenData", request);
    }
}
