package ai.planit.pev.core.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class BaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<?> handleBaseException(BaseException e) {
        int status = e.getErrorType().getStatus();
        
        if (isDevProfile()) {
            log.error("BaseException occurred: {}", e.getMessage(), e);
            return getResponseEntity(status, e.getMessage(), getDetailedMessage(e), HttpStatus.valueOf(status));
        } else {
            log.error("BaseException occurred: {}", e.getMessage());
            return getResponseEntity(status, e.getMessage(), null, HttpStatus.valueOf(status));
        }
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    protected ResponseEntity<?> handleSqlException(Exception e) {
        if (isDevProfile()) {
            log.error("SQL Exception occurred", e);
            return getResponseEntity(500, "데이터 조회 중 오류가 발생했습니다.", getDetailedMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.error("SQL Exception occurred: {}", e.getMessage());
            return getResponseEntity(500, "데이터 조회 중 오류가 발생했습니다.", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleException(Exception e) {
        // 클라이언트가 연결을 끊은 경우 로그 남기지 않고 무시
        if (e instanceof ClientAbortException || (e.getCause() instanceof IOException && "Broken pipe".equalsIgnoreCase(e.getCause().getMessage()))) {
            return ResponseEntity.ok().build();
        }

        if (isDevProfile()) {
            log.error("Unexpected exception occurred", e);
            return getResponseEntity(500, e.getMessage(), getDetailedMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.error("Unexpected exception occurred: {}", e.getMessage());
            return getResponseEntity(500, "서버 오류가 발생했습니다.", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isDevProfile() {
        return "local".equals(activeProfile) || "dev".equals(activeProfile);
    }

    private String getDetailedMessage(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getName()).append(": ").append(e.getMessage());
        
        if (e.getCause() != null) {
            sb.append("\nCaused by: ").append(e.getCause().getClass().getName()).append(": ").append(e.getCause().getMessage());
        }
        
        sb.append("\nStack trace:\n");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("  at ").append(element.toString()).append("\n");
            // 처음 10개의 스택만 포함
            if (sb.length() > 2000) {
                sb.append("  ... (truncated)\n");
                break;
            }
        }
        
        return sb.toString();
    }

    private ResponseEntity<Error> getResponseEntity(int status, String message, String details, HttpStatus httpStatus) {
        Error error = details != null ? new Error(status, message, details) : new Error(status, message);
        return new ResponseEntity<>(error, httpStatus);
    }

}
