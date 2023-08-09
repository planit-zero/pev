package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.CertificateDTO;
import ai.planit.pev.domain.record.dto.DetailRequestDTO;
import ai.planit.pev.domain.record.dto.DetailResponseDTO;

import java.util.List;

public interface RecordDAO {
    List<CertificateDTO> getCertificateListByPtNo(String ptNo);

    List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO);
}
