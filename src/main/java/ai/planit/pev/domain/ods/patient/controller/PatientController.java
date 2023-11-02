package ai.planit.pev.domain.ods.patient.controller;

import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
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

    @PostMapping("gid")
    public ResponseEntity<?> getPatient(HttpSession session, @RequestBody IdentifiedPatient.Request request) {
        return ResponseEntity.ok().body(patientService.getPatient(session, request));
    }
}
