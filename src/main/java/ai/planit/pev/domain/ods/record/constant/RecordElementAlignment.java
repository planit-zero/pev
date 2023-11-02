package ai.planit.pev.domain.ods.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordElementAlignment {
    LEFT("left"),
    RIGHT("right"),
    CENTER("center"),
    ;

    private final String value;
}
