package ai.planit.pev.domain.patient.repository;

import ai.planit.pev.domain.patient.entity.Patient;

public interface PatientRepository {
    Patient findPatientByPtNo(String ptNo);
}
