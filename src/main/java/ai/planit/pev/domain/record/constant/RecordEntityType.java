package ai.planit.pev.domain.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordEntityType {
    TEXT("TEXT"),
    TABLE("TABLE"),
    ;

    private final String type;
}
