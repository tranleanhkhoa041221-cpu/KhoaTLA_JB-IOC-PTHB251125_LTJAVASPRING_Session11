package ra.edu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.edu.dto.response.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {

        log.error("Lỗi hệ thống xảy ra: ", e);

        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Internal Server Error"));
    }
}
