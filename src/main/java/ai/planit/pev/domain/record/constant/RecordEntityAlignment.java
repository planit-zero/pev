package ai.planit.pev.domain.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordEntityAlignment {
    LEFT("left"),
    RIGHT("right"),
    CENTER("center"),
    ;

    private final String alignment;
}
