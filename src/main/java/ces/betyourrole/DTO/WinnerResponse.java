package ces.betyourrole.dto;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.exception.InvalidBettingDataException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WinnerResponse {
    private String name;
    private String comment;

    public WinnerResponse(Todo todo, List<Betting> bettings){
        if(todo.getWinner() != null){
            this.name = todo.getWinner().getName();
            Betting winBetting = bettings.stream().filter(bet -> todo.equals(bet.getTodo()) && todo.getWinner().equals(bet.getParticipant())).findFirst().orElseThrow(() -> new InvalidBettingDataException("유효하지 않은 승리자입니다."));
            this.comment = winBetting.getComment();
        }
    }
}
