package ces.betyourrole.repository;

import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.MemberState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    List<Member> findByName(String name);

    List<Member> findByMemberState(MemberState memberState);
}
