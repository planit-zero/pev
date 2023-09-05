package ai.planit.pev.domain.form.service;

import ai.planit.pev.domain.form.constant.FormClassType;
import ai.planit.pev.domain.form.dao.FormDAO;
import ai.planit.pev.domain.form.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormDAO formDAO;

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
        formSheet.setSections(getFormSections(identifier));

        return formSheet;
    }

    private List<FormSection> getFormSections(FormIdentifier identifier) {
        List<FormSection> sections = formDAO.getFormSections(identifier);
        List<FormElement> elements = formDAO.getFormElements(identifier);

        for (FormSection section : sections) {
            List<FormElement> elementsBySection = getFormElementBySection(elements, section);
            section.setEntities(getFormEntitiesInElements(elementsBySection));
        }

        return sections;
    }

    private List<FormElement> getFormElementBySection(List<FormElement> elements, FormSection section) {
        return elements
                .stream()
                .filter(element -> element.getSectionSeq() == section.getMdfmSctnSeq())
                .collect(Collectors.toList());
    }

    private List<FormEntity> getFormEntitiesInElements(List<FormElement> elementsBySection) {
        List<FormEntity> entities = new ArrayList<>();

        List<FormElement> entityElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.ENTITY.getCode()))
                .collect(Collectors.toList());

        for (FormElement entityElement : entityElements) {
            FormEntity entity = getFormEntity(elementsBySection, entityElement);
            entities.add(entity);
        }

        return entities;
    }

    private FormEntity getFormEntity(List<FormElement> elementsBySection, FormElement entityElement) {
        FormEntity entity = new FormEntity();
        entity.setElement(entityElement);

        // TODO: Entity ID 에 따른 하드코딩 처리
        if (entityElement.getSectionSeq() == -1) {

        }

        List<FormAttribute> attributes = getFormAttributes(elementsBySection, entity.getId());

        entity.setHasAttributes(attributes.size() > 0);
        entity.setAttributes(attributes);
        entity.setValues(getFormValues(elementsBySection, entity.getId()));

        return entity;
    }

    private List<FormAttribute> getFormAttributes(List<FormElement> elementsBySection, String parentId) {
        List<FormAttribute> attributes = new ArrayList<>();

        List<FormElement> attributeElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.ATTRIBUTE.getCode()) && element.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormElement attributeElement : attributeElements) {
            FormAttribute attribute = getFormAttribute(elementsBySection, attributeElement);
            attributes.add(attribute);
        }

        return attributes;
    }

    private FormAttribute getFormAttribute(List<FormElement> elementsBySection, FormElement attributeElement) {
        FormAttribute attribute = new FormAttribute();
        attribute.setElement(attributeElement);
        attribute.setValues(getFormValues(elementsBySection, attribute.getId()));
        return attribute;
    }

    private List<FormValue> getFormValues(List<FormElement> elementsBySection, String parentId) {
        List<FormValue> values = new ArrayList<>();

        List<FormElement> valueElements = elementsBySection
                .stream()
                .filter(element -> element.getClassType().equals(FormClassType.VALUE.getCode()) && element.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (FormElement valueElement : valueElements) {
            FormValue value = getFormValue(valueElement);
            values.add(value);
        }

        return values;
    }

    private FormValue getFormValue(FormElement valueElement) {
        FormValue formValue = new FormValue();
        formValue.setElement(valueElement);

        // TODO: VALUE 실제 값 세팅
        return formValue;
    }
}
