package ai.planit.pev.domain.patient.dao;

import ai.planit.pev.domain.patient.dto.PatientDTO;

public interface PatientDAO {
    PatientDTO getPatientInfo(String ptNo);
}
