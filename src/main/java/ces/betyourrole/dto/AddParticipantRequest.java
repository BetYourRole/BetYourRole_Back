package ces.betyourrole.dto;

import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.TodoRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddParticipantRequest {

    private String name;
    private String password;
    private List<BettingRequest> bettings;

    public Participant toEntity(TodoRoom todoRoom, Member member){
        return new Participant(member, todoRoom, this.name);
    }

    public Participant toEntity(TodoRoom todoRoom){
        return new Participant(this.password, todoRoom, this.name);
    }

}
