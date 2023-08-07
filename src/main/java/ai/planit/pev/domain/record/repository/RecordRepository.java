package ai.planit.pev.domain.record.repository;

import ai.planit.pev.domain.record.dto.Certificate;

import java.util.List;

public interface RecordRepository {
    List<Certificate> findAllCertificateByPtNo(String ptNo);
}
