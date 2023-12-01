package ai.planit.pev.domain.ods.function.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.function.dao.FunctionDAO;
import ai.planit.pev.domain.ods.function.dto.FunctionData;
import ai.planit.pev.domain.ods.function.dto.FunctionDecodeMaster;
import ai.planit.pev.domain.ods.record.constant.RecordElementClass;
import ai.planit.pev.domain.ods.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.ods.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunctionServiceImpl implements FunctionService {
    private final FunctionDAO functionDAO;

    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();

        recordSheet.setHeaderSection(getRecordHeaderSection(record));
        recordSheet.setSections(getRecordSections(record));

        return recordSheet;
    }

    private RecordSection getRecordHeaderSection(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText("기능검사결과");

        List<RecordAttribute> attributes = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "진료과 :", String.format("%s (%s)", record.getWritingDeptNm(), record.getPactTpNm())));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "작성일 :", record.getWritingDate()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "항목명 :", record.getItemNm()));

        entity.setAttributes(attributes);
        entities.add(entity);

        section.setEntities(entities);

        return section;
    }

    private List<RecordSection> getRecordSections(Record.Response record) {
        List<RecordSection> sections = new ArrayList<>();

        List<FunctionDecodeMaster> masterList = functionDAO.getFunctionDecodeMasterList(record.getExamKey());

        for (FunctionDecodeMaster master : masterList) {
            sections.add(getRecordSection(master));
        }

        return sections;
    }

    private RecordSection getRecordSection(FunctionDecodeMaster master) {
        RecordSection section = new RecordSection();

        List<FunctionData> functionDataList = functionDAO.getFunctionData(master);
        List<FunctionData> entityDataList = functionDataList
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.ENTITY.getType()))
                .collect(Collectors.toList());

        section.setEntities(getRecordEntities(functionDataList, entityDataList));

        return section;
    }

    private List<RecordEntity> getRecordEntities(List<FunctionData> functionDataList, List<FunctionData> entityDataList) {
        List<RecordEntity> entities = new ArrayList<>();

        for (FunctionData entityData : entityDataList) {
            entities.add(getRecordEntity(functionDataList, entityData));
        }

        return entities;
    }

    private RecordEntity getRecordEntity(List<FunctionData> functionDataList, FunctionData entityData) {
        RecordEntity entity = new RecordEntity();

        entity.setClassType(entityData.getClassType());
        entity.setControlType(entityData.getControlType());
        entity.setText(entityData.getText());

        entity.setAttributes(getRecordAttributes(functionDataList, entityData.getId()));
        entity.setValues(getRecordValues(functionDataList, entityData.getId()));

        return entity;
    }

    private List<RecordAttribute> getRecordAttributes(List<FunctionData> functionDataList, String parentId) {
        List<RecordAttribute> attributes = new ArrayList<>();

        List<FunctionData> attributeDataList = functionDataList
                .stream()
                .filter(d -> d.getParentId().equals(parentId))
                .filter(d -> d.getClassType().equals(RecordElementClass.ATTRIBUTE.getType()))
                .collect(Collectors.toList());

        for (FunctionData attributeData : attributeDataList) {
            attributes.add(getRecordAttribute(functionDataList, attributeData));
        }

        return attributes;
    }

    private RecordAttribute getRecordAttribute(List<FunctionData> functionDataList, FunctionData attributeData) {
        RecordAttribute attribute = new RecordAttribute();

        attribute.setClassType(attributeData.getClassType());
        attribute.setControlType(attributeData.getControlType());
        attribute.setText(attributeData.getText());

        attribute.setAttributes(getRecordAttributes(functionDataList, attributeData.getId()));
        attribute.setValues(getRecordValues(functionDataList, attributeData.getId()));

        return attribute;
    }

    private List<RecordValue> getRecordValues(List<FunctionData> functionDataList, String parentId) {
        List<RecordValue> values = new ArrayList<>();

        List<FunctionData> valueDataList = functionDataList
                .stream()
                .filter(d -> d.getParentId().equals(parentId))
                .filter(d -> d.getClassType().equals(RecordElementClass.VALUE.getType()))
                .collect(Collectors.toList());

        for (FunctionData valueData : valueDataList) {
            values.add(getRecordValue(valueData));
        }

        return values;
    }

    private RecordValue getRecordValue(FunctionData valueData) {
        RecordValue value = new RecordValue();

        value.setClassType(valueData.getClassType());
        value.setControlType(valueData.getControlType());
        value.setText(valueData.getText());

        return value;
    }
}
