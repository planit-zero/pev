package ai.planit.pev.domain.pathology.dao;

import ai.planit.pev.domain.pathology.dto.PathologyData;
import ai.planit.pev.domain.pathology.dto.PathologyProcess;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PathologyDAOImpl implements PathologyDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    /** {@inheritDoc} */
    @Override
    public PathologyData.Response getPathologyData(PathologyData.Request request) {
        return sqlSessionTemplate.selectOne("getPathologyData", request);
    }

    /** {@inheritDoc} */
    @Override
    public List<PathologyProcess.Response> getPathologyProcessList(PathologyProcess.Request request) {
        return sqlSessionTemplate.selectList("getPathologyProcessList", request);
    }
}
