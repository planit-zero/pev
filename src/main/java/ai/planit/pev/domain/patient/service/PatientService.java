package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.entity.Patient;
import ai.planit.pev.domain.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient findPatientByPtNo(String ptNo) {
        return patientRepository.findPatientByPtNo(ptNo);
    }


}
