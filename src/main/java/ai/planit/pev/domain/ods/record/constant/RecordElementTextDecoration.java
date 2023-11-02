package ai.planit.pev.domain.ods.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordElementTextDecoration {
    NORMAL("normal"),
    UNDERLINE("underline")
    ;

    private final String value;
}
