package ai.planit.pev.domain.ods.patient.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.patient.dao.PatientDAO;
import ai.planit.pev.domain.ods.patient.dto.EncryptedPatient;
import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.Patient;
import ai.planit.pev.domain.ods.patient.dto.PatientByIrb;
import ai.planit.pev.domain.ods.patient.dto.RidByGid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@org.springframework.context.annotation.Profile("dev")
public class DevPatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    @Override
    public Patient getPatient(HttpSession session, IdentifiedPatient.Request request) {
        // Mock implementation for dev environment
        // Extract RID and convert to PT_NO directly (simplified - just use first patient)
        String pid = convertRidToPid(request);

        Patient patient = patientDAO.getPatient(pid);
        if (patient == null) throw new BaseException(ErrorType.PATIENT_NOT_FOUND);

        session.setAttribute("pev-pid", pid);
        session.setAttribute("pev-irb", request.getIrb());
        session.setAttribute("pev-rid", request.getRidList().get(0));

        return patient;
    }

    private String convertRidToPid(IdentifiedPatient.Request request) {
        // Mock implementation for dev environment
        // Simple mapping: RID-xxx-000y -> 0000000y
        String rid = request.getRidList().get(0);
        
        // Extract patient number from RID (e.g., RID-001-0001 -> 00000001)
        if (rid != null && rid.matches("RID-\\d{3}-\\d{4}")) {
            return rid.substring(8); // Extract last 4 digits (0001 -> 00000001)
        }
        
        // Default fallback
        return "00000001";
    }

    @Override
    public RidByGid.Response getRidByGid(RidByGid.Request request) {
        // Mock implementation for dev environment
        RidByGid.Response response = new RidByGid.Response();
        response.setIrbNo(request.getIrbNo());
        
        // Process each EncryptedPatient in the request
        List<EncryptedPatient> responseData = new ArrayList<>();
        
        if (request.getData() != null && !request.getData().isEmpty()) {
            for (EncryptedPatient encryptedPatient : request.getData()) {
                EncryptedPatient responsePatient = new EncryptedPatient();
                
                String gid = encryptedPatient.getGid();
                
                // Create mock RID from GID
                // Simple mock: extract last 4 digits from GID and create RID
                if (gid != null && gid.length() >= 4) {
                    String suffix = gid.substring(gid.length() - 4);
                    responsePatient.setRid("RID-001-" + suffix);
                } else {
                    responsePatient.setRid("RID-001-0001");
                }
                
                responseData.add(responsePatient);
            }
        }
        
        response.setData(responseData);
        
        return response;
    }

    @Override
    public List<PatientByIrb.Response> getPatientList(PatientByIrb.Request request) {
        // Mock implementation for dev environment
        // Return mock patient list based on IRB number
        List<PatientByIrb.Response> patientList = getPatientListByIrb(request);

        return patientList.stream()
                .sorted(Comparator.comparing(PatientByIrb.Response::getId))
                .collect(Collectors.toList());
    }

    private List<PatientByIrb.Response> getPatientListByIrb(PatientByIrb.Request request) {
        // Mock implementation for dev environment
        // Return mock data based on IRB number
        List<PatientByIrb.Response> mockPatients = new ArrayList<>();
        
        String irb = request.getIrb();
        
        // Mock data for different IRB numbers
        if ("IRB-2024-001".equals(irb)) {
            // 5 patients
            for (int i = 1; i <= 5; i++) {
                PatientByIrb.Response patient = new PatientByIrb.Response();
                patient.setId(String.format("RID-001-%04d", i));
                patient.setName(String.format("테스트환자%d", i));
                patient.setDob(getMockDob(i));
                mockPatients.add(patient);
            }
        } else if ("IRB-2024-002".equals(irb)) {
            // 3 patients
            mockPatients.add(createMockPatient("RID-002-0001", "테스트환자1", "19800101"));
            mockPatients.add(createMockPatient("RID-002-0002", "테스트환자3", "19750320"));
            mockPatients.add(createMockPatient("RID-002-0003", "테스트환자5", "20001105"));
        } else if ("IRB-2024-003".equals(irb)) {
            // 2 patients
            mockPatients.add(createMockPatient("RID-003-0001", "테스트환자2", "19900515"));
            mockPatients.add(createMockPatient("RID-003-0002", "테스트환자4", "19951208"));
        } else if ("IRB-2024-101".equals(irb)) {
            // 5 patients
            for (int i = 1; i <= 5; i++) {
                PatientByIrb.Response patient = new PatientByIrb.Response();
                patient.setId(String.format("RID-101-%04d", i));
                patient.setName(String.format("테스트환자%d", i));
                patient.setDob(getMockDob(i));
                mockPatients.add(patient);
            }
        } else if ("IRB-2024-102".equals(irb)) {
            // 4 patients
            for (int i = 1; i <= 4; i++) {
                PatientByIrb.Response patient = new PatientByIrb.Response();
                patient.setId(String.format("RID-102-%04d", i));
                patient.setName(String.format("테스트환자%d", i));
                patient.setDob(getMockDob(i));
                mockPatients.add(patient);
            }
        } else {
            // Default: return empty or sample data
            mockPatients.add(createMockPatient("RID-999-0001", "테스트환자1", "19800101"));
        }
        
        return mockPatients;
    }
    
    private PatientByIrb.Response createMockPatient(String id, String name, String dob) {
        PatientByIrb.Response patient = new PatientByIrb.Response();
        patient.setId(id);
        patient.setName(name);
        patient.setDob(dob);
        return patient;
    }
    
    private String getMockDob(int index) {
        String[] dobs = {"19800101", "19900515", "19750320", "19951208", "20001105"};
        return dobs[(index - 1) % dobs.length];
    }
}
