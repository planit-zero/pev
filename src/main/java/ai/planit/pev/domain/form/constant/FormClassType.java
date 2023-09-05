package ai.planit.pev.domain.form.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormClassType {
    ENTITY("E", "Entity"),
    ATTRIBUTE("A", "Attribute"),
    VALUE("V", "Value");

    private final String code;
    private final String text;
}
