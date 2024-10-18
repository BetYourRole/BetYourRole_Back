package ces.betyourrole.dto;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.MatchingType;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.repository.TodoRoomRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class TodoRoomResponse {

    private Long id;

//    private String roomOwner;

//    private LocalDateTime createDate;

    private String name;

    private String inscription;

    private Integer headCount;

    private MatchingType matchingType;

    private Integer point;

    private MatchingState state;

    private boolean visibility;

    public TodoRoomResponse(TodoRoom room){
        this.id = room.getId();
        this.name = room.getName();
        this.inscription = room.getInscription();
        this.headCount = room.getHeadCount();;
        this.matchingType = room.getMatchingType();
        this.point = room.getPoint();
        this.state = room.getState();
        this.visibility = room.getVisibility();
    }
}
