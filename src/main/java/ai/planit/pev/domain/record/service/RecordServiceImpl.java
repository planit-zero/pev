package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dao.RecordDAO;
import ai.planit.pev.domain.record.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.*;
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
    public RecordFormInfoResponseDTO getRecordFormInfo(RecordFormInfoRequestDTO recordFormInfoRequestDTO) {
        return recordDAO.getRecordFormInfo(recordFormInfoRequestDTO);
    }

    @Override
    public List<RecordDataResponseDTO> getRecordData(RecordDataRequestDTO recordDataRequestDTO) {
        List<RecordDataResponseDTO> recordDataList = new ArrayList<>();

        for (DetailResponseDTO detailResponseDTO : recordDataRequestDTO.getTargets()) {
            // 기본 정보 세팅
            RecordDataResponseDTO recordDataResponseDTO = new RecordDataResponseDTO();

            recordDataResponseDTO.setMdfmId(detailResponseDTO.getMdfmId());
            recordDataResponseDTO.setMdfmFomSeq(detailResponseDTO.getMdfmFomSeq());
            recordDataResponseDTO.setMdrcId(detailResponseDTO.getMdrcId());
            recordDataResponseDTO.setMdrcFomSeq(detailResponseDTO.getMdrcFomSeq());

            // 데이터 취득
            RecordValueRequestDTO recordValueRequestDTO = new RecordValueRequestDTO();

            recordValueRequestDTO.setMdrcId(detailResponseDTO.getMdrcId());
            recordValueRequestDTO.setMdrcFomSeq(detailResponseDTO.getMdrcFomSeq());

            List<RecordValueResponseDTO> recordValueResponseDTOList = recordDAO.getRecordValueList(recordValueRequestDTO);

            // xml 목록 취득
            RecordXmlFormRequestDTO recordXmlFormRequestDTO = new RecordXmlFormRequestDTO();

            recordXmlFormRequestDTO.setMdfmId(detailResponseDTO.getMdfmId());
            recordXmlFormRequestDTO.setMdfmFomSeq(detailResponseDTO.getMdfmFomSeq());

            List<RecordXmlFormResponseDTO> recordXmlFormResponseDTOList = recordDAO.getRecordXmlForm(recordXmlFormRequestDTO);

            List<RecordSectionDTO> recordSectionDTOList = new ArrayList<>();

            for (RecordXmlFormResponseDTO recordXmlFormResponseDTO : recordXmlFormResponseDTOList) {
                // xml -> DTO 변환
                try {
                    JAXBContext jaxbContext = JAXBContext.newInstance(RecordSectionDTO.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                    RecordSectionDTO recordSectionDTO = (RecordSectionDTO) unmarshaller.unmarshal(new StringReader(recordXmlFormResponseDTO.getSctnDgnMetaLdat()));
                    recordSectionDTO.setMdfmSctnSeq(recordXmlFormResponseDTO.getMdfmSctnSeq());

                    // DTO - 데이터 병합
                    for (RecordItemDTO recordItemDTO : recordSectionDTO.getItems()) {
                        List<RecordValueResponseDTO> recordValueList = recordValueResponseDTOList
                                .stream()
                                .filter(value -> value.getMdfmCpemNo().equals(recordItemDTO.getId()))
                                .collect(Collectors.toList());

                        if (recordValueList.size() > 0) {
                            RecordValueResponseDTO recordValue = recordValueList.get(0);

                            if ("V".equals(recordValue.getValueType())) {
                                recordItemDTO.setValue(recordValue.getMdfmElmtInptCnte());
                            }

                            if ("LV".equals(recordValue.getValueType())) {
                                recordItemDTO.setValue(recordValue.getDcstLdat());
                            }
                        }
                    }

                    recordSectionDTOList.add(recordSectionDTO);

                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }

            recordDataResponseDTO.setSections(recordSectionDTOList);
            recordDataList.add(recordDataResponseDTO);
        }

        return recordDataList;
    }

    @Override
    public List<CertificateDTO> getCertificateListByPtNo(String ptNo) {
        return recordDAO.getCertificateListByPtNo(ptNo);
    }

    @Override
    public List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO) {
        List<DetailResponseDTO> detailListByCondition = new ArrayList<>();

        List<String> recordTypeList = new ArrayList<>(Arrays.asList(detailRequestDTO.getRecordType()));

        // 진료기록
        detailListByCondition.addAll(getMedicalRecordList(recordTypeList, detailRequestDTO));

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

    private List<DetailResponseDTO> getMedicalRecordList(List<String> recordTypeList, DetailRequestDTO detailRequestDTO) {
        List<DetailResponseDTO> medicalRecordList = new ArrayList<>();

        // 진료기록 - 수술기록
        if (recordTypeList.contains("D005")) {
            medicalRecordList.addAll(recordDAO.getSurgeryRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 퇴원기록
        if (recordTypeList.contains("D006")) {
            medicalRecordList.addAll(recordDAO.getDischargeRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 타과의뢰
        if (recordTypeList.contains("D007")) {
            List<DetailResponseDTO> departmentRecordListByCondition = recordDAO.getDepartmentRecordListByCondition(detailRequestDTO);

            for (DetailResponseDTO departmentRecord : departmentRecordListByCondition) {
                List<String> departmentList = recordDAO.getDepartmentListForDepartmentRecord(departmentRecord);
                String departments = String.join(", ", departmentList);

                departmentRecord.setItemNm(String.format("%s (%s)", departmentRecord.getItemNm(), departments));
            }

            medicalRecordList.addAll(departmentRecordListByCondition);
        }

        // 진료기록 - 마취기록
        if (recordTypeList.contains("D010")) {
            String[] detailType = {"D010"};
            detailRequestDTO.setDetailType(detailType);
            medicalRecordList.addAll(recordDAO.getAnesthesiaRecordListByCondition(detailRequestDTO));
        }

        // 진료기록 - 마취 전 상태평가
        if (recordTypeList.contains("D011")) {
            String[] detailType = {"D011"};
            detailRequestDTO.setDetailType(detailType);
            medicalRecordList.addAll(recordDAO.getAnesthesiaRecordListByCondition(detailRequestDTO));
        }

        List<String> generalTypeList = recordTypeList
                .stream()
                .filter(type -> !type.equals("D005"))
                .filter(type -> !type.equals("D006"))
                .filter(type -> !type.equals("D007"))
                .filter(type -> !type.equals("D010"))
                .filter(type -> !type.equals("D011"))
                .collect(Collectors.toList());

        // 진료기록 - 일반
        if (generalTypeList.size() > 0) {
            String[] detailType = generalTypeList.toArray(new String[0]);
            detailRequestDTO.setDetailType(detailType);
            medicalRecordList.addAll(recordDAO.getMedicalRecordListByCondition(detailRequestDTO));
        }

        return medicalRecordList;
    }
}
