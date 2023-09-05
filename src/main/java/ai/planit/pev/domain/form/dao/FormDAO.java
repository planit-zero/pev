package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.*;

import java.util.List;

public interface FormDAO {
    FormInfoBasic getFormBasicInfo(FormIdentifier identifier);

    List<FormSection> getFormSections(FormIdentifier identifier);

    List<FormElement> getFormElements(FormIdentifier identifier);

    List<FormValueData> getFormValueData(FormValueIdentifier valueIdentifier);

    List<FormValueLargeData> getFormValueLargeData(FormValueIdentifier valueIdentifier);
}
