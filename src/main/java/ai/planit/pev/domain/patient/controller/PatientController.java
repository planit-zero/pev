package ai.planit.pev.domain.patient.controller;

import ai.planit.pev.domain.patient.entity.Patient;
import ai.planit.pev.domain.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("one")
    public ResponseEntity<Patient> getAllPatient(@RequestParam("ptNo") String ptNo) {
        Patient patient = patientService.findPatientByPtNo(ptNo);
        return ResponseEntity.ok().body(patient);
    }
}
