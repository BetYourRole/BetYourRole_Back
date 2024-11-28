package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CustomException{
    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
