package ai.planit.pev.core.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<?> handleBaseException(Exception e) {
        System.err.println(e.getMessage());

        int status = 500;

        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            status = be.getErrorType().getStatus();
        }

        return getResponseEntity(status, e.getMessage(), HttpStatus.valueOf(status));
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    protected ResponseEntity<?> handleSqlException(Exception e) {
        System.err.println(e.getMessage());
        return getResponseEntity(500, "데이터 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleException(Exception e) {
        System.err.println(e.getMessage());
        return getResponseEntity(500, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Error> getResponseEntity(int status, String message, HttpStatus httpStatus) {
        Error error = new Error(status, message);
        return new ResponseEntity<>(error, httpStatus);
    }

}
