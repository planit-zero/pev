package ai.planit.pev.domain.meta.event.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    REPORT("목록조회");

    private final String value;
}
