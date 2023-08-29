package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dto.*;

import java.util.List;

public interface RecordService {
    List<DeptInfoDTO> getDeptInfoList();

    List<RecordDataResponseDTO> getRecordData(RecordDataRequestDTO recordDataRequestDTO);

    List<CertificateDTO> getCertificateListByPtNo(String ptNo);

    List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO);
}
