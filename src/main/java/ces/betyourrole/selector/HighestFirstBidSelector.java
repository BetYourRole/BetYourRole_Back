package ces.betyourrole.selector;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.MatchingType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class HighestFirstBidSelector implements DetermineWinnerAlgorithm{

    @Override
    public MatchingType getMatchingType() {
        return MatchingType.HIGHEST_FIRST;
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
            HighestBetting highestBetting = getHighestInRemainingTodo(remainingMember,remainingTodo,bettingList);
            Betting win = bettingList.stream()
                    .filter(bet -> Objects.equals(bet.getId(), highestBetting.id)).findFirst().get();
            winners.put(win.getTodo().getId(), win.getParticipant().getId());
            remainingMember.remove(win.getParticipant().getId());
            remainingTodo.remove(win.getTodo().getId());
        }

        return winners;
    }

    private HighestBetting getHighestInRemainingTodo(Set<Long> remainingMember, Set<Long> remainingTodo, List<Betting> bettingList){
        Map<Long, Long> point = new HashMap<>();
        HighestBetting highestBetting = new HighestBetting();

        for(Betting bet : bettingList){
            if(!remainingMember.contains(bet.getParticipant().getId())) continue;
            if(!remainingTodo.contains(bet.getTodo().getId())) continue;
            point.put(bet.getTodo().getId(), point.getOrDefault(bet.getTodo().getId(), 0L) + bet.getPoint());
            //지연로딩이라 속도 많이 느릴지도
            if(highestBetting.maxPoint < point.get(bet.getTodo().getId()) || (Objects.equals(highestBetting.maxPoint, point.get(bet.getTodo().getId())) && highestBetting.localDateTime.isBefore(bet.getParticipant().getUpdateDate()))){
                highestBetting.maxPoint = point.get(bet.getTodo().getId());
                highestBetting.id = bet.getId();
            }
        }

        return highestBetting;
    }

    public static class HighestBetting {
        public Long maxPoint = -1L;
        public Long id = -1L;

        public LocalDateTime localDateTime = LocalDateTime.now();

    }

}
