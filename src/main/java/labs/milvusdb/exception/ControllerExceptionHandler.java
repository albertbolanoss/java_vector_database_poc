package labs.milvusdb.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ControllerError> missingArgsExceptionHandle(HttpServletRequest request, Exception ex) {
        logInfo(request.getMethod(), request.getRequestURL().toString(), ex.getMessage());
        return new ResponseEntity<>(new ControllerError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ControllerError> noControllerExceptionHandler (HttpServletRequest request, Exception ex) {
        logError(request.getMethod(), request.getRequestURL().toString(), ex);
        return new ResponseEntity<>(new ControllerError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logInfo(String method, String path, String message) {
        log.info("The request {} - {} had failed: {}", method, path, message);
    }

    private void logError(String method, String path, Throwable throwable) {
        log.error("The request {} - {} had failed: {}", method, path, throwable);
    }
}
