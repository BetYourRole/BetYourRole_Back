package ces.betyourrole.selector;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.MatchingType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DeferredRatioBidSelector implements DetermineWinnerAlgorithm{
    @Override
    public MatchingType getMatchingType() {
        return MatchingType.DEFERRED_RATIO;
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
            MaxTodo maxTodo = findMaxTodo(bettingList, remainingMember, remainingTodo);
            Betting win = findWinner(bettingList, remainingMember, maxTodo);
            winners.put(win.getTodo().getId(), win.getParticipant().getId());
            remainingMember.remove(win.getParticipant().getId());
            remainingTodo.remove(win.getTodo().getId());
        }

        return winners;
    }

    private Betting findWinner(List<Betting> bettingList, Set<Long> remainingMember, MaxTodo maxTodo){
        int defferPoint = maxTodo.sumPoint / remainingMember.size();
        List<Betting> list = new ArrayList<>(bettingList.stream().filter(bet -> (remainingMember.contains(bet.getParticipant().getId())) && (bet.getTodo().getId() == maxTodo.id) && (bet.getPoint() >= defferPoint)).toList());
        int sumPoint = list.stream().mapToInt(Betting::getPoint).sum();
        if(sumPoint == 0){
            Collections.shuffle(list);
            return list.getFirst();
        }

        Random random = new Random();
        int point = random.nextInt(sumPoint);
        for(Betting bet : list){
            point -= bet.getPoint();
            if(point<0) return bet;
        }

        return null;
    }

    private MaxTodo findMaxTodo(List<Betting> bettingList, Set<Long> remainingMember, Set<Long> remainingTodo){
        MaxTodo maxTodo = new MaxTodo();
        for(Long id : remainingTodo){
            int sum = bettingList.stream().filter(bet -> (Objects.equals(bet.getTodo().getId(), id)) && (remainingMember.contains(bet.getParticipant().getId()))).mapToInt(Betting::getPoint).sum();
            if(maxTodo.sumPoint < sum){
                maxTodo.sumPoint = sum;
                maxTodo.id = id;
            }
        }
        return maxTodo;
    }

    private static class MaxTodo{
        int sumPoint = -1;
        long id = -1;
    }

}
