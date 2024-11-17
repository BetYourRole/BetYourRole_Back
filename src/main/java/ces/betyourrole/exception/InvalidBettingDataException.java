package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class InvalidBettingDataException extends CustomException{

    public InvalidBettingDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
