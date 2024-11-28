package ces.betyourrole.repository;

import ces.betyourrole.domain.TodoRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRoomRepository extends JpaRepository<TodoRoom, Long> {
    Optional<TodoRoom> findByRandomKey(String key);
}
