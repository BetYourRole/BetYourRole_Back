package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class InvalidCapacityException extends CustomException{
    public InvalidCapacityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
