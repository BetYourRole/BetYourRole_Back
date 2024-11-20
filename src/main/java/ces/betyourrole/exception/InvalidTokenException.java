package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException  extends CustomException{

    public InvalidTokenException() {
        super("올바르지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }
}
