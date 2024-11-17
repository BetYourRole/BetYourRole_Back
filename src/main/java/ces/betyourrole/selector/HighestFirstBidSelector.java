package ces.betyourrole.selector;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.MatchingType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HighestFirstBidSelector implements DetermineWinnerAlgorithm{

    @Override
    public MatchingType getMatchingType() {
        return MatchingType.HIGHEST_FIRST_BID;
    }

    @Override
    public Map<Long, Long> determineWinner(List<Betting> bettingList) {
        Set<Long> remainingMember = new HashSet<>();
        Set<Long> remainingTodo = new HashSet<>();
        for(Betting bet : bettingList){
            remainingMember.add(bet.getParticipant().getId());
            remainingTodo.add(bet.getTodo().getId());
        }

        Map<Long, Long> winners = new HashMap<>();
        while(!remainingTodo.isEmpty()){
            HighestTodo highestTodo = getHighestInRemainingTodo(remainingMember,remainingTodo,bettingList);
            Betting win = bettingList.stream().filter(bet -> {
                return Objects.equals(bet.getTodo().getId(), highestTodo.highestId) && remainingMember.contains(bet.getParticipant().getId());
            }).max(Comparator.comparing(Betting::getPoint)).get();
            winners.put(win.getTodo().getId(), win.getParticipant().getId());
            remainingMember.remove(win.getParticipant().getId());
            remainingTodo.remove(win.getTodo().getId());
        }

        return winners;
    }

    private HighestTodo getHighestInRemainingTodo(Set<Long> remainingMember, Set<Long> remainingTodo, List<Betting> bettingList){
        Map<Long, Long> point = new HashMap<>();
        HighestTodo highestTodo = new HighestTodo();

        for(Betting bet : bettingList){
            if(!remainingMember.contains(bet.getParticipant().getId())) continue;
            if(!remainingTodo.contains(bet.getTodo().getId())) continue;
            point.put(bet.getTodo().getId(), point.getOrDefault(bet.getTodo().getId(), 0L) + bet.getPoint());
            if(highestTodo.maxPoint < point.get(bet.getTodo().getId())){
                highestTodo.maxPoint = point.get(bet.getTodo().getId());
                highestTodo.highestId = bet.getTodo().getId();
            }
        }

        return highestTodo;
    }

    public static class HighestTodo {
        public Long maxPoint = -1L;
        public Long highestId = -1L;

    }

}
