package ai.planit.pev.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Error {
    private final int status;
    private final String message;
}
