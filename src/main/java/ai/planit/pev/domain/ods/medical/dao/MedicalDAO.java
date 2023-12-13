package ai.planit.pev.domain.ods.medical.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;

import java.util.List;

public interface MedicalDAO {
    List<MedicalData> getMedicalData(Record.Response record);
}
