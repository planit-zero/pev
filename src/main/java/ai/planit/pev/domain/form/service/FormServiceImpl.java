package ai.planit.pev.domain.form.service;

import ai.planit.pev.domain.form.constant.FormClassType;
import ai.planit.pev.domain.form.dao.FormDAO;
import ai.planit.pev.domain.form.dto.*;
import ai.planit.pev.domain.form.utility.FormUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormDAO formDAO;
    private final FormFixedSectionService formFixedSectionService;

    public FormContentResponse getFormContent(FormContentRequest formContentRequest) {
        FormContentResponse formContentResponse = new FormContentResponse();

        List<FormSheet> formSheetList = new ArrayList<>();

        for (FormIdentifier identifier : formContentRequest.getIdentifiers()) {
            FormSheet formSheet = getFormSheet(identifier);
            formSheetList.add(formSheet);
        }

        formContentResponse.setSheets(formSheetList);

        return formContentResponse;
    }

    private FormSheet getFormSheet(FormIdentifier identifier) {
        FormSheet formSheet = new FormSheet();

        FormInfoBasic formInfoBasic = formDAO.getFormBasicInfo(identifier);
        formSheet.setBasicInfo(formInfoBasic);
        formSheet.setSections(getFormSections(identifier, formSheet));

        return formSheet;
    }

    private List<FormSection> getFormSections(FormIdentifier identifier, FormSheet formSheet) {
        List<FormSection> sections = formDAO.getFormSections(identifier);
        List<FormElement> elements = formDAO.getFormElements(identifier);

        for (FormSection section : sections) {
            if (section.getMdfmSctnSeq() == -1) {
                FormSection fixedSection = formFixedSectionService.getFixedSection(identifier, section);
                section.setEntities(fixedSection.getEntities());
                continue;
            }

            List<FormElement> elementsBySection = getFormElementBySection(elements, section);
            section.setEntities(getFormEntities(identifier, elementsBySection));
        }

        sections.add(0, getHeaderSection(formSheet));
        sections.add(getFooterSection(formSheet));

        return sections;
    }

    private FormSection getHeaderSection(FormSheet formSheet) {
        FormSection headerSection = new FormSection();

        headerSection.setMdfmId(formSheet.getMdfmId());
        headerSection.setMdfmFomSeq(formSheet.getMdfmFomSeq());
        headerSection.setMdfmSctnSeq(-99);

        List<FormEntity> headerEntities = new ArrayList<>();

        // 1. 서식지명 (작성일자)
        FormEntity entity1 = FormUtility.getFakeEntity(true, formSheet.getItemNm(), String.format("(%s)", formSheet.getWritingDate()));
        headerEntities.add(entity1);

        // 2. 작성과
        FormEntity entity2 = FormUtility.getFakeEntity(true, "작성과:", formSheet.getWritingDeptNm());
        headerEntities.add(entity2);

        // 3. 수진과
        FormEntity entity3 = FormUtility.getFakeEntity(true, "수진과:", formSheet.getPtMedDeptNm());
        headerEntities.add(entity3);

        headerSection.setEntities(headerEntities);

        return headerSection;
    }

    private FormSection getFooterSection(FormSheet formSheet) {
        FormSection footerSection = new FormSection();

        footerSection.setMdfmId(formSheet.getMdfmId());
        footerSection.setMdfmFomSeq(formSheet.getMdfmFomSeq());
        footerSection.setMdfmSctnSeq(99);

        List<FormEntity> footerEntities = new ArrayList<>();

        // 1. 작성자
        FormEntity entity1 = FormUtility.getFakeEntity(true, "작성자", formSheet.getWriterNm());
        footerEntities.add(entity1);

        // 2. 작성시간
        FormEntity entity2 = FormUtility.getFakeEntity(true, "작성시간", formSheet.getWritingDateTime());
        footerEntities.add(entity2);

        footerSection.setEntities(footerEntities);

        return footerSection;
    }

    private List<FormElement> getFormElementBySection(List<FormElement> elements, FormSection section) {
        return elements
                .stream()
                .filter(element -> element.getSectionSeq() == section.getMdfmSctnSeq())
                .collect(Collectors.toList());
    }

    private List<FormEntity> getFormEntities(FormIdentifier identifier, List<FormElement> elementsBySection) {
        List<FormEntity> entities = new ArrayList<>();

        List<FormElement> entityElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.ENTITY.getCode()))
                .collect(Collectors.toList());

        for (FormElement entityElement : entityElements) {
            FormEntity entity = getFormEntity(identifier, elementsBySection, entityElement);
            entities.add(entity);
        }

        return entities;
    }

    private FormEntity getFormEntity(FormIdentifier identifier, List<FormElement> elementsBySection, FormElement entityElement) {
        FormEntity entity = new FormEntity();
        entity.setElement(entityElement);

        List<FormAttribute> attributes = getFormAttributes(identifier, elementsBySection, entity.getId());

        entity.setHasAttributes(attributes.size() > 0);
        entity.setAttributes(attributes);
        entity.setValues(getFormValues(identifier, elementsBySection, entity.getId()));

        return entity;
    }

    private List<FormAttribute> getFormAttributes(FormIdentifier identifier, List<FormElement> elementsBySection, String parentId) {
        List<FormAttribute> attributes = new ArrayList<>();

        List<FormElement> attributeElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.ATTRIBUTE.getCode()) && element.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormElement attributeElement : attributeElements) {
            FormAttribute attribute = getFormAttribute(identifier, elementsBySection, attributeElement);
            attributes.add(attribute);
        }

        return attributes;
    }

    private FormAttribute getFormAttribute(FormIdentifier identifier, List<FormElement> elementsBySection, FormElement attributeElement) {
        FormAttribute attribute = new FormAttribute();
        attribute.setElement(attributeElement);
        attribute.setValues(getFormValues(identifier, elementsBySection, attribute.getId()));
        return attribute;
    }

    private List<FormValue> getFormValues(FormIdentifier identifier, List<FormElement> elementsBySection, String parentId) {
        List<FormValue> values = new ArrayList<>();

        List<FormElement> valueElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.VALUE.getCode()) && element.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormElement valueElement : valueElements) {
            FormValue value = getFormValue(identifier, valueElement);
            values.add(value);
        }

        return values;
    }

    private FormValue getFormValue(FormIdentifier identifier, FormElement valueElement) {
        FormValue formValue = new FormValue();
        formValue.setElement(valueElement);

        FormValueIdentifier valueIdentifier = new FormValueIdentifier();

        valueIdentifier.setMdrcId(identifier.getMdrcId());
        valueIdentifier.setMdrcFomSeq(identifier.getMdrcFomSeq());
        valueIdentifier.setMdfmCpemId(valueElement.getId());

        List<FormValueData> valueDataList = formDAO.getFormValueData(valueIdentifier);

        if (valueDataList.size() > 0) {
            // TODO: 테이블 등 다중 값을 가지고 있는 경우 추후 처리 필요
            formValue.setValue(valueDataList.get(0).getMdfmElmtInptCnte());
            return formValue;
        }

        List<FormValueLargeData> valueLargeDataList = formDAO.getFormValueLargeData(valueIdentifier);

        if (valueLargeDataList.size() > 0) {
            // TODO: 테이블 등 다중 값을 가지고 있는 경우 추후 처리 필요
            formValue.setValue(valueLargeDataList.get(0).getDcstLdat());
        }

        return formValue;
    }
}
