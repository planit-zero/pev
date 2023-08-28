package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dao.RecordDAO;
import ai.planit.pev.domain.record.dto.CertificateDTO;
import ai.planit.pev.domain.record.dto.DeptInfoDTO;
import ai.planit.pev.domain.record.dto.DetailRequestDTO;
import ai.planit.pev.domain.record.dto.DetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordDAO recordDAO;

    @Override
    public List<DeptInfoDTO> getDeptInfoList() {
        return recordDAO.getDeptInfoList();
    }

    @Override
    public List<CertificateDTO> getCertificateListByPtNo(String ptNo) {
        return recordDAO.getCertificateListByPtNo(ptNo);
    }

    @Override
    public List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO) {
        List<DetailResponseDTO> detailListByCondition = new ArrayList<>();

        List<String> recordTypeList = new ArrayList<>(Arrays.asList(detailRequestDTO.getRecordType()));
        List<String> recordDetailTypeList = new ArrayList<>(Arrays.asList(detailRequestDTO.getRecordDetailType()));

        // 진료기록
        if (recordTypeList.contains("DR")) {
            detailListByCondition.addAll(getMedicalRecordList(recordDetailTypeList, detailRequestDTO));
        }

        // 간호

        // 처방
        if (recordTypeList.contains("OR")) {
            detailListByCondition.addAll(recordDAO.getOrderRecordListByCondition(detailRequestDTO));
        }

        // 검사

        // 스캔자료

        // 특성화 기록


        detailListByCondition = detailListByCondition
                .stream()
                .sorted(Comparator.comparing(DetailResponseDTO::getWritingDate).reversed())
                .collect(Collectors.toList());

        return detailListByCondition;
    }

    private List<DetailResponseDTO> getMedicalRecordList(List<String> recordDetailTypeList, DetailRequestDTO detailRequestDTO) {
        List<DetailResponseDTO> medicalRecordList = new ArrayList<>();

        // 진료기록 - 수술기록
        if (recordDetailTypeList.contains("D005")) {
            medicalRecordList.addAll(recordDAO.getSurgeryRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 퇴원기록
        if (recordDetailTypeList.contains("D006")) {
            medicalRecordList.addAll(recordDAO.getDischargeRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 타과의뢰
        if (recordDetailTypeList.contains("D007")) {
            List<DetailResponseDTO> departmentRecordListByCondition = recordDAO.getDepartmentRecordListByCondition(detailRequestDTO);

            for (DetailResponseDTO departmentRecord : departmentRecordListByCondition) {
                List<String> departmentList = recordDAO.getDepartmentListForDepartmentRecord(departmentRecord);
                String departments = String.join(", ", departmentList);

                departmentRecord.setItemNm(String.format("%s (%s)", departmentRecord.getItemNm(), departments));
            }

            medicalRecordList.addAll(departmentRecordListByCondition);
        }

        // 진료기록 - 마취기록
        if (recordDetailTypeList.contains("D010")) {
            detailRequestDTO.setDetailType("D010");
            medicalRecordList.addAll(recordDAO.getAnesthesiaRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 마취 전 상태평가
        if (recordDetailTypeList.contains("D011")) {
            detailRequestDTO.setDetailType("D011");
            medicalRecordList.addAll(recordDAO.getAnesthesiaRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 그외
//        if (recordDetailTypeList.contains("D0")) {
//            detailListByCondition.addAll(recordDAO.getMedicalRecordListByCondition(detailRequestDTO));
//        }

        return medicalRecordList;
    }
}
