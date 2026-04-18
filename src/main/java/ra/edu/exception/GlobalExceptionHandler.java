package ra.edu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.edu.dto.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handle(Exception e) {
//
//        log.error("Lỗi hệ thống xảy ra: ", e);
//
//        return ResponseEntity.status(500)
//                .body(new ErrorResponse(500, "Internal Server Error"));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

        return new ErrorResponse(
                "Invalid data!",
                errors,
                "400 BAD_REQUEST"
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(
                "Not Found!",
                List.of(ex.getMessage()),
                "404 NOT_FOUND"
        );
    }


    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicate(DuplicateException ex) {
        return new ErrorResponse(
                "Duplicate!",
                List.of(ex.getMessage()),
                "409 CONFLICT"
        );
    }



    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(BadRequestException ex) {
        return new ErrorResponse(
                "Bad Request!",
                List.of(ex.getMessage()),
                "400 BAD_REQUEST"
        );
    }

    @ExceptionHandler(ForbiddenFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleForbidden(ForbiddenFieldException ex) {
        return new ErrorResponse(
                "Forbidden Field!",
                List.of(ex.getMessage()),
                "400 BAD_REQUEST"
        );
    }



    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntime(RuntimeException ex) {
        return new ErrorResponse(
                "Internal Server Error!",
                List.of(ex.getMessage()),
                "500 INTERNAL_SERVER_ERROR"
        );
    }


}
