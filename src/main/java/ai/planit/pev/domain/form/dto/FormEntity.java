package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormEntity extends FormElement {
    private boolean hasAttributes;
    private List<FormAttribute> attributes;
    private List<FormValue> values;
}
