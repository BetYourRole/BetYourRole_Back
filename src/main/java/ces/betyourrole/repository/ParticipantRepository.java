package ces.betyourrole.repository;

import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.TodoRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Integer countByRoom(TodoRoom room);
    List<Participant> findByRoom(TodoRoom room);

}
