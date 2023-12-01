package ai.planit.pev.domain.ods.anesthesia.dao;

import ai.planit.pev.domain.ods.anesthesia.dto.AnesthesiaRecordData;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnesthesiaDAOImpl implements AnesthesiaDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<AnesthesiaRecordData> getAnesthesiaRecordDataList(String opExptRegId) {
        return sqlSessionTemplate.selectList("getAnesthesiaRecordDataList", opExptRegId);
    }
}
