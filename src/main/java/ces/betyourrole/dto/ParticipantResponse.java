package ces.betyourrole.dto;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Participant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantResponse {

    private Long id;
    private Long roomId;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<BettingResponse> bettings;

    public ParticipantResponse(Participant p, List<Betting> bettings){
        this.id = p.getId();
        this.roomId = p.getRoom().getId();
        this.name = p.getName();
        this.createDate = p.getCreateDate();
        this.updateDate = p.getUpdateDate();
        this.bettings = bettings.stream().map(BettingResponse::new).toList();
    }

}
