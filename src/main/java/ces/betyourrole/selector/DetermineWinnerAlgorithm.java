package ces.betyourrole.selector;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.MatchingType;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.TodoResponse;

import java.util.List;
import java.util.Map;

public interface DetermineWinnerAlgorithm {
    MatchingType getMatchingType();
    Map<Long, Long> determineWinner(List<Betting> bettingList);//<TodoId, ParticipantId>
}
