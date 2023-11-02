package ai.planit.pev.domain.ods.specimen.dao;

import ai.planit.pev.domain.ods.specimen.dto.SpecimenData;
import ai.planit.pev.domain.ods.specimen.dto.SpecimenHeaderData;
import ai.planit.pev.domain.ods.specimen.dto.SpecimenInfo;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpecimenDAOImpl implements SpecimenDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    /** {@inheritDoc} */
    @Override
    public SpecimenInfo.Response getSpecimenInfo(SpecimenInfo.Request request) {
        return sqlSessionTemplate.selectOne("getSpecimenInfo", request);
    }

    /** {@inheritDoc} */
    @Override
    public List<SpecimenData.Response> getSpecimenData(SpecimenData.Request request) {
        return sqlSessionTemplate.selectList("getSpecimenData", request);
    }

    @Override
    public SpecimenHeaderData getSpecimenHeaderData(String spcmNo) {
        return sqlSessionTemplate.selectOne("getSpecimenHeaderData", spcmNo);
    }
}
