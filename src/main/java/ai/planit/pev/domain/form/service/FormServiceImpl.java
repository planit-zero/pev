package ai.planit.pev.domain.form.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.form.dao.FormDAO;
import ai.planit.pev.domain.form.dto.*;
import ai.planit.pev.domain.record.constant.RecordElementClass;
import ai.planit.pev.domain.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.record.constant.RecordTarget;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormDAO formDAO;

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
        if (record.getRecordType().equals(RecordTarget.MEDICAL_RECORD.getType())) {
            if (
                    record.getRecordDetailType().equals(RecordTarget.MEDICAL_OUTPATIENT_FIRST.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_OUTPATIENT_PROGRESS.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_INPATIENT_FIRST.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_INPATIENT_PROGRESS.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_EMERGENCY.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_SURGERY.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_DISCHARGE.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_REQUEST.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_COVER.getType())
            ) {
                return getRecordHeaderSectionWithItemNameAndDepartment(record);
            }

            if (
                    record.getRecordDetailType().equals(RecordTarget.MEDICAL_ANESTHESIA.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType())
                            || record.getRecordDetailType().equals(RecordTarget.MEDICAL_DEPARTMENT.getType())
            ) {
                return getRecordHeaderSectionWithItemName(record);
            }
        }

        return null;
    }

    private RecordSection getRecordHeaderSectionWithItemName(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        entities.add(PevEntityUtil.getSimpleTextEntity(element, String.format("%s (%s)", record.getItemNm(), record.getWritingDate()), null));
        section.setEntities(entities);

        return section;
    }

    private RecordSection getRecordHeaderSectionWithItemNameAndDepartment(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        RecordEntity entity = new RecordEntity();
        entity.setText(String.format("%s (%s)", record.getItemNm(), record.getWritingDate()));

        List<RecordAttribute> attributes = new ArrayList<>();

        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "작성과:", record.getWritingDeptNm()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "수진과:", record.getPtMedDeptNm()));

        entity.setAttributes(attributes);
        entities.add(entity);
        section.setEntities(entities);

        return section;
    }

    private List<RecordSection> getRecordSections(Record.Response record) {
        List<FormData> formDataList = formDAO.getFormData(record);
        List<Integer> sectionSeqList = formDataList.stream().map(FormData::getSectionSeq).distinct().collect(Collectors.toList());

        List<RecordSection> sections = new ArrayList<>();

        for (Integer sectionSeq : sectionSeqList) {
            List<FormData> formDataListInSection = formDataList
                    .stream()
                    .filter(d -> d.getSectionSeq() == sectionSeq)
                    .collect(Collectors.toList());

            FormStyleXML.Request xmlRequest = new FormStyleXML.Request();
            xmlRequest.setMdfmId(record.getMdfmId());
            xmlRequest.setMdfmFomSeq(record.getMdfmFomSeq());
            xmlRequest.setMdfmSctnSeq(sectionSeq);

            RecordSection section = getRecordSection(xmlRequest, formDataListInSection);
            if (section != null) sections.add(section);
        }

        return sections;
    }

    private RecordSection getRecordSection(FormStyleXML.Request xmlRequest, List<FormData> formDataListInSection) {
        RecordSection section = new RecordSection();

        List<FormData> entityDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.ENTITY.getType()))
                .collect(Collectors.toList());

        FormStyleSection formStyleSection = getFormStyleSection(xmlRequest);
        section.setEntities(getRecordEntities(formDataListInSection, entityDataList, formStyleSection));

        if (section.getEntities().size() == 0) return null;
        return section;
    }

    private FormStyleSection getFormStyleSection(FormStyleXML.Request xmlRequest) {
        FormStyleXML.Response formStyleXML = formDAO.getFormStyleXML(xmlRequest);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FormStyleSection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (FormStyleSection) unmarshaller.unmarshal(new StringReader(formStyleXML.getSctnDgnMetaLdat()));

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new BaseException(ErrorType.FAILED_GET_FORM_STYLE);
        }
    }

    private FormStyleItem getFormStyleItem(FormStyleSection formStyleSection, String id) {
        Optional<FormStyleItem> formStyleItem = formStyleSection.getItems()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findAny();

        return formStyleItem.orElse(null);
    }

    private List<RecordEntity> getRecordEntities(List<FormData> formDataListInSection, List<FormData> entityDataList, FormStyleSection formStyleSection) {
        List<RecordEntity> entities = new ArrayList<>();

        for (FormData entityData : entityDataList) {
            RecordEntity entity = getRecordEntity(formDataListInSection, entityData, formStyleSection);
            if (entity != null) entities.add(entity);
        }

        return entities;
    }

    private RecordEntity getRecordEntity(List<FormData> formDataListInSection, FormData entityData, FormStyleSection formStyleSection) {
        RecordEntity entity = new RecordEntity();

        entity.setControlType(entityData.getControlType());
        entity.setClassType(entityData.getClassType());
        entity.setText(entityData.getText());
        entity.setAttributes(getRecordAttributes(formDataListInSection, entityData.getId(), formStyleSection));
        entity.setValues(getRecordValues(formDataListInSection, entityData.getId(), formStyleSection));

        if (entity.getAttributes().size() == 0 && entity.getValues().size() == 0) return null;

        entity.setFormStyleItem(getFormStyleItem(formStyleSection, entityData.getId()));
        return entity;
    }

    private List<RecordAttribute> getRecordAttributes(List<FormData> formDataListInSection, String parentId, FormStyleSection formStyleSection) {
        List<RecordAttribute> attributes = new ArrayList<>();

        List<FormData> attributeDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.ATTRIBUTE.getType())
                        && d.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormData attributeData : attributeDataList) {
            RecordAttribute attribute = getRecordAttribute(formDataListInSection, attributeData, formStyleSection);
            if (attribute != null) attributes.add(attribute);
        }

        return attributes;
    }

    private RecordAttribute getRecordAttribute(List<FormData> formDataListInSection, FormData attributeData, FormStyleSection formStyleSection) {
        RecordAttribute attribute = new RecordAttribute();

        attribute.setControlType(attributeData.getControlType());
        attribute.setClassType(attributeData.getClassType());
        attribute.setText(attributeData.getText());
        attribute.setAttributes(getRecordAttributes(formDataListInSection, attributeData.getId(), formStyleSection));
        attribute.setValues(getRecordValues(formDataListInSection, attributeData.getId(), formStyleSection));

        if (attribute.getAttributes().size() == 0 && attribute.getValues().size() == 0) return null;

        attribute.setFormStyleItem(getFormStyleItem(formStyleSection, attributeData.getId()));
        return attribute;
    }

    private List<RecordValue> getRecordValues(List<FormData> formDataListInSection, String parentId, FormStyleSection formStyleSection) {
        List<RecordValue> values = new ArrayList<>();

        List<FormData> valueDataList = formDataListInSection
                .stream()
                .filter(d -> d.getClassType().equals(RecordElementClass.VALUE.getType())
                        && d.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormData valueData : valueDataList) {
            values.add(getRecordValue(valueData, formStyleSection));
        }

        return values;
    }

    private RecordValue getRecordValue(FormData valueData, FormStyleSection formStyleSection) {
        RecordValue value = new RecordValue();

        value.setControlType(valueData.getControlType());
        value.setClassType(valueData.getClassType());
        value.setText(valueData.getText());
        value.setFormStyleItem(getFormStyleItem(formStyleSection, valueData.getId()));

        return value;
    }
}
