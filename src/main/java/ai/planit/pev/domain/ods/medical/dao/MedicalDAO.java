package ai.planit.pev.domain.ods.medical.dao;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;

import java.util.List;

public interface MedicalDAO {
    List<MedicalData> getMedicalData(Record.Response record);
    List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request);
}
