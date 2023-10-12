package ai.planit.pev.domain.medical.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.medical.dao.MedicalFormDAO;
import ai.planit.pev.domain.medical.dto.*;
import ai.planit.pev.domain.record.constant.RecordElementClass;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalFormServiceImpl implements MedicalFormService {
    private final MedicalFormDAO medicalFormDAO;

    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();
        recordSheet.setSections(getRecordSections(record));

        return recordSheet;
    }

    private List<RecordSection> getRecordSections(Record.Response record) {
        List<MedicalFormData> formDataList = medicalFormDAO.getMedicalFormData(record);
        List<Integer> sectionSeqList = formDataList.stream().map(MedicalFormData::getSectionSeq).distinct().collect(Collectors.toList());

        List<RecordSection> sections = new ArrayList<>();

        for (Integer sectionSeq : sectionSeqList) {
            List<MedicalFormData> formDataListInSection = formDataList
                    .stream()
                    .filter(d -> d.getSectionSeq() == sectionSeq)
                    .collect(Collectors.toList());

            RecordSection section = getRecordSection(formDataListInSection);
            if (section != null) sections.add(section);
        }

        return sections;
    }

    private RecordSection getRecordSection(List<MedicalFormData> formDataListInSection) {
        RecordSection section = new RecordSection();

        List<MedicalFormData> entityDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.ENTITY.getType()))
                .collect(Collectors.toList());

        section.setEntities(getRecordEntities(formDataListInSection, entityDataList));

        if (section.getEntities().size() == 0) return null;
        return section;
    }

    private List<RecordEntity> getRecordEntities(List<MedicalFormData> formDataListInSection, List<MedicalFormData> entityDataList) {
        List<RecordEntity> entities = new ArrayList<>();

        for (MedicalFormData entityData : entityDataList) {
            RecordEntity entity = getRecordEntity(formDataListInSection, entityData);
            if (entity != null) entities.add(entity);
        }

        return entities;
    }

    private RecordEntity getRecordEntity(List<MedicalFormData> formDataListInSection, MedicalFormData entityData) {
        RecordEntity entity = new RecordEntity();

        entity.setControlType(entityData.getControlType());
        entity.setClassType(entityData.getClassType());
        entity.setText(entityData.getText());
        entity.setAttributes(getRecordAttributes(formDataListInSection, entityData.getId()));
        entity.setValues(getRecordValues(formDataListInSection, entityData.getId()));

        if (entity.getAttributes().size() == 0 && entity.getValues().size() == 0) return null;
        return entity;
    }

    private List<RecordAttribute> getRecordAttributes(List<MedicalFormData> formDataListInSection, String parentId) {
        List<RecordAttribute> attributes = new ArrayList<>();

        List<MedicalFormData> attributeDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.ATTRIBUTE.getType())
                        && d.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (MedicalFormData attributeData : attributeDataList) {
            RecordAttribute attribute = getRecordAttribute(formDataListInSection, attributeData);
            if (attribute != null) attributes.add(attribute);
        }

        return attributes;
    }

    private RecordAttribute getRecordAttribute(List<MedicalFormData> formDataListInSection, MedicalFormData attributeData) {
        RecordAttribute attribute = new RecordAttribute();

        attribute.setControlType(attributeData.getControlType());
        attribute.setClassType(attributeData.getClassType());
        attribute.setText(attributeData.getText());
        attribute.setAttributes(getRecordAttributes(formDataListInSection, attributeData.getId()));
        attribute.setValues(getRecordValues(formDataListInSection, attributeData.getId()));

        if (attribute.getAttributes().size() == 0 && attribute.getValues().size() == 0) return null;
        return attribute;
    }

    private List<RecordValue> getRecordValues(List<MedicalFormData> formDataListInSection, String parentId) {
        List<RecordValue> values = new ArrayList<>();

        List<MedicalFormData> valueDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.VALUE.getType())
                        && d.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (MedicalFormData valueData : valueDataList) {
            values.add(getRecordValue(valueData));
        }

        return values;
    }

    private RecordValue getRecordValue(MedicalFormData valueData) {
        RecordValue value = new RecordValue();

        value.setControlType(valueData.getControlType());
        value.setClassType(valueData.getClassType());
        value.setText(valueData.getText());

        return value;
    }
}
