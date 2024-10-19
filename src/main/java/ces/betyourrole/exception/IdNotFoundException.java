package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends CustomException{
    public IdNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public IdNotFoundException() {
        super("해당하는 방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
