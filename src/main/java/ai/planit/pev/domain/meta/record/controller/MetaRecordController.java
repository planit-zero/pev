package ai.planit.pev.domain.meta.record.controller;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시에 실행
    public void reloadMedicalRecordFormat() {
        MetaRecordFormat.Request request = MetaRecordFormat.Request.builder()
                .startDate(LocalDate.now().minusDays(1).toString())
                .endDate(LocalDate.now().toString())
                .build();

        List<MetaRecordFormat.Response> metaRecordFormatList = medicalService.getMedicalRecordFormatList(request);
        metaRecordService.reloadMedicalRecordFormat(metaRecordFormatList);
    }
}
