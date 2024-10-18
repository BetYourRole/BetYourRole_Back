package ces.betyourrole.repository;

import ces.betyourrole.domain.Betting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BettingRepository extends JpaRepository<Betting, Long>{
}
