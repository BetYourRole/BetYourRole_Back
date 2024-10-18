package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class InvalidRangeException extends CustomException{

    public InvalidRangeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
