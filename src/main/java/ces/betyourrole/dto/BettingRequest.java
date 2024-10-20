package ces.betyourrole.dto;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BettingRequest {

    private Long todoId;
    private Long participantId;
    private Integer point;
    private String comment;

    public Betting toEntity(Todo todo, Participant p){
        return new Betting(todo, p, this.point, this.comment);
    }



}
