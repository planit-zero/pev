package ai.planit.pev.domain.ods.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordEntityPattern {
    TEXT("TEXT"),
    TABLE("TABLE"),
    ;

    private final String type;
}
