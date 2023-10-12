package ai.planit.pev.domain.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordElementClass {
    ENTITY("E"),
    ATTRIBUTE("A"),
    VALUE("V"),
    ;

    private final String type;
}
