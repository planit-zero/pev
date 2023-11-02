package ai.planit.pev.domain.ods.patient.dao;

import ai.planit.pev.domain.ods.patient.dto.Patient;

public interface PatientDAO {
    Patient getPatient(String pid);
}
