package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.dto.PatientDTO;

public interface PatientService {
    PatientDTO getPatientInfo(String ptNo);
}
