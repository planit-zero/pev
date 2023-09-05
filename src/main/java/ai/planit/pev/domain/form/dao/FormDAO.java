package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormElement;
import ai.planit.pev.domain.form.dto.FormIdentifier;
import ai.planit.pev.domain.form.dto.FormInfoBasic;
import ai.planit.pev.domain.form.dto.FormSection;

import java.util.List;

public interface FormDAO {
    FormInfoBasic getFormBasicInfo(FormIdentifier identifier);

    List<FormSection> getFormSections(FormIdentifier identifier);

    List<FormElement> getFormElements(FormIdentifier identifier);
}
