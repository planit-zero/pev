package ai.planit.pev.domain.meta.record.dao;

import ai.planit.pev.domain.meta.record.dto.MetaRecord;
import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MetaRecordDAOImpl implements MetaRecordDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<MetaRecord> getMetaRecordList() {
        return sqlSessionTemplate.selectList("getMetaRecordList");
    }

    @Override
    public List<ChartElement> getRecordFormatList(Record.Response record) {
        return sqlSessionTemplate.selectList("getRecordFormatList", record);
    }

    @Override
    public void reloadMedicalRecordFormat(List<MetaRecordFormat.Response> metaRecordFormatList) {
        sqlSessionTemplate.insert("reloadMedicalRecordFormat", metaRecordFormatList);
    }
}
