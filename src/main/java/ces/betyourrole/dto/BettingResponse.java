package ces.betyourrole.dto;


import ces.betyourrole.domain.Betting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BettingResponse {
    private Long id;
    private Integer point;
    private String comment;

    public BettingResponse(Betting betting){
        this.id = betting.getId();
        this.point = betting.getPoint();
        this.comment = betting.getComment();
    }
}
