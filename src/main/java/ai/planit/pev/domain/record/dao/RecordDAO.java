package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.*;

import java.util.List;

public interface RecordDAO {
    List<DeptInfoDTO> getDeptInfoList();

    List<RecordXmlFormResponseDTO> getRecordXmlForm(RecordXmlFormRequestDTO recordXmlFormRequestDTO);

    List<CertificateDTO> getCertificateListByPtNo(String ptNo);

    List<DetailResponseDTO> getMedicalRecordListByCondition(DetailRequestDTO detailRequestDTO);

    List<DetailResponseDTO> getSurgeryRecordListByCondition(DetailRequestDTO detailRequestDTO);

    List<DetailResponseDTO> getDepartmentRecordListByCondition(DetailRequestDTO detailRequestDTO);

    List<String> getDepartmentListForDepartmentRecord(DetailResponseDTO detailResponseDTO);

    List<DetailResponseDTO> getDischargeRecordListByCondition(DetailRequestDTO detailRequestDTO);

    List<DetailResponseDTO> getAnesthesiaRecordListByCondition(DetailRequestDTO detailRequestDTO);

    List<DetailResponseDTO> getOrderRecordListByCondition(DetailRequestDTO detailRequestDTO);
}
