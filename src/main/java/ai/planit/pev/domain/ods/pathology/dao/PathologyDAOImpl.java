package ai.planit.pev.domain.ods.pathology.dao;

import ai.planit.pev.strategy.chart.object.pathology.PathologyContent;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.pathology.PathologyProcess;
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
    public PathologyContent getPathologyData(PathologyData.Request request) {
        return sqlSessionTemplate.selectOne("getPathologyData", request);
    }

    /** {@inheritDoc} */
    @Override
    public List<PathologyProcess> getPathologyProcessList(PathologyData.Request request) {
        return sqlSessionTemplate.selectList("getPathologyProcessList", request);
    }
}
