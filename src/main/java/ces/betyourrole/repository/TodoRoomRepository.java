package ces.betyourrole.repository;

import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.MyPageTodoRoomResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRoomRepository extends JpaRepository<TodoRoom, Long> {
    Optional<TodoRoom> findByRandomKey(String key);

    @Query("""
            SELECT new ces.betyourrole.dto.MyPageTodoRoomResponse(
                r.name,
                r.description,
                r.randomKey,
                r.state,
                (SELECT COUNT(p) FROM Participant p WHERE p.room = r)
            )
            FROM TodoRoom r
            WHERE r.activeSession.id = :memberId
    """)
    List<MyPageTodoRoomResponse> findCreatedTodoRoomsByMember(@Param("memberId") Long memberId);
}
