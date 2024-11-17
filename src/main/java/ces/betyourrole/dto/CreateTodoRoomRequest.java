package ces.betyourrole.dto;

import ces.betyourrole.domain.MatchingType;
import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.TodoRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateTodoRoomRequest {

    private String name;

    private String inscription;

    private MatchingType matchingType;

    private Integer point;

    private boolean visibility;

    private String password;

    private List<CreateTodoRequest> todos;

    public TodoRoom toEntity(Member member){
        return new TodoRoom(member, this.name, this.inscription, this.todos.size(),this.matchingType, this.point, this.visibility);
    }

    public TodoRoom toEntity(){
        return new TodoRoom(this.password, this.name, this.inscription, this.todos.size(), this.matchingType, this.point, this.visibility);
    }
}
