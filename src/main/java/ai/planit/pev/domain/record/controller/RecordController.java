package ai.planit.pev.domain.record.controller;

import ai.planit.pev.domain.record.dto.Certificate;
import ai.planit.pev.domain.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/certificate")
    public ResponseEntity<List<Certificate>> findCertificateByPtNo(@RequestParam("ptNo") String ptNo) {
        return ResponseEntity.ok(recordService.findCertificateByPtNo(ptNo));
    }
}
