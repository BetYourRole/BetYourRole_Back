package ces.betyourrole.repository;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.TodoRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BettingRepository extends JpaRepository<Betting, Long>{

    List<Betting> findByTodo_room(TodoRoom todoRoom);
}
