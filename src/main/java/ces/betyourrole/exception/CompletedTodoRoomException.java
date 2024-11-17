package ces.betyourrole.exception;

import org.springframework.http.HttpStatus;

public class CompletedTodoRoomException extends CustomException{
    public CompletedTodoRoomException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public CompletedTodoRoomException() {
        this("이미 결과가 정해진 방입니다.");
    }
}
