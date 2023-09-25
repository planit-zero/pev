package ai.planit.pev.domain.specimen.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.record.constant.RecordEntityType;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.domain.specimen.dao.SpecimenDAO;
import ai.planit.pev.domain.specimen.dto.SpecimenData;
import ai.planit.pev.domain.specimen.dto.SpecimenInfo;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {
    private final SpecimenDAO specimenDAO;

    /** {@inheritDoc} */
    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();
        recordSheet.setSections(getRecordSections(pid, record));

        return recordSheet;
    }

    /**
     * 검체검사 기록의 섹션 목록 생성
     *
     * @param pid 환자병록번호
     * @param record 조회할 기록 정보
     * @return 검체검사 섹션 목록
     */
    private List<RecordSection> getRecordSections(String pid, Record.Response record) {
        SpecimenInfo.Request request = new SpecimenInfo.Request();
        request.setPtNo(pid);
        request.setSpcmNo(record.getExamKey());
        request.setMedExmCtgCd(record.getKeyId());

        SpecimenInfo.Response specimenInfo = specimenDAO.getSpecimenInfo(request);

        List<RecordSection> sections = new ArrayList<>();

        // 검사명-검체명
        sections.add(getSpecimenCategorySection(specimenInfo));

        // 항목명-검사결과-참고치
        sections.add(getSpecimenResultSection(pid, record));

        // 보고자
        sections.add(getSpecimenReporterSection(specimenInfo));

        return sections;
    }

    /**
     * 검체검사 기록의 검사 분류 정보 섹션 생성
     *
     * @param specimenInfo 검체검사 기록 정보
     * @return 검체검사 검사 분류 정보 섹션
     */
    private RecordSection getSpecimenCategorySection(SpecimenInfo.Response specimenInfo) {
        RecordSection recordSection = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        entities.add(PevEntityUtil.getSimpleTextEntity(true, "검사명 :", specimenInfo.getExmCtgNm()));
        entities.add(PevEntityUtil.getSimpleTextEntity(true, "검체명 :", specimenInfo.getSpcmNm()));

        recordSection.setEntities(entities);
        return recordSection;
    }

    /**
     * 검체검사 기록의 검사결과 섹션 생성
     *
     * @param pid 환자병록번호
     * @param record 조회할 기록 정보
     * @return 검체검사 기록 검사결과 섹션
     */
    private RecordSection getSpecimenResultSection(String pid, Record.Response record) {
        RecordSection recordSection = new RecordSection();

        SpecimenData.Request request = new SpecimenData.Request();
        request.setPtNo(pid);
        request.setSpcmNo(record.getExamKey());
        request.setMedExmCtgCd(record.getKeyId());

        List<SpecimenData.Response> specimenDataList = specimenDAO.getSpecimenData(request);
        recordSection.setEntities(getSpecimenResultEntities(specimenDataList));

        return recordSection;
    }

    /**
     * 검체검사 기록의 검사결과 엔티티 생성
     *
     * @param specimenDataList 검체검사 기록의 검사결과 데이터 목록
     * @return 검체검사 기록 검사결과 엔티티
     */
    private List<RecordEntity> getSpecimenResultEntities(List<SpecimenData.Response> specimenDataList) {
        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setType(RecordEntityType.TABLE.getType());

        List<RecordAttribute> attributes = new ArrayList<>();

        RecordAttribute nameAttribute = new RecordAttribute();
        nameAttribute.setText("항목명");

        RecordAttribute resultAttribute = new RecordAttribute();
        resultAttribute.setText("검사결과");

        RecordAttribute refAttribute = new RecordAttribute();
        refAttribute.setText("참고치");

        List<RecordValue> nameValues = new ArrayList<>();
        List<RecordValue> resultValues = new ArrayList<>();
        List<RecordValue> refValues = new ArrayList<>();

        for (SpecimenData.Response specimenData : specimenDataList) {
            RecordValue nameValue = new RecordValue();
            RecordValue resultValue = new RecordValue();
            RecordValue refValue = new RecordValue();

            nameValue.setText(specimenData.getEitmAbbr());
            resultValue.setText(specimenData.getExrsFsrcDcstLdat());
            refValue.setText(specimenData.getRefCnte());

            nameValues.add(nameValue);
            resultValues.add(resultValue);
            refValues.add(refValue);
        }

        nameAttribute.setValues(nameValues);
        resultAttribute.setValues(resultValues);
        refAttribute.setValues(refValues);

        attributes.add(nameAttribute);
        attributes.add(resultAttribute);
        attributes.add(refAttribute);

        entity.setAttributes(attributes);
        entities.add(entity);

        return entities;
    }

    /**
     * 검체검사 기록의 보고자 섹션 생성
     *
     * @param specimenInfo 검체검사 기록 정보
     * @return 검체검사 기록 보고자 섹션
     */
    private RecordSection getSpecimenReporterSection(SpecimenInfo.Response specimenInfo) {
        RecordSection recordSection = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        entities.add(PevEntityUtil.getSimpleTextEntity(false, "보고자", specimenInfo.getItemCbVrfcIptnCnte()));

        recordSection.setEntities(entities);
        return recordSection;
    }
}
