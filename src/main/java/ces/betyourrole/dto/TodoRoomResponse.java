package ces.betyourrole.dto;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.MatchingType;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.repository.TodoRoomRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodoRoomResponse {

    private Long id;

//    private String roomOwner;

//    private LocalDateTime createDate;

    private String name;

    private String inscription;

    private Integer headCount;

    private Integer participantCount;

    private MatchingType matchingType;

    private Integer point;

    private MatchingState state;

    private boolean visibility;

    private List<TodoResponse> todos;


    public TodoRoomResponse(TodoRoom room, List<Todo> todos, Integer participantCount){
        this.id = room.getId();
        this.name = room.getName();
        this.inscription = room.getInscription();
        this.headCount = todos.size();
        this.participantCount = participantCount;
        this.matchingType = room.getMatchingType();
        this.point = room.getPoint();
        this.state = room.getState();
        this.visibility = room.getVisibility();
        this.todos = todos.stream().map(TodoResponse::new).toList();
    }
}
