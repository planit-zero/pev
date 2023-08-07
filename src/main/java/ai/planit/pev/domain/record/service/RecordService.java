package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dto.Certificate;
import ai.planit.pev.domain.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public List<Certificate> findCertificateByPtNo(String ptNo) {
        return recordRepository.findAllCertificateByPtNo(ptNo);
    }
}
