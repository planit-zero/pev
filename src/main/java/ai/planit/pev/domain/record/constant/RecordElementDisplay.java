package ai.planit.pev.domain.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordElementDisplay {
    BLOCK("block"),
    INLINE("inline"),
    ;

    private final String value;
}
