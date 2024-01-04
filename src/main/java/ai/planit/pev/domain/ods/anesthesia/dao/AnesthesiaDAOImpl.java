package ai.planit.pev.domain.ods.anesthesia.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatRequest;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatValue;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordHistory;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnesthesiaDAOImpl implements AnesthesiaDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Record.Response getAnesthesiaRecord(String opExptRegId) {
        return sqlSessionTemplate.selectOne("getAnesthesiaFormatInfo", opExptRegId);
    }

    @Override
    public List<AnesthesiaRecordHistory> getAnesthesiaRecordHistories(String opExptRegId) {
        return sqlSessionTemplate.selectList("getAnesthesiaRecordHistories", opExptRegId);
    }

    @Override
    public List<AnesthesiaFormatValue> getAnesthesiaFormatValue(AnesthesiaFormatRequest request) {
        return sqlSessionTemplate.selectList("getAnesthesiaFormatValue", request);
    }
}
