package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dto.CertificateDTO;
import ai.planit.pev.domain.record.dto.DeptInfoDTO;
import ai.planit.pev.domain.record.dto.DetailRequestDTO;
import ai.planit.pev.domain.record.dto.DetailResponseDTO;

import java.util.List;

public interface RecordService {
    List<DeptInfoDTO> getDeptInfoList();

    List<CertificateDTO> getCertificateListByPtNo(String ptNo);

    List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO);
}
