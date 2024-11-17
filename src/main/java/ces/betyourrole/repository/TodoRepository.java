package ces.betyourrole.repository;

import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByRoom(TodoRoom room);
    Integer countByRoom(TodoRoom room);
}
