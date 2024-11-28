package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class URLNotFoundException extends CustomException{

    public URLNotFoundException() {
        super("해당 주소가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

    public URLNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
