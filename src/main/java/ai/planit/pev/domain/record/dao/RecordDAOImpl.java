package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.*;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordDAOImpl implements RecordDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<DeptInfoDTO> getDeptInfoList() {
        return sqlSessionTemplate.selectList("getDeptInfoList");
    }

    @Override
    public RecordFormInfoResponseDTO getRecordFormInfo(RecordFormInfoRequestDTO recordFormInfoRequestDTO) {
        return sqlSessionTemplate.selectOne("getRecordFormInfo", recordFormInfoRequestDTO);
    }

    @Override
    public List<RecordXmlFormResponseDTO> getRecordXmlForm(RecordXmlFormRequestDTO recordXmlFormRequestDTO) {
        return sqlSessionTemplate.selectList("getRecordXmlForm", recordXmlFormRequestDTO);
    }

    @Override
    public List<RecordValueResponseDTO> getRecordValueList(RecordValueRequestDTO recordValueRequestDTO) {
        return sqlSessionTemplate.selectList("getRecordValueList", recordValueRequestDTO);
    }

    @Override
    public List<CertificateDTO> getCertificateListByPtNo(String ptNo) {
        return sqlSessionTemplate.selectList("getCertificateListByPtNo", ptNo);
    }

    @Override
    public List<DetailResponseDTO> getMedicalRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getMedicalRecordListByCondition", detailRequestDTO);
    }

    @Override
    public List<DetailResponseDTO> getSurgeryRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getSurgeryRecordListByCondition", detailRequestDTO);
    }

    @Override
    public List<DetailResponseDTO> getDepartmentRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getDepartmentRecordListByCondition", detailRequestDTO);
    }

    @Override
    public List<String> getDepartmentListForDepartmentRecord(DetailResponseDTO detailResponseDTO) {
        return sqlSessionTemplate.selectList("getDepartmentListForDepartmentRecord", detailResponseDTO);
    }

    @Override
    public List<DetailResponseDTO> getDischargeRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getDischargeRecordListByCondition", detailRequestDTO);
    }

    @Override
    public List<DetailResponseDTO> getAnesthesiaRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getAnesthesiaRecordListByCondition", detailRequestDTO);
    }

    @Override
    public List<DetailResponseDTO> getOrderRecordListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getOrderRecordListByCondition", detailRequestDTO);
    }
}
