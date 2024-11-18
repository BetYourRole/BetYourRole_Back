package ces.betyourrole.dto;

import ces.betyourrole.domain.Participant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ParticipantInfo {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ParticipantInfo(Participant p){
        this.id = p.getId();
        this.name = p.getName();
        this.createDate = p.getCreateDate();
        this.updateDate = p.getUpdateDate();
    }
}
