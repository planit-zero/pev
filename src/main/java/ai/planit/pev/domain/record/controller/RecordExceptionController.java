package ai.planit.pev.domain.record.controller;

import ai.planit.pev.domain.record.dto.RecordExceptionRequestDTO;
import ai.planit.pev.domain.record.dto.SurgeryDefaultValueDTO;
import ai.planit.pev.domain.record.service.RecordExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record/exception")
public class RecordExceptionController {
    private final RecordExceptionService recordExceptionService;

    @PostMapping("surgery")
    public ResponseEntity<List<SurgeryDefaultValueDTO>> getSurgeryDefaultValueList(@RequestBody RecordExceptionRequestDTO recordExceptionRequestDTO) {
        return ResponseEntity.ok().body(recordExceptionService.getSurgeryDefaultValueList(recordExceptionRequestDTO));
    }
}
