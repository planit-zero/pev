package ai.planit.pev.domain.ods.picture.dao;

import ai.planit.pev.strategy.chart.object.picture.PictureData;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PictureDAOImpl implements PictureDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    /** {@inheritDoc} */
    @Override
    public PictureData.Response getPictureData(PictureData.Request request) {
        return sqlSessionTemplate.selectOne("getPictureData", request);
    }
}
