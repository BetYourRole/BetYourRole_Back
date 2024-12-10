package ces.betyourrole.repository;

import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.MyPageTodoRoomResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Integer countByRoom(TodoRoom room);
    List<Participant> findByRoom(TodoRoom room);

    @Query("""
    SELECT new ces.betyourrole.dto.MyPageTodoRoomResponse(
        tr.name,
        tr.description,
        tr.randomKey,
        tr.state,
        COUNT(distinct p2.id))
    FROM Participant p
    JOIN p.room tr
    JOIN Participant p2 ON tr = p2.room
    WHERE p.activeSession.id = :memberId
    GROUP BY tr.id, tr.name, tr.description, tr.randomKey, tr.state
    """)
    List<MyPageTodoRoomResponse> findTodoRoomsByMember(@Param("memberId") Long memberId);

}
