package ces.betyourrole.controller;

import ces.betyourrole.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customExceptionResponse(CustomException e) {
        return e.toResponse();
    }

}
