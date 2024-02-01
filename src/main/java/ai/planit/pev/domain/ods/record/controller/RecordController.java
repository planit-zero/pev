package ai.planit.pev.domain.ods.record.controller;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.service.RecordService;
import ai.planit.pev.strategy.chart.object.common.Chart;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
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

    @PostMapping("chart")
    public ResponseEntity<?> getChart(HttpSession session, @RequestBody Chart.Request request) {
        return ResponseEntity.ok().body(recordService.getChart(session, request));
    }

    @PostMapping("chart/reply")
    public ResponseEntity<?> getChartReply(HttpSession session, @RequestBody MedicalReply.Request request) {
        return ResponseEntity.ok().body(recordService.getChartReply(session, request));
    }

    @PostMapping("chart/function")
    public ResponseEntity<?> getFunctionChart(HttpSession session, @RequestBody Chart.Request request) {
        return ResponseEntity.ok().body(recordService.getFunctionChart(session, request));
    }
}
