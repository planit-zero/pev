package ai.planit.pev.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<?> handleBaseException(Exception e) {
        int status = 500;
        String message = e.getMessage();

        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            status = be.getErrorType().getStatus();
        }

        Error error = new Error(status, message);

        return new ResponseEntity<>(error, HttpStatus.valueOf(status));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleException(Exception e) {
        Error error = new Error(500, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
