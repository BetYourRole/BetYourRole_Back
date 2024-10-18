package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class RequiredFieldMissingException extends CustomException{
    public RequiredFieldMissingException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
