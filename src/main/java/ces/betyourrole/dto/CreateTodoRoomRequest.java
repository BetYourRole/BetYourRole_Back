package ces.betyourrole.dto;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.MatchingType;
import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.exception.InvalidRangeException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class CreateTodoRoomRequest {

    private String name;

    private String inscription;

    private Integer headCount;

    private MatchingType matchingType;

    private Integer point;

    private boolean visibility;

    private String password;

    private List<TodoDTO> todos;

    public TodoRoom toEntity(Member member){
        return new TodoRoom(member, this.name, this.inscription, this.headCount, this.matchingType, this.point, this.visibility);
    }

    public TodoRoom toEntity(){
        return new TodoRoom(this.password, this.name, this.inscription, this.headCount, this.matchingType, this.point, this.visibility);

    }
}
