package ai.planit.pev.domain.ods.patient.controller;

import ai.planit.pev.domain.ods.patient.dto.RidByGid;
import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.PatientByIrb;
import ai.planit.pev.domain.ods.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

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
