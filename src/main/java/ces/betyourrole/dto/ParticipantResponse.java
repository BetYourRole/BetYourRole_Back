package ces.betyourrole.dto;

import ces.betyourrole.domain.Participant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantResponse {

    private Long id;
    private Long roomId;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ParticipantResponse(Participant p){
        this.id = p.getId();
        this.roomId = p.getRoom().getId();
        this.name = p.getName();
        this.createDate = p.getCreateDate();
        this.updateDate = p.getUpdateDate();
    }

}
