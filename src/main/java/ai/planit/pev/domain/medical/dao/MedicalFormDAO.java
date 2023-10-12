package ai.planit.pev.domain.medical.dao;

import ai.planit.pev.domain.medical.dto.MedicalFormData;
import ai.planit.pev.domain.record.dto.Record;

import java.util.List;

public interface MedicalFormDAO {
    List<MedicalFormData> getMedicalFormData(Record.Response record);
}
