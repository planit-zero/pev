package ai.planit.pev.domain.form.utility;

import ai.planit.pev.domain.form.dto.FormEntity;
import ai.planit.pev.domain.form.dto.FormValue;

import java.util.ArrayList;
import java.util.List;

public class FormUtility {
    public static FormEntity getFakeEntity(String entityStr, String valueStr) {
        FormEntity entity = new FormEntity();

        entity.setValue(entityStr);

        List<FormValue> formValues = new ArrayList<>();
        FormValue formValue = new FormValue();
        formValue.setValue(valueStr);
        formValues.add(formValue);

        entity.setValues(formValues);

        return entity;
    }
}
