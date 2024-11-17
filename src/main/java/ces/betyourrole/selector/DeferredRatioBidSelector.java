//package ces.betyourrole.selector;
//
//import ces.betyourrole.domain.Betting;
//import ces.betyourrole.domain.MatchingType;
//
//import java.util.*;
//
//public class DeferredRatioBidSelector implements DetermineWinnerAlgorithm{
//    @Override
//    public MatchingType getMatchingType() {
//        return MatchingType.DEFERRED_RATIO;
//    }
//
//    @Override
//    public Map<Long, Long> determineWinner(List<Betting> bettingList) {
//        Set<Long> remainingMember = new HashSet<>();
//        Set<Long> remainingTodo = new HashSet<>();
//        for(Betting bet : bettingList){
//            remainingMember.add(bet.getParticipant().getId());
//            remainingTodo.add(bet.getTodo().getId());
//        }
//
//        Map<Long, Long> winners = new HashMap<>();
//        while(!remainingTodo.isEmpty()){
//            HighestFirstBidSelector.HighestBetting highestBetting = getHighestInRemainingTodo(remainingMember,remainingTodo,bettingList);
//            Betting win = bettingList.stream().filter(bet -> {
//                return Objects.equals(bet.getTodo().getId(), highestBetting.id) && remainingMember.contains(bet.getParticipant().getId());
//            }).max(Comparator.comparing(Betting::getPoint)).get();
//            winners.put(win.getTodo().getId(), win.getParticipant().getId());
//            remainingMember.remove(win.getParticipant().getId());
//            remainingTodo.remove(win.getTodo().getId());
//        }
//
//        return winners;    }
//}
