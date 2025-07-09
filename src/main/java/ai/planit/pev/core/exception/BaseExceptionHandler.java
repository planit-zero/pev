package ai.planit.pev.core.exception;

import org.apache.catalina.connector.ClientAbortException;
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
        // 클라이언트가 연결을 끊은 경우 로그 남기지 않고 무시
        if (e instanceof org.apache.catalina.connector.ClientAbortException || (e.getCause() instanceof java.io.IOException && "Broken pipe".equalsIgnoreCase(e.getCause().getMessage()))) {
            return ResponseEntity.ok().build();
        }

        System.err.println(e.getMessage());
        return getResponseEntity(500, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Error> getResponseEntity(int status, String message, HttpStatus httpStatus) {
        Error error = new Error(status, message);
        return new ResponseEntity<>(error, httpStatus);
    }

    private boolean isCausedByClientAbort(Throwable e) {
        while (e != null) {
            if (e instanceof ClientAbortException) {
                return true;
            }
            e = e.getCause();
        }
        return false;
    }

}
