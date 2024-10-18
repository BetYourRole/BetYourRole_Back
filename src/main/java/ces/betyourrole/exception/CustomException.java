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

    public ResponseEntity<?> toResponse(){
        return ResponseEntity.status(status).body(this.message);
    }

}
