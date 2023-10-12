package ai.planit.pev.domain.medical.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MedicalElementClass {
    ENTITY("E"),
    ATTRIBUTE("A"),
    VALUE("V"),
    ;

    private final String type;
}
