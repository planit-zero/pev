package ai.planit.pev.domain.ods.record.controller;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("list")
    public ResponseEntity<?> getRecordList(HttpSession session, @RequestBody Record.Request request) {
        return ResponseEntity.ok().body(recordService.getRecordList(session, request));
    }

    @PostMapping("sheet")
    public ResponseEntity<?> getRecordSheet(HttpSession session, @RequestBody Record.Response record) {
        return ResponseEntity.ok().body(recordService.getRecordSheet(session, record));
    }
}
