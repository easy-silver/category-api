package homework.jelee.categoryapi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * 공통 오류 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 유효하지 않은 입력 값 오류 핸들러(400)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handleIllegalArgumentException", e);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status.value(), status.getReasonPhrase(), e.getMessage()),
                status);
    }

    /**
     * Validation을 통한 Request 값 검증 오류에 대한 핸들러(400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        //유효성 검증 실패한 필드, 에러 메시지 정보
        String errorField = e.getBindingResult().getFieldError().getField();
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        String errorMessage = errorField + ": " + defaultMessage;

        return new ResponseEntity<>(new ErrorResponse(status.value(), status.getReasonPhrase(), errorMessage),
                status);
    }

    /**
     * 엔티티 조회 실패 오류 핸들러(404)
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("handleEntityNotFoundException", e);

        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ErrorResponse(status.value(),status.getReasonPhrase(), e.getMessage()),
                status);
    }

    /**
     * 서버 장애에 대한 공통 처리 핸들러(500)
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleUnexpectedException(RuntimeException e) {
        log.error("handleUnexpectedException", e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status.value(), status.getReasonPhrase(), e.getMessage()), status);
    }

}
