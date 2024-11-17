package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class ParticipationNotAllowedException extends CustomException {
    public ParticipationNotAllowedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

}