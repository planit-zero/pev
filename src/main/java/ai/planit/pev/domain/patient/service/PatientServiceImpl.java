package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.dto.Patient;
import ai.planit.pev.domain.patient.dao.PatientDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    @Override
    public Patient getPatient(String ptNo) {
        return patientDAO.getPatient(ptNo);
    }
}
