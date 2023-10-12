package ai.planit.pev.domain.specimen.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.record.constant.RecordElementAlignment;
import ai.planit.pev.domain.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.record.constant.RecordElementTextDecoration;
import ai.planit.pev.domain.record.constant.RecordEntityPattern;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.domain.specimen.dao.SpecimenDAO;
import ai.planit.pev.domain.specimen.dto.SpecimenData;
import ai.planit.pev.domain.specimen.dto.SpecimenHeaderData;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();

        recordSheet.setHeaderSection(getRecordHeaderSection(record.getExamKey()));
        recordSheet.setSections(getRecordSections(pid, record));

        return recordSheet;
    }

    /**
     * 검체검사 기록의 헤더 섹션 생성
     *
     * @param spcmNo 검체번호
     * @return 검체검사 기록의 헤더 섹션
     */
    private RecordSection getRecordHeaderSection(String spcmNo) {
        SpecimenHeaderData headerData = specimenDAO.getSpecimenHeaderData(spcmNo);

        RecordSection section = new RecordSection();
        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText(String.format("%s (%s)", "검체검사결과", headerData.getOrdCtgNm()));

        List<RecordAttribute> attributes = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "의뢰처/진료과 :", String.format("%s / %s", headerData.getPbsoDeptCd(), headerData.getPtHmeDeptCd())));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "의뢰의사 :", headerData.getAndrStfNm()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "의뢰일시 :", headerData.getOrdDt()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "접수일시 :", headerData.getAcptDtm()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "보고일시 :", headerData.getBrfgDtm()));

        entity.setAttributes(attributes);
        entities.add(entity);

        section.setEntities(entities);
        return section;
    }

    /**
     * 검체검사 기록의 섹션 목록 생성
     *
     * @param pid    환자병록번호
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

        RecordElement element = new RecordElement();
        element.setTextDecoration(RecordElementTextDecoration.UNDERLINE.getValue());

        entities.add(PevEntityUtil.getSimpleTextEntity(element, "검사명 :", String.format("\u2003%s", specimenInfo.getExmCtgNm())));
        entities.add(PevEntityUtil.getSimpleTextEntity(element, "검체명 :", String.format("\u2003%s", specimenInfo.getSpcmNm())));

        recordSection.setEntities(entities);
        return recordSection;
    }

    /**
     * 검체검사 기록의 검사결과 섹션 생성
     *
     * @param pid    환자병록번호
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
        entity.setType(RecordEntityPattern.TABLE.getType());

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

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());
        element.setAlignment(RecordElementAlignment.RIGHT.getValue());

        entities.add(PevEntityUtil.getSimpleTextEntity(element, "보고자", specimenInfo.getItemCbVrfcIptnCnte()));
        recordSection.setEntities(entities);

        return recordSection;
    }
}
