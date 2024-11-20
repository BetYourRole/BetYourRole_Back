package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public CustomException(String message, HttpStatus status){
        super(message);
        this.message = message;
        this.status = status;
    }

    public ResponseEntity<String> toResponse(){
        return ResponseEntity.status(status).body(this.message);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.status = errorCode.getHttpStatus();
        this.message = message;
    }

}
