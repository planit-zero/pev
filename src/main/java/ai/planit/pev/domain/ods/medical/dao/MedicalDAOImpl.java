package ai.planit.pev.domain.ods.medical.dao;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.medical.dto.MedicalImage;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartStyleXml;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
import ai.planit.pev.strategy.chart.object.medical.SurgeryData;
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
    public List<SurgeryData> getSurgeryData(Record.Response record) {
        return sqlSessionTemplate.selectList("getSurgeryData", record);
    }

    @Override
    public List<String> getMedicalImageData(MedicalImage.Request request) {
        return sqlSessionTemplate.selectList("getMedicalImageData", request);
    }

    @Override
    public List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request) {
        return sqlSessionTemplate.selectList("getMedicalRecordFormatList", request);
    }

    @Override
    public List<ChartStyleXml.Response> getChartStyleList(ChartStyleXml.Request request) {
        return sqlSessionTemplate.selectList("getChartStyleList", request);
    }

    @Override
    public Record.Response getMedicalReplyRecord(MedicalReply.Request request) {
        return sqlSessionTemplate.selectOne("getMedicalReplyRecord", request);
    }
}
