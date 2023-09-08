package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.dto.Patient;

public interface PatientService {
    Patient getPatient(String ptNo);
}
