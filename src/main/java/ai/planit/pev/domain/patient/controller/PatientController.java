package ai.planit.pev.domain.patient.controller;

import ai.planit.pev.domain.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("")
    public ResponseEntity<?> getPatient(HttpSession session, @RequestParam String gid) {
        return ResponseEntity.ok().body(patientService.getPatient(session, gid));
    }
}
