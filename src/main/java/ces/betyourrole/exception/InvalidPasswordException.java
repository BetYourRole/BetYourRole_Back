package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends CustomException{
    public InvalidPasswordException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public InvalidPasswordException() {
        super("올바르지 않은 비밀번호입니다.", HttpStatus.UNAUTHORIZED);
    }

}
