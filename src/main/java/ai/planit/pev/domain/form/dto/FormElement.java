package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormElement {
    private int sectionSeq;
    private String id;
    private String parentId;
    private String classType;
    private String controlType;
    private String value;
    private String defaultValue;

    public void setElement(FormElement element) {
        this.id = element.getId();
        this.parentId = element.getParentId();
        this.classType = element.getClassType();
        this.controlType = element.getControlType();
        this.value = element.getValue();
        this.defaultValue = element.getDefaultValue();
    }
}
