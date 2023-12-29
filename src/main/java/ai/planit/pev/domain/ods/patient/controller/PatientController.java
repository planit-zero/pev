package ai.planit.pev.domain.ods.patient.controller;

import ai.planit.pev.domain.ods.patient.dto.RidByGid;
import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.PatientByIrb;
import ai.planit.pev.domain.ods.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("pid")
    public ResponseEntity<?> getPatientNumber(HttpSession session) {
        Map<String, String> map = new HashMap<>();

        String pid = "";

        if (session.getAttribute("pev-pid") != null) {
            pid = session.getAttribute("pev-pid").toString();
        }

        map.put("pid", pid);

        return ResponseEntity.ok().body(map);
    }

    @PostMapping("rid")
    public ResponseEntity<?> getPatient(HttpSession session, @RequestBody IdentifiedPatient.Request request) {
        return ResponseEntity.ok().body(patientService.getPatient(session, request));
    }

    @PostMapping("gid")
    public ResponseEntity<?> getRidByGid(@RequestBody RidByGid.Request request) {
        return ResponseEntity.ok().body(patientService.getRidByGid(request));
    }

    @PostMapping("list")
    public ResponseEntity<?> getPatientList(@RequestBody PatientByIrb.Request request) {
        return ResponseEntity.ok().body(patientService.getPatientList(request));
    }
}
