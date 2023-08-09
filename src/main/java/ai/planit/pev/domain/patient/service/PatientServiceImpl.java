package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.dto.PatientDTO;
import ai.planit.pev.domain.patient.dao.PatientDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    @Override
    public PatientDTO getPatientInfo(String ptNo) {
        PatientDTO patientDTO = patientDAO.getPatientInfo(ptNo);
        // TODO: 사영자 정보 가명화 처리 로직 필요
        return patientDTO;
    }
}
