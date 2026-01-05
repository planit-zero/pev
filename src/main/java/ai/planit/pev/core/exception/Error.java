package ai.planit.pev.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Error {
    private final int status;
    private final String message;
    private final String details;

    public Error(int status, String message) {
        this.status = status;
        this.message = message;
        this.details = null;
    }

    public Error(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
