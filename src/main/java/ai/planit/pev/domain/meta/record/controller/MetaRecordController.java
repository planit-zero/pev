package ai.planit.pev.domain.meta.record.controller;

import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meta/record")
@RequiredArgsConstructor
public class MetaRecordController {
    private final MetaRecordService metaRecordService;

    @GetMapping("")
    public ResponseEntity<?> getMetaRecordList() {
        return ResponseEntity.ok().body(metaRecordService.getMetaRecordList());
    }
}
