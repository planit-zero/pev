package ai.planit.pev.domain.ods.medical.dao;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicalDAOImpl implements MedicalDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<MedicalData> getMedicalData(Record.Response record) {
        return sqlSessionTemplate.selectList("getMedicalData", record);
    }

    @Override
    public List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request) {
        return sqlSessionTemplate.selectList("getMedicalRecordFormatList", request);
    }
}
