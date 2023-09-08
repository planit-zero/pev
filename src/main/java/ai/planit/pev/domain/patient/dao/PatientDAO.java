package ai.planit.pev.domain.patient.dao;

import ai.planit.pev.domain.patient.dto.Patient;

public interface PatientDAO {
    Patient getPatient(String ptNo);
}
