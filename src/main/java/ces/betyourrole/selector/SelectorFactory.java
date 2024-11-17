package ces.betyourrole.selector;

import ces.betyourrole.domain.MatchingType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SelectorFactory {

    private final Map<MatchingType, DetermineWinnerAlgorithm> serviceMap = new HashMap<>();

    public SelectorFactory(List<DetermineWinnerAlgorithm> algorithms) {
        // `DetermineWinnerAlgorithm` 구현체들을 자동으로 스캔하여 `MatchingType`과 매핑
        for (DetermineWinnerAlgorithm algorithm : algorithms) {
            MatchingType matchingType = algorithm.getMatchingType(); // 각 구현체에서 MatchingType 반환
            serviceMap.put(matchingType, algorithm);
        }
    }

    public DetermineWinnerAlgorithm getAlgorithm(MatchingType matchingType) {
        DetermineWinnerAlgorithm algorithm = serviceMap.get(matchingType);
        if (algorithm == null) {
            throw new IllegalArgumentException("No matching algorithm found for type: " + matchingType);
        }
        return algorithm;
    }
}