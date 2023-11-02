package ai.planit.pev.domain.ods.hospital.controller;

import ai.planit.pev.domain.ods.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("dept")
    public ResponseEntity<?> getDepartmentList() {
        return ResponseEntity.ok().body(hospitalService.getDepartmentList());
    }
}
