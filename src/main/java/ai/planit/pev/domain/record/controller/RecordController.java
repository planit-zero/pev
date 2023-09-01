package ai.planit.pev.domain.record.controller;

import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.domain.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/certificate")
    public ResponseEntity<List<CertificateDTO>> getCertificateListByPtNo(@RequestParam("ptNo") String ptNo) {
        return ResponseEntity.ok().body(recordService.getCertificateListByPtNo(ptNo));
    }

    @PostMapping("/detail")
    public ResponseEntity<List<DetailResponseDTO>> getDetailListByCondition(@RequestBody DetailRequestDTO detailRequestDTO) {
        return ResponseEntity.ok().body(recordService.getDetailListByCondition(detailRequestDTO));
    }

    @GetMapping("/info/dept")
    public ResponseEntity<List<DeptInfoDTO>> getDeptInfo() {
        return ResponseEntity.ok().body(recordService.getDeptInfoList());
    }

    @PostMapping("/info/form")
    public ResponseEntity<RecordFormInfoResponseDTO> getRecordFormInfo(@RequestBody RecordFormInfoRequestDTO recordFormInfoRequestDTO) {
        return ResponseEntity.ok().body(recordService.getRecordFormInfo(recordFormInfoRequestDTO));
    }

    @PostMapping("/data/medical")
    public ResponseEntity<List<RecordDataResponseDTO>> getRecordData(@RequestBody RecordDataRequestDTO recordDataRequestDTO) {
        return ResponseEntity.ok().body(recordService.getRecordData(recordDataRequestDTO));
    }
}
