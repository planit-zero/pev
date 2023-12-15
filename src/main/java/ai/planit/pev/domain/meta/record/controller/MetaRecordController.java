package ai.planit.pev.domain.meta.record.controller;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meta/record")
@RequiredArgsConstructor
public class MetaRecordController {
    private final MetaRecordService metaRecordService;
    private final MedicalService medicalService;

    @GetMapping("")
    public ResponseEntity<?> getMetaRecordList() {
        return ResponseEntity.ok().body(metaRecordService.getMetaRecordList());
    }

    @PostMapping("reload")
    public ResponseEntity<?> reloadMedicalRecordFormat(@RequestBody MetaRecordFormat.Request request) {
        List<MetaRecordFormat.Response> metaRecordFormatList = medicalService.getMedicalRecordFormatList(request);
        metaRecordService.reloadMedicalRecordFormat(metaRecordFormatList);
        return ResponseEntity.ok().build();
    }
}
